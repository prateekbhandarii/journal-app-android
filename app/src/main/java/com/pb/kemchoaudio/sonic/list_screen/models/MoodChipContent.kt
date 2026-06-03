package com.pb.kemchoaudio.sonic.list_screen.models

import com.pb.kemchoaudio.R
import com.pb.kemchoaudio.core.presentation.util.UiText

data class MoodChipContent(
    val iconRes: List<Int> = emptyList(),
    val title: UiText = UiText.StringResource(R.string.all_moods)
)