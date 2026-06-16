package com.pb.audia.core.presentation.designsystem.dropdowns

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.pb.audia.R
import com.pb.audia.core.presentation.designsystem.dropdowns.Selectable.Companion.asUnselectedItems
import com.pb.audia.core.presentation.designsystem.theme.AppTheme

@Composable
fun <T> SelectableDropDownOptionsMenu(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    key: (T) -> Any,
    items: List<Selectable<T>>,
    itemDisplayText: (T) -> String,
    onItemClick: (Selectable<T>) -> Unit,
    leadingIcon: (@Composable (T) -> Unit)? = null,
    dropDownOffset: IntOffset = IntOffset.Zero,
    maxDropDownHeight: Dp = Dp.Unspecified,
    dropDownExtras: SelectableOptionExtras? = null
) {
    Popup(
        onDismissRequest = onDismiss,
        offset = dropDownOffset
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(10.dp),
            shadowElevation = 4.dp,
            modifier = modifier
                .heightIn(max = maxDropDownHeight)
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .animateContentSize()
                    .padding(6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(
                    items = items,
                    key = { key(it.item) }
                ) {
                    Row(
                        modifier = Modifier
                            .animateItem()
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                color = if (it.selected) {
                                    MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.05f)
                                } else {
                                    MaterialTheme.colorScheme.surface
                                }
                            )
                            .clickable(
                                onClick = {
                                    onItemClick(it)
                                })
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        leadingIcon?.invoke(it.item)
                        Text(
                            text = itemDisplayText(it.item),
                            modifier = Modifier.weight(1f)
                        )
                        if (it.selected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                }

                if (dropDownExtras != null && dropDownExtras.text.isNotEmpty()) {
                    item(key = true) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surface)
                                .clickable {
                                    dropDownExtras.onClick()
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .size(18.dp)
                            )

                            Text(
                                text = stringResource(R.string.create_entry, dropDownExtras.text),
                                modifier = Modifier.weight(1f),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SelectableDropDownOptionsMenuPreview() {
    AppTheme {
        SelectableDropDownOptionsMenu(
            items = (1..5).map { "Hello $it" }.asUnselectedItems(),
            onDismiss = {},
            onItemClick = {},
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.hashtag),
                    contentDescription = null
                )
            },
            maxDropDownHeight = 500.dp,
            dropDownExtras = SelectableOptionExtras(
                text = "Add new",
                onClick = {}
            ),
            itemDisplayText = { it },
            key = { it }
        )
    }
}