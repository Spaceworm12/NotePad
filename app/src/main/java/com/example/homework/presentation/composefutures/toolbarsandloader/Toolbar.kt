package com.example.homework.presentation.composefutures.toolbarsandloader

import NotesTheme
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.homework.presentation.composefutures.ThemeSettings

@Composable
fun Toolbar(
    title: String,
    subtitle: String? = null,
    actions: @Composable RowScope.() -> Unit = {},
    elevation: Dp = AppBarDefaults.TopAppBarElevation,
    isBackArrowVisible: Boolean = true,
    onBackClick: () -> Unit
) {
    TopAppBar(
        modifier = Modifier
            .height(80.dp)
            .shadow(10.dp, RoundedCornerShape(bottomEnd = 15.dp, bottomStart = 15.dp)),
        title = {
            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    style = NotesTheme.typography.body1,
                    fontSize = 20.sp
                )
                if (subtitle?.isNotBlank() == true) {
                    Text(
                        style = NotesTheme.typography.subtitle1,
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 15.sp,
                        text = subtitle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = NotesTheme.colors.onPrimary
                    )
                }

            }
        },
        actions = actions,
        navigationIcon = {
            if (isBackArrowVisible)
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
        },
        backgroundColor = NotesTheme.colors.secondary,
        contentColor = NotesTheme.colors.onPrimary,
        elevation = elevation
    )
}

@Preview(name = "Toolbar", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ToolbarPreview() {
    ThemeSettings() {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Toolbar(
                title = "title",
                subtitle = "subtitle",
                actions = {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = null
                    )
                },
                onBackClick = {}
            )

            Toolbar(
                title = "title without subtitle",
                actions = {
                    Text(text = "action", color = Color.Green)
                },
                onBackClick = {}
            )
            Toolbar(
                title = "title without action",
                onBackClick = {}
            )
            Toolbar(
                title = "just title",
                isBackArrowVisible = false,
                onBackClick = {}
            )
        }
    }
}
