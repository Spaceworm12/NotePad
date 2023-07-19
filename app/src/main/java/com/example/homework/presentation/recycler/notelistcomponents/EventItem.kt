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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.homework.presentation.composefutures.ThemeSettings
import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.model.NoteType
import com.example.homework.presentation.recycler.ListEvents


@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun EventItem(note: NoteModel, onUiEvent: (ListEvents) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(NotesTheme.dimens.halfContentMargin)
            //TODO: В ресурсы
            .padding(top = 10.dp)
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
        //TODO: В ресурсы
        shape = RoundedCornerShape(30.dp),
        backgroundColor = NotesTheme.colors.secondary,
        elevation = NotesTheme.dimens.contentMargin,
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
        Box(contentAlignment = Alignment.BottomEnd) {
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    //TODO: В ресурсы
                    .padding(5.dp)
                    //TODO: В ресурсы
                    .padding(end = 25.dp)
                    .align(Alignment.CenterEnd)
            ) {
                Text(
                    modifier = Modifier
                        .drawBehind {
                            drawCircle(
                                color = Color.Black,
                                radius = this.size.maxDimension
                            )
                        }
                        .background(
                            NotesTheme.colors.rippleColor,
                            shape = RoundedCornerShape(5.dp)
                        )
                        //TODO: В ресурсы
                        .padding(10.dp),
                    text = note.date,
                    color = NotesTheme.colors.onPrimary
                )
            }
        }
    }
}