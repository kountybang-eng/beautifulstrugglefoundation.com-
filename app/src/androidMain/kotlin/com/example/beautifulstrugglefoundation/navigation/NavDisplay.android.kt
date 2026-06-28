package com.example.beautifulstrugglefoundation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay

@Composable
actual inline fun <reified T : NavKey> NavDisplayWrapper(
    backstack: SnapshotStateList<NavKey>,
    noinline onBack: () -> Unit,
    noinline content: @Composable (T) -> Unit
) {
    NavDisplay(
        backStack = backstack as List<T>,
        onBack = onBack,
        entryProvider = { key: T ->
            NavEntry(key) {
                content(key)
            }
        }
    )
}
