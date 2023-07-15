package com.example.homework.presentation.recycler

import NotesTheme
import android.content.res.Configuration
import android.view.*
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Api
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.homework.R
import com.example.homework.presentation.composefutures.*
import com.example.homework.presentation.composefutures.dialogs.DateAddDialog
import com.example.homework.presentation.composefutures.dialogs.DefaultDialog
import com.example.homework.presentation.composefutures.dialogs.ItemsDialog
import com.example.homework.presentation.composefutures.toolbarsandloader.Toolbar
import com.example.homework.presentation.detail.NoteFragment
import com.example.homework.presentation.detailbd.BirthdayFragment
import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.model.NoteType

class ListFragment : ComposeFragment() {

    private val viewModel: ListViewModel by lazy {
        ViewModelProvider(this)[ListViewModel::class.java]
    }

    @Composable
    override fun GetContent() {
        val state = viewModel.viewStateObs.observeAsState().value ?: return
        ThemeSettings(themeCode = state.currentTheme) {
            ListNotesScreen(state)
        }
    }

    @Composable
    private fun ListNotesScreen(state: ListViewState) {

        viewModel.submitUIEvent(ListEvents.GetNotes)

        val isVisibleNow = remember { mutableStateOf(false) }

        if (state.errorText.isNotBlank())
            Toast.makeText(context, state.errorText, Toast.LENGTH_SHORT).show()
        if (state.isShowDeleteDialog) DeleteDialog(state.deletableNoteId)
        if (state.isShowChangeDialog) showChangeDialog(state.currentNote!!)
        if (state.isShowDateAddDialog) ChangeDateDialog(note = state.currentNote!!)
        if (state.isShowSettingsDialog) SettingsDialog()
        if (state.isShowDeleteAllDialog) ClearAllNotes()

        Column(modifier = Modifier.background(NotesTheme.colors.background)) {

            Toolbar(
                title = stringResource(id = R.string.list_fragment_title),
                isBackArrowVisible = false,
                actions = {
                    IconButton(onClick = {
                        viewModel.submitUIEvent(ListEvents.ShowSettingsDialog(true))
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Api,
                            contentDescription = "select theme"
                        )
                    }
                },
                onBackClick = { }
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {

                items(
                    items = state.notesList
                ) { item: NoteModel ->
                    when (item.type) {
                        NoteType.BIRTHDAY_TYPE -> {
                            SecondItem(item)
                        }
                        NoteType.NOTE_TYPE -> Item(item)
                    }
                }
            }

        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart) {

            FloatingActionButton(
                modifier = Modifier
                    .padding(
                        start = NotesTheme.dimens.sideMargin,
                        end = NotesTheme.dimens.sideMargin,
                        bottom = NotesTheme.dimens.sideMargin
                    ),
                backgroundColor = NotesTheme.colors.secondary,
                onClick = {
                    viewModel.submitUIEvent(ListEvents.ClearAll(true))
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(id = R.string.delete_all),
                    tint = NotesTheme.colors.background
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {

            val fabSize = 56.dp

            FloatingActionButton(
                modifier = Modifier
                    .padding(
                        end = NotesTheme.dimens.sideMargin,
                        bottom = NotesTheme.dimens.sideMargin
                    ),
                backgroundColor = NotesTheme.colors.secondary,
                onClick = {
                    isVisibleNow.value = !isVisibleNow.value
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_black),
                    contentDescription = null,
                    tint = NotesTheme.colors.background
                )
            }

            AnimatedVisibility(
                visible = isVisibleNow.value,
                enter = slideInVertically(),
                exit = shrinkVertically()
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .offset(y = (-fabSize) - NotesTheme.dimens.sideMargin)
                        .padding(
                            end = NotesTheme.dimens.sideMargin,
                            bottom = NotesTheme.dimens.sideMargin
                        ),
                    backgroundColor = NotesTheme.colors.secondary,
                    onClick = { goToDetails(state.getEmptyNote()) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.NoteAdd,
                        contentDescription = "Событие",
                        tint = NotesTheme.colors.background
                    )
                }
            }

            AnimatedVisibility(visible = isVisibleNow.value) {
                FloatingActionButton(
                    modifier = Modifier
                        .offset(y = (-fabSize * 2) - (NotesTheme.dimens.sideMargin * 2))
                        .padding(
                            end = NotesTheme.dimens.sideMargin,
                            bottom = NotesTheme.dimens.sideMargin
                        ),
                    backgroundColor = NotesTheme.colors.secondary,
                    onClick = { goToDetails(state.getEmptyBirth()) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Event,
                        contentDescription = "Заметка",
                        tint = NotesTheme.colors.background
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun Item(note: NoteModel) {
        viewModel.viewState = viewModel.viewState.copy(currentNote = note)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(NotesTheme.dimens.contentMargin)
                .combinedClickable(
                    onClick = { viewModel.submitUIEvent(ListEvents.ShowChangeDialog(true)) },
                    onLongClick = {
                        viewModel.submitUIEvent(
                            ListEvents.ShowDeleteDialog(
                                true,
                                note.id
                            )
                        )
                    },
                ),
            shape = RoundedCornerShape(NotesTheme.dimens.contentMargin),
            backgroundColor = NotesTheme.colors.primary,
            elevation = NotesTheme.dimens.halfContentMargin
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(NotesTheme.dimens.contentMargin)
            ) {
                Text(text = note.name, style = NotesTheme.typography.h6)
                Text(text = note.description, style = NotesTheme.typography.body1)
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun SecondItem(note: NoteModel) {
        viewModel.viewState = viewModel.viewState.copy(currentNote = note)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(NotesTheme.dimens.halfContentMargin)
                .combinedClickable(
                    onClick = { viewModel.submitUIEvent(ListEvents.ShowChangeDialog(true)) },
                    onLongClick = {
                        viewModel.submitUIEvent(
                            ListEvents.ShowDeleteDialog(
                                true,
                                note.id
                            )
                        )
                    },
                ),
            shape = RoundedCornerShape(NotesTheme.dimens.halfContentMargin),
            backgroundColor = NotesTheme.colors.secondary,
            elevation = NotesTheme.dimens.contentMargin,
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(NotesTheme.dimens.contentMargin)
            ) {
                Text(text = note.name, style = NotesTheme.typography.h6)
                Text(text = note.description, style = NotesTheme.typography.body1)
            }
            Box(contentAlignment = Alignment.BottomEnd) {
                Row {
                    Text(text = note.date)
                }
            }
        }
    }

    @Composable
    private fun DeleteDialog(id: Long) {
        DefaultDialog(
            title = stringResource(id = R.string.delete),
            onPositiveClick = {
                if (id != -1L) viewModel.submitUIEvent(ListEvents.DeleteNote(id))
                viewModel.submitUIEvent(ListEvents.ShowDeleteDialog(false, -1))
            },
            onNegativeClick = { viewModel.submitUIEvent(ListEvents.ShowDeleteDialog(false, -1)) }) {
        viewModel.submitUIEvent(ListEvents.ShowDeleteDialog(false,-1))}
    }
    @Composable
    private fun ChangeDateDialog(note:NoteModel) {
        viewModel.viewState = viewModel.viewState.copy(currentNote = note)
        DateAddDialog(
            noteDate = note.date.ifBlank {"00.00.00"},
            onPositiveClick = {viewModel.submitUIEvent(ListEvents.SaveUserDate(note,it,note.id))},
            onNegativeClick = { viewModel.submitUIEvent(ListEvents.ShowDateAddDialog(false)) }) {

           viewModel.submitUIEvent(ListEvents.ShowDateAddDialog(false))} }


//    @Composable
//    private fun deleteNoteDialog(note: NoteModel) {
//        DefaultDialog(
//            title = stringResource(id = R.string.delete),
//            onPositiveClick = {
//                viewModel.submitUIEvent(ListEvents.DeleteNoteModel(note))
//                viewModel.submitUIEvent(ListEvents.ShowDeleteDialog(false, -1))
//            },
//            onNegativeClick = { viewModel.submitUIEvent(ListEvents.ShowDeleteDialog(false, -1)) }) {
//        }
//    }

    @Composable
    private fun showChangeDialog(note: NoteModel) {
        val items = arrayOf("Открыть", "Удалить", "Изменить дату")
        ItemsDialog(
            title = "Выберите действие",
            items = items,
            onItemClick = { position ->
                when (position) {
                    0 -> {
                        goToDetails(note)
                        viewModel.submitUIEvent(ListEvents.ShowChangeDialog(false))
                    }
                    1 -> {
                        viewModel.submitUIEvent(ListEvents.DeleteNoteModel(note))
                        viewModel.submitUIEvent(ListEvents.ShowChangeDialog(false))
                    }
                2 -> {viewModel.submitUIEvent(ListEvents.ShowDateAddDialog(true))
                    viewModel.submitUIEvent(ListEvents.ShowChangeDialog(false))}
                }
            }
        ) {
            viewModel.submitUIEvent(ListEvents.ShowChangeDialog(false))
        }
    }


        @Composable
        private fun ClearAllNotes() {
            DefaultDialog(
                title = stringResource(id = R.string.delete_all),
                negativeButtonText = stringResource(id = R.string.cancel),
                positiveButtonText = stringResource(id = R.string.yes),
                onPositiveClick = {
                    viewModel.submitUIEvent(ListEvents.DeleteAll)
                    Toast.makeText(requireContext(), R.string.all_notes_delete, Toast.LENGTH_SHORT)
                        .show()
                    viewModel.submitUIEvent(ListEvents.ClearAll(false))
                },
                onNegativeClick = {
                    viewModel.submitUIEvent(ListEvents.ClearAll(false))
                })
            {}
        }

        @Composable
        private fun SettingsDialog() {

            val items = arrayOf(
                stringResource(id = R.string.first_theme),
                stringResource(id = R.string.second_theme),
                stringResource(id = R.string.third_theme),
            )

            ItemsDialog(
                title = stringResource(R.string.set_theme),
                items = items,
                onItemClick = { position ->
                    when (position) {
                        0 -> viewModel.submitUIEvent(ListEvents.ChangeTheme(FIRST_THEME))
                        1 -> viewModel.submitUIEvent(ListEvents.ChangeTheme(SECOND_THEME))
                        2 -> viewModel.submitUIEvent(ListEvents.ChangeTheme(THIRD_THEME))
                    }
                }
            ) { viewModel.submitUIEvent(ListEvents.ShowSettingsDialog(false)) }
        }

        private fun goToDetails(note: NoteModel) {
            if (note.type == NoteType.NOTE_TYPE) {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        NoteFragment.newInstance(note ?: viewModel.viewState.getEmptyNote())
                    )
                    .addToBackStack("")
                    .commit()
            } else {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        BirthdayFragment.newInstance(note ?: viewModel.viewState.getEmptyBirth())
                    )
                    .addToBackStack("")
                    .commit()
            }

        }

        @Preview(name = "ListNotesScreen", uiMode = Configuration.UI_MODE_NIGHT_NO)
        @Composable
        private fun RecyclerScreenPreview() {
            ThemeSettings() {

                val model = NoteModel(
                    id = 0,
                    name = "Заметка",
                    description = "Ты собака, я собака, ты собака, я собака, ты собака, я собака, ты собака, " +
                            "я собака, ты собака, я собака, ты собака, я собака, ты собака, я собака",
                    type = NoteType.BIRTHDAY_TYPE,
                    date = "25.01.22"
                )

                val secondModel = NoteModel(
                    id = 2,
                    name = "Заметка нотка",
                    description = "без собак без собак без собак",
                    type = NoteType.NOTE_TYPE,
                    date = "01.01.01"
                )


                val state = ListViewState(
                    notesList = listOf(model, secondModel, model),
                    isShowDeleteDialog = false,
                    isLoading = false,
                    errorText = "",
                    isShowSettingsDialog = false

                )

                ListNotesScreen(state)
            }
        }

    }