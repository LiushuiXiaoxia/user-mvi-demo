package com.example.user_mvi_demo.user

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UserViewModel(
    val repository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState

    fun handleIntent(intent: UserUiIntent) {
        when (intent) {
            is UserUiIntent.LoadUsers -> loadUsers()
            is UserUiIntent.SelectUser -> showUserDetail(intent.user)
            is UserUiIntent.CloseDialog -> closeDialog()
            is UserUiIntent.EditUser -> editUser(intent.updateUser)
            is UserUiIntent.SaveUser -> confirmSave()
            is UserUiIntent.DeleteUser -> confirmDelete()
            is UserUiIntent.ShowConfirm -> showConfirm(intent.action)
            is UserUiIntent.ConfirmActionYes -> doConfirmAction()
            is UserUiIntent.ConfirmActionNo -> cancelConfirm()
        }
    }

    private fun loadUsers() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        val users = repository.getAllUsers()
        _uiState.value = _uiState.value.copy(
            users = users,
            isLoading = false,
        )
        _uiState.update {
            it.copy(
                users = users,
                isLoading = false,
            )
        }
    }

    private fun showUserDetail(user: User) {
        _uiState.value = _uiState.value.copy(
            selectUser = user,
            isDialogVisible = true,
            isEditing = false
        )
    }

    private fun closeDialog() {
        _uiState.value = _uiState.value.copy(
            isDialogVisible = false,
        )
    }

    private fun editUser(user: User) {
        _uiState.value = _uiState.value.copy(
            selectUser = user,
            isEditing = true
        )
    }

    private fun confirmSave() {
        _uiState.value = _uiState.value.copy(
            isConfirmVisible = true,
            confirmAction = ConfirmAction.SAVE
        )
    }

    private fun confirmDelete() {
        _uiState.value = _uiState.value.copy(
            isConfirmVisible = true,
            confirmAction = ConfirmAction.DELETE
        )
    }

    private fun showConfirm(action: ConfirmAction?) {
        _uiState.value = _uiState.value.copy(
            isConfirmVisible = true,
            confirmAction = action
        )
    }

    private fun doConfirmAction() {
        val current = _uiState.value
        when (current.confirmAction) {
            ConfirmAction.SAVE -> {
                current.selectUser?.let { repository.saveUser(it) }
            }

            ConfirmAction.DELETE -> {
                current.selectUser?.let { repository.deleteUser(it.id) }
            }

            null -> {}
        }

        _uiState.value = _uiState.value.copy(
            isDialogVisible = false,
            isConfirmVisible = false,
            confirmAction = null,
            users = repository.getAllUsers(),
        )
    }

    private fun cancelConfirm() {
        _uiState.value = _uiState.value.copy(
            isConfirmVisible = false,
            confirmAction = null
        )
    }
}