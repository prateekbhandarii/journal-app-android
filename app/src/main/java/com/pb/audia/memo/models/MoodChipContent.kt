package com.pb.audia.memo.models

import com.pb.audia.R
import com.pb.audia.core.presentation.util.UiText

data class MoodChipContent(
    val iconRes: List<Int> = emptyList(),
    val title: UiText = UiText.StringResource(R.string.all_moods)
)