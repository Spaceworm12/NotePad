package com.example.homework.presentation.recycler.notelistcomponents

import NotesTheme
import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.homework.presentation.composefutures.ThemeSettings
import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.model.NoteType
import com.example.homework.presentation.recycler.ListEvents


@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun EventItem(note: NoteModel, onUiEvent: (ListEvents) -> Unit) {
    Card(
        modifier = Modifier
            .padding(NotesTheme.dimens.halfContentMargin)
            .padding(NotesTheme.dimens.sideMargin)
            .combinedClickable(
                onClick = {
                    onUiEvent.invoke(ListEvents.SaveCurrentNote(note))
                    onUiEvent.invoke(ListEvents.ShowChangeDialog(true, note))
                },
                onLongClick = {
                    onUiEvent(
                        ListEvents.ShowDeleteDialog(
                            true,
                            note.id
                        )
                    )
                },
            ),
        shape = NotesTheme.shapes.xlLarge,
        backgroundColor = NotesTheme.colors.secondary,
        elevation = NotesTheme.dimens.contentMargin,
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(NotesTheme.dimens.contentMargin)
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = NotesTheme.dimens.halfContentMargin, end = NotesTheme.dimens.halfContentMargin),
                text = note.name,
                style = NotesTheme.typography.h6,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = NotesTheme.dimens.halfContentMargin, end = NotesTheme.dimens.halfContentMargin),
                text = note.description,
                style = NotesTheme.typography.body1,
                softWrap = true,
                overflow = TextOverflow.Clip
            )

            Text(
                modifier = Modifier
                    .padding(NotesTheme.dimens.halfContentMargin)
                    .fillMaxWidth()
                    .background(
                        NotesTheme.colors.primaryVariant,
                        shape = RoundedCornerShape(NotesTheme.dimens.inputsMargin)
                    )
                    .padding(NotesTheme.dimens.sideMargin),
                text = note.date,
                color = NotesTheme.colors.onPrimary,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview(name = "EventItem", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun EventItemPreview() {
    ThemeSettings {

        val note = NoteModel(
            id = 0,
            name = "Заметка",
            description = "Ты собака, я собака, ты собака, я собака, ты собака, я собака, ты собака, " +
                    "я собака, ты собака, я собака, ты собака, я собака, ты собака, я собака в фы вфы вфы в фы вфы вф ы вфы вфы в" +
                    " вфы вфы вфы фы вфы вфывфывфывфывфывфыв" +
                    "фывфывфывввввввввввввввввввввввв   ывффффффффффффффффффффффф выфвфывввввввввввввввввввввввввввввввв выв",
            type = NoteType.BIRTHDAY_TYPE,
            date = "25.01.22"
        )

        EventItem(note) {}
    }
}
