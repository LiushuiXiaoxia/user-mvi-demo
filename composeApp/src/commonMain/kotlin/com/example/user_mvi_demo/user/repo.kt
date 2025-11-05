package com.example.user_mvi_demo.user

class UserRepository {
    private val list = mutableListOf(
        User(1, "555", "Shanghai"),
        User(2, "Bob", "Beijing"),
        User(3, "Charlie", "Shenzhen")
    )

    fun getAllUsers(): List<User> = list.toList()
    fun saveUser(user: User) {
        val index = list.indexOfFirst { it.id == user.id }
        if (index >= 0) list[index] = user
    }

    fun deleteUser(id: Int) {
        list.removeAll { it.id == id }
    }
}