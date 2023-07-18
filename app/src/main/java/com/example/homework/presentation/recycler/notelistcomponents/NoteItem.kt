package com.example.homework.presentation.recycler.notelistcomponents

import NotesTheme
import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.homework.presentation.composefutures.ThemeSettings
import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.model.NoteType
import com.example.homework.presentation.recycler.ListEvents


@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun NoteItem(note: NoteModel, onUiEvent: (ListEvents) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(NotesTheme.dimens.contentMargin)
            //TODO: В ресурсы
            .padding(top = 10.dp)
            .combinedClickable(
                onClick = {
                    onUiEvent.invoke(ListEvents.SaveCurrentNote(note))
                    onUiEvent.invoke(ListEvents.ShowChangeDialog(true, note))
                },
                onLongClick = {
                    onUiEvent.invoke(
                        ListEvents.ShowDeleteDialog(
                            true,
                            note.id
                        )
                    )
                },
            ),
        shape = RoundedCornerShape(30.dp),
        backgroundColor = NotesTheme.colors.primary,
        elevation = NotesTheme.dimens.halfContentMargin
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                //TODO: В ресурсы
                .padding(10.dp)
        ) {
            Text(text = note.name, style = NotesTheme.typography.h6)
            Text(text = note.description, style = NotesTheme.typography.body1)
        }
    }
}

@Preview(name = "NoteItem", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun NoteItemPreview() {
    ThemeSettings {

        val note = NoteModel(
            id = 0,
            name = "Заметка",
            description = "Ты собака, я собака, ты собака, я собака, ты собака, я собака, ты собака, " +
                    "я собака, ты собака, я собака, ты собака, я собака, ты собака, я собака",
            type = NoteType.BIRTHDAY_TYPE,
            date = "25.01.22"
        )

        NoteItem(note) {}
    }
}
