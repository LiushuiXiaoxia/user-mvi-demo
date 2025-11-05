package com.example.user_mvi_demo.user

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserScreen(
    viewModel: UserViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.handleIntent(UserUiIntent.LoadUsers)
    }

    Column {
        Text(text = "User List", style = MaterialTheme.typography.titleLarge)

        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            items(uiState.users) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .clickable {
                                viewModel.handleIntent(UserUiIntent.SelectUser(it))
                            },
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = it.id.toString())
                        Text(text = it.name, modifier = Modifier.weight(1f))
                        Text(text = it.address)
                    }
                }
                Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.Red))
            }
        }
    }

    if (uiState.isLoading) {
        Text(text = "Loading...")
    }
    if (uiState.error != null) {
        Text(text = "Error: ${uiState.error}")
    }

    if (uiState.isDialogVisible) {
        UserDetailDialog(
            user = uiState.selectUser!!,
            isEditing = uiState.isEditing,
            onEdit = { viewModel.handleIntent(UserUiIntent.EditUser(it)) },
            onSave = { viewModel.handleIntent(UserUiIntent.SaveUser) },
            onDelete = { viewModel.handleIntent(UserUiIntent.DeleteUser) },
            onCancel = { viewModel.handleIntent(UserUiIntent.CloseDialog) }
        )
    }

    if (uiState.isConfirmVisible) {
        ConfirmDialog(
            action = uiState.confirmAction!!,
            onConfirm = { viewModel.handleIntent(UserUiIntent.ConfirmActionYes) },
            onCancel = { viewModel.handleIntent(UserUiIntent.ConfirmActionNo) }
        )
    }
}

@Composable
fun UserDetailDialog(
    user: User,
    isEditing: Boolean,
    onEdit: (User) -> Unit,
    onSave: () -> Unit,
    onDelete: () -> Unit,
    onCancel: () -> Unit,
) {
    AlertDialog(
        title = { Text(text = "User Detail") },
        onDismissRequest = onCancel,
        text = {
            Column {
                var name by remember { mutableStateOf(user.name) }
                var address by remember { mutableStateOf(user.address) }
                OutlinedTextField(value = name, onValueChange = { name = it })
                OutlinedTextField(value = address, onValueChange = { address = it })
                Row(horizontalArrangement = Arrangement.SpaceBetween) {

                    Button(onClick = {
                        onEdit(user.copy(name = name, address = address))
                    }) {
                        Text(text = "Edit")
                    }
                    Button(onClick = {
                        onSave()
                    }) {
                        Text(text = "Save")
                    }
                    Button(onClick = {
                        onDelete()
                    }) {
                        Text(text = "Delete")
                    }
                }
            }
        },
        confirmButton = {}
    )
}

@Composable
fun ConfirmDialog(
    action: ConfirmAction,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
) {
    val message = when (action) {
        ConfirmAction.SAVE -> "Are you sure you want to save this user?"
        ConfirmAction.DELETE -> "Are you sure you want to delete this user?"
    }
    AlertDialog(
        title = { Text(text = "Confirm") },
        text = { Text(text = message) },
        onDismissRequest = onCancel,
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = "Yes")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text(text = "No")
            }
        }
    )
}


@Preview
@Composable
fun UserScreenPreview() {
    UserScreen()
}