package com.pb.audia.app.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationRoute {

    @Serializable
    data object Memos: NavigationRoute

    @Serializable
    data class CreateMemo(
        val recordingPath: String,
        val duration: Long,
        val amplitudes: String,
    ): NavigationRoute
}
