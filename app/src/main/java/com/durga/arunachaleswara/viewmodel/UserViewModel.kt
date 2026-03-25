package com.durga.arunachaleswara.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.durga.arunachaleswara.model.User
import com.durga.arunachaleswara.repo.UserRepository
import com.google.firebase.firestore.ListenerRegistration

class UserViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    var userList by mutableStateOf<List<User>>(emptyList())
        private set

    var selectedUser by mutableStateOf<User?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf("")
        private set

    var message by mutableStateOf("")
        private set

    private var listenerRegistration: ListenerRegistration? = null

    fun observeUsers() {
        isLoading = true
        errorMessage = ""

        listenerRegistration?.remove()
        listenerRegistration = repository.observeUsers(
            onResult = { users ->
                userList = users
                isLoading = false
            },
            onError = { error ->
                errorMessage = error
                isLoading = false
            }
        )
    }

    fun addUser(
        name: String,
        email: String,
        phone: String,
        date:String,
        address: String,
        onSuccess: () -> Unit
    ) {
        if (name.isBlank() || email.isBlank() || phone.isBlank() || address.isBlank()) {
            message = "Please fill all fields"
            return
        }

        repository.addUser(
            name = name,
            email = email,
            phone = phone,
            address = address,
            date = date,
            onSuccess = {
                message = "Form submitted successfully"
                onSuccess()
            },
            onFailure = { error ->
                message = error
            }
        )
    }

    fun getUserById(userId: String) {
        isLoading = true
        errorMessage = ""
        selectedUser = null

        repository.getUserById(
            userId = userId,
            onSuccess = { user ->
                selectedUser = user
                isLoading = false
            },
            onFailure = { error ->
                errorMessage = error
                isLoading = false
            }
        )
    }

    fun clearMessage() {
        message = ""
    }

    override fun onCleared() {
        super.onCleared()
        listenerRegistration?.remove()
    }
    fun updateUser(user: User) {
        if (
            user.name.isBlank() ||
            user.email.isBlank() ||
            user.phone.isBlank() ||
            user.address.isBlank()
        ) {
            message = "Please fill all fields"
            return
        }

        isLoading = true
        message = ""
        errorMessage = ""

        repository.updateUser(
            user = user,
            onSuccess = { successMessage ->
                message = successMessage
                selectedUser = user
                isLoading = false
            },
            onFailure = { error ->
                errorMessage = error
                isLoading = false
            }
        )
    }
}