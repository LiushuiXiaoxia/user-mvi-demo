package com.example.user_mvi_demo.user


data class User(
    val id: Int,
    val name: String,
    val address: String,
)

data class UserUiState(
    val users: List<User> = emptyList(),
    val selectUser: User? = null,
    val isDialogVisible: Boolean = false,
    val isConfirmVisible: Boolean = false,
    val confirmAction: ConfirmAction? = null,
    val isEditing: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)

sealed class UserUiIntent {
    data object LoadUsers : UserUiIntent()
    data class SelectUser(val user: User) : UserUiIntent()
    data object CloseDialog : UserUiIntent()

    data class EditUser(val updateUser: User) : UserUiIntent()
    data object SaveUser : UserUiIntent()
    data object DeleteUser : UserUiIntent()

    data class ShowConfirm(val action: ConfirmAction) : UserUiIntent()
    data object ConfirmActionYes : UserUiIntent()
    data object ConfirmActionNo : UserUiIntent()
}

enum class ConfirmAction {
    SAVE, DELETE
}