package com.pb.kemchoaudio.ui.components

import android.adservices.topics.Topic
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pb.kemchoaudio.core.presentation.designsystem.dropdowns.Selectable
import com.pb.kemchoaudio.ui.list_screen.AudioFilterType
import com.pb.kemchoaudio.ui.list_screen.ListScreenAction
import com.pb.kemchoaudio.ui.models.MoodChipContent
import com.pb.kemchoaudio.ui.models.MoodUi

@Composable
fun FilterRow(
    modifier: Modifier = Modifier,
    moodChipContent: MoodChipContent,
    hasActiveMoodFilters: Boolean,
    selectedAudioFilterChip: AudioFilterType?,
    moods: List<Selectable<MoodUi>>,
    topics: List<Selectable<Topic>>,
    onAction: (ListScreenAction) -> Unit,
) {

}