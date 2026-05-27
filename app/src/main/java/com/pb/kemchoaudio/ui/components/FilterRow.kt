@file:OptIn(ExperimentalLayoutApi::class)

package com.pb.kemchoaudio.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.pb.kemchoaudio.R
import com.pb.kemchoaudio.core.presentation.designsystem.chips.MultiChoiceChip
import com.pb.kemchoaudio.core.presentation.designsystem.dropdowns.Selectable
import com.pb.kemchoaudio.core.presentation.designsystem.dropdowns.SelectableDropDownOptionsMenu
import com.pb.kemchoaudio.core.presentation.util.UiText
import com.pb.kemchoaudio.ui.list_screen.AudioFilterType
import com.pb.kemchoaudio.ui.list_screen.ListScreenAction
import com.pb.kemchoaudio.ui.models.MoodChipContent
import com.pb.kemchoaudio.ui.models.MoodUi

@Composable
fun FilterRow(
    modifier: Modifier = Modifier,
    moodChipContent: MoodChipContent,
    hasActiveMoodFilters: Boolean,
    hasActiveTopicFilters: Boolean,
    selectedAudioFilterChip: AudioFilterType?,
    moods: List<Selectable<MoodUi>>,
    topicChipTitle: UiText,
    topics: List<Selectable<String>>,
    onAction: (ListScreenAction) -> Unit,
) {
    val context = LocalContext.current

    var dropdownOffset by remember {
        mutableStateOf(IntOffset.Zero)
    }

    val configuration = LocalConfiguration.current
    val dropdownMaxHeight = (configuration.screenHeightDp * 0.3f).dp

    FlowRow(
        modifier = modifier
            .padding(16.dp)
            .onGloballyPositioned {
                dropdownOffset = IntOffset(0, it.size.height)
            },
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        //Mood chip
        MultiChoiceChip(
            displayText = moodChipContent.title.asString(),
            onClick = {
                onAction(ListScreenAction.OnMoodChipClick)
            },
            leadingContent = {
                if (moodChipContent.iconRes.isNotEmpty()) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy((-4).dp),
                        modifier = Modifier.padding(end = 4.dp)
                    ) {
                        moodChipContent.iconRes.forEach { iconRes ->
                            Image(
                                imageVector = ImageVector.vectorResource(iconRes),
                                contentDescription = moodChipContent.title.asString(),
                                modifier = Modifier.height(16.dp)
                            )
                        }
                    }
                }
            },
            isClearVisible = hasActiveMoodFilters,
            isDropDownVisible = selectedAudioFilterChip == AudioFilterType.MOODS,
            isHighlighted = hasActiveMoodFilters || selectedAudioFilterChip == AudioFilterType.MOODS,
            onClearButtonClick = {
                onAction(ListScreenAction.OnRemoveFilters(AudioFilterType.MOODS))
            },
            dropDownMenu = {
                SelectableDropDownOptionsMenu(
                    items = moods,
                    itemDisplayText = { moodUi -> moodUi.title.asString(context) },
                    onDismiss = {
                        onAction(ListScreenAction.OnDismissMoodChipDropdown)
                    },
                    key = { moodUi -> moodUi },
                    onItemClick = { moodUi ->
                        onAction(ListScreenAction.OnFilterByMoodClick(moodUi.item))
                    },
                    dropDownOffset = dropdownOffset,
                    maxDropDownHeight = dropdownMaxHeight,
                    leadingIcon = {
                        Image(
                            imageVector = ImageVector.vectorResource(it.iconSet.filled),
                            contentDescription = it.title.asString(context)
                        )
                    })
            })

        //topics chip
        MultiChoiceChip(
            displayText = topicChipTitle.asString(),
            onClick = {
                onAction(ListScreenAction.OnTopicChipClick)
            },
            isClearVisible = hasActiveTopicFilters,
            isDropDownVisible = selectedAudioFilterChip == AudioFilterType.TOPICS,
            isHighlighted = hasActiveTopicFilters || selectedAudioFilterChip == AudioFilterType.TOPICS,
            onClearButtonClick = {
                onAction(ListScreenAction.OnRemoveFilters(AudioFilterType.TOPICS))
            },
            dropDownMenu = {
                if (topics.isEmpty()) {
                    SelectableDropDownOptionsMenu(
                        items = listOf(
                            Selectable(
                                item = context.getString(R.string.you_don_t_have_any_topics_yet),
                                selected = false
                            )
                        ),
                        itemDisplayText = { it },
                        onDismiss = {
                            onAction(ListScreenAction.OnDismissTopicChipDropdown)
                        },
                        key = { it },
                        onItemClick = { },
                        dropDownOffset = dropdownOffset,
                        maxDropDownHeight = dropdownMaxHeight,
                    )
                } else {
                    SelectableDropDownOptionsMenu(
                        items = topics,
                        itemDisplayText = { topic -> topic },
                        onDismiss = {
                            onAction(ListScreenAction.OnDismissTopicChipDropdown)
                        },
                        key = { topic -> topic },
                        onItemClick = { topic ->
                            onAction(ListScreenAction.OnFilterByTopicClick(topic.item))
                        },
                        dropDownOffset = dropdownOffset,
                        maxDropDownHeight = dropdownMaxHeight,
                        leadingIcon = {
                            Image(
                                imageVector = ImageVector.vectorResource(R.drawable.hashtag),
                                contentDescription = it
                            )
                        })
                }
            })
    }
}