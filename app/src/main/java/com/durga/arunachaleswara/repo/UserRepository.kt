package com.durga.arunachaleswara.repo

import com.durga.arunachaleswara.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class UserRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    fun observeUsers(
        onResult: (List<User>) -> Unit,
        onError: (String) -> Unit
    ): ListenerRegistration {
        return db.collection("Users")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    onError(error.message ?: "Failed to fetch users")
                    return@addSnapshotListener
                }

                val users = value?.documents?.mapNotNull { document ->
                    document.toObject(User::class.java)
                } ?: emptyList()

                onResult(users)
            }
    }

    fun addUser(
        name: String,
        email: String,
        phone: String,
        address: String,
        date: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val docRef = db.collection("Users").document()

        val user = User(
            id = docRef.id,
            name = name,
            email = email,
            phone = phone,
            address = address,
            createdAt = date
        )

        docRef.set(user)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e ->
                onFailure(e.message ?: "Failed to add user")
            }
    }

    fun getUserById(
        userId: String,
        onSuccess: (User?) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("Users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                onSuccess(document.toObject(User::class.java))
            }
            .addOnFailureListener { e ->
                onFailure(e.message ?: "Failed to load user details")
            }
    }

    fun updateUser(
        user: User,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("Users")
            .document(user.id)
            .set(user)
            .addOnSuccessListener {
                onSuccess("User updated successfully")
            }
            .addOnFailureListener { e ->
                onFailure(e.message ?: "Failed to update user")
            }
    }
}