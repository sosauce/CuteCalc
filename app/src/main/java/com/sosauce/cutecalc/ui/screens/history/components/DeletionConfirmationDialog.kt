package com.sosauce.cutecalc.ui.screens.history.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.sosauce.cutecalc.R
import com.sosauce.cutecalc.ui.shared_components.CuteText

@Composable
fun DeletionConfirmationDialog(
    onDismissRequest: () -> Unit,
    onDelete: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { CuteText(stringResource(R.string.clear_history)) },
        text = { CuteText(stringResource(R.string.cant_be_undone)) },
        confirmButton = {
            TextButton(
                onClick = {
                    onDelete()
                    onDismissRequest()
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                CuteText(stringResource(R.string.delete))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                CuteText(stringResource(R.string.cancel))
            }
        },
        icon = {
            Icon(
                painter = painterResource(R.drawable.delete),
                contentDescription = null
            )
        }
    )
}