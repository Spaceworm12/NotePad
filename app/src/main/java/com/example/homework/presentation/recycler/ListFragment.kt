package com.example.homework.presentation.recycler

import com.example.homework.presentation.composefutures.NotesTheme
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.homework.R
import com.example.homework.data.models.model.app.AppNotes
import com.example.homework.presentation.composefutures.ComposeFragment
import com.example.homework.presentation.composefutures.THEME_CODE
import com.example.homework.presentation.composefutures.ThemeSettings
import com.example.homework.presentation.composefutures.toolbarsandloader.LoaderBlock
import com.example.homework.presentation.composefutures.toolbarsandloader.Toolbar
import com.example.homework.presentation.detail.NoteFragment
import com.example.homework.presentation.detailbd.BirthdayFragment
import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.model.NoteType
import com.example.homework.presentation.recycler.notelistcomponents.*

class ListFragment : ComposeFragment() {

    private val viewModel: ListViewModel by lazy {
        ViewModelProvider(this)[ListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.submitUIEvent(ListEvents.GetNotes)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @Composable
    override fun GetContent() {
        val state = viewModel.viewStateObs.observeAsState().value ?: return
        val themeCount = AppNotes.getSettingsTheme().getInt(THEME_CODE, 0).toString()

        ThemeSettings(themeCode = state.currentTheme) {
            ListNotesScreen(state, themeCount)
        }
    }

    @Composable
    private fun ListNotesScreen(state: ListViewState, themeCount: String) {
        val isVisibleNow = remember { mutableStateOf(false) }

        if (state.isLoading) LoaderBlock(text = "падажжи")

        if (state.errorText.isNotBlank())
            Toast.makeText(context, state.errorText, Toast.LENGTH_SHORT).show()

        if (state.isShowDeleteDialog)
            DeleteDialog(state.deletableNoteId) { event -> viewModel.submitUIEvent(event) }

        if (state.isShowCalendar)
            ShowDateDialog(
                state.currentNote!!,
                onDismissInvoke = {
                    viewModel.submitUIEvent(ListEvents.ShowCalendar(false, state.currentNote))
                    viewModel.submitUIEvent(ListEvents.ShowChangeDialog(false, state.currentNote))
                }) {
                    event -> viewModel.submitUIEvent(event)
            }

        if (state.isShowChangeDialog)
            ShowChangeDialog(
                state.currentNote!!,
                goToTheNextScreen = { note -> goToDetails(note) },
                dismiss = { viewModel.submitUIEvent(ListEvents.ShowChangeDialog(false, state.currentNote)) }
            ) { event ->
                viewModel.submitUIEvent(event)
            }

        if (state.isShowSettingsDialog)
            ShowSettingsDialog(
                onToastShow = { textToast -> Toast.makeText(context, textToast, Toast.LENGTH_SHORT).show() },
                state.currentTheme
            ) { event -> viewModel.submitUIEvent(event) }

        if (state.isShowSettingsDialogRadio)
            ShowSettingsDialogRadio(
                state.currentTheme,
                onDismiss = { viewModel.submitUIEvent(ListEvents.ShowSettingsDialogRadio(false)) },
                onUiEvent = { event -> viewModel.submitUIEvent(event) },
                onToastShow = { textToast -> Toast.makeText(context, textToast, Toast.LENGTH_SHORT).show() }
            )

        if (state.isShowDeleteAllDialog)
            ClearAllNotes(
                onToastShow = { textToast -> Toast.makeText(context, textToast, Toast.LENGTH_SHORT).show() },
                onDismiss = { viewModel.submitUIEvent(ListEvents.ShowSettingsDialogRadio(false)) }
            ) { event ->
                viewModel.submitUIEvent(event)
            }

        Column(
            modifier = Modifier.background(
                NotesTheme.colors.background, shape = RoundedCornerShape(
                    NotesTheme.dimens.halfContentMargin
                )
            )
        ) {
            Toolbar(
                title = String.format(
                    "%s%s",
                    stringResource(id = R.string.list_of_notes_theme_number),
                    themeCount
                ),
                stringResource(id = R.string.list_fragment_title),
                isBackArrowVisible = false,
                actions = {
                    IconButton(onClick = {
                        viewModel.submitUIEvent(ListEvents.ShowSettingsDialog(true))
                    }) {
                        Icon(
                            modifier = Modifier
                                .size(NotesTheme.dimens.bigDimension)
                                .padding(NotesTheme.dimens.sideMargin),
                            imageVector = Icons.Filled.Api,
                            contentDescription = stringResource(R.string.select_theme)
                        )
                    }

                    Spacer(modifier = Modifier.padding(1.dp))

                    IconButton(onClick = {
                        viewModel.submitUIEvent(ListEvents.ShowSettingsDialogRadio(true))
                    }) {
                        Icon(
                            modifier = Modifier
                                .size(NotesTheme.dimens.bigDimension)
                                .padding(NotesTheme.dimens.sideMargin),
                            imageVector = Icons.Filled.Lens,
                            contentDescription = stringResource(R.string.select_theme)
                        )
                    }
                },
                onBackClick = { }
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(NotesTheme.dimens.sideMargin),
            ) {
                items(
                    items = state.notesList
                ) { item: NoteModel ->
                    when (item.type) {
                        NoteType.BIRTHDAY_TYPE -> EventItem(item) { event ->
                            viewModel.submitUIEvent(event)
                        }

                        NoteType.NOTE_TYPE -> NoteItem(item) { event ->
                            viewModel.submitUIEvent(event)
                        }
                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart) {
            FloatingActionButton(
                modifier = Modifier
                    .height(NotesTheme.dimens.bigDimension)
                    .width(NotesTheme.dimens.bigDimension)
                    .padding(
                        start = NotesTheme.dimens.sideMargin,
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
                    .height(NotesTheme.dimens.bigDimension)
                    .width(NotesTheme.dimens.bigDimension)
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
                        .height(NotesTheme.dimens.bigDimension)
                        .width(NotesTheme.dimens.bigDimension)
                        .offset(y = (-fabSize) - (NotesTheme.dimens.sideMargin))
                        .padding(
                            end = NotesTheme.dimens.sideMargin,
                            bottom = NotesTheme.dimens.sideMargin
                        ),
                    backgroundColor = NotesTheme.colors.secondary,
                    onClick = { goToDetails(state.getEmptyNote()) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.NoteAdd,
                        contentDescription = stringResource(R.string.event),
                        tint = NotesTheme.colors.background
                    )
                }
            }

            AnimatedVisibility(visible = isVisibleNow.value) {
                FloatingActionButton(
                    modifier = Modifier
                        .height(NotesTheme.dimens.bigDimension)
                        .width(NotesTheme.dimens.bigDimension)
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
                        contentDescription = stringResource(R.string.note),
                        tint = NotesTheme.colors.background
                    )
                }
            }
        }
    }

    private fun goToDetails(note: NoteModel) {
        if (note.type == NoteType.NOTE_TYPE) {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    NoteFragment.newInstance(note)
                )
                .addToBackStack("")
                .commit()
        } else {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    BirthdayFragment.newInstance(note)
                )
                .addToBackStack("")
                .commit()
        }
    }


    @Preview(name = "ListNotesScreen", uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Composable
    private fun RecyclerScreenPreview() {
        ThemeSettings {

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
                notesList = listOf(model, secondModel, model, secondModel, model)
            )

            ListNotesScreen(state, themeCount = "2")
        }
    }

}
