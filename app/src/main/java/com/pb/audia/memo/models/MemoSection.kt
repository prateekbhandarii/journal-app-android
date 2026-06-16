package com.pb.audia.memo.models

import com.pb.audia.core.presentation.util.UiText

data class MemoSection(
    val dateHeader: UiText,
    val memos: List<MemoUi>
)