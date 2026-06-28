package com.example.beautifulstrugglefoundation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey

@Composable
actual inline fun <reified T : NavKey> NavDisplayWrapper(
    backstack: SnapshotStateList<NavKey>,
    noinline onBack: () -> Unit,
    noinline content: @Composable (T) -> Unit
) {
    val key = backstack.lastOrNull() as? T
    if (key != null) {
        content(key)
    }
}
