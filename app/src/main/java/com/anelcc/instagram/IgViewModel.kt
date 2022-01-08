package com.anelcc.instagram

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.anelcc.instagram.data.Event
import com.anelcc.instagram.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

const val USERS = "users"

@HiltViewModel
class IgViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db : FirebaseFirestore,
    val storage: FirebaseStorage
) : ViewModel() {


    private var isProgress = mutableStateOf(false)
    var singIn = mutableStateOf(false)
    var userData = mutableStateOf<UserData?>(null)
    var popNotification = mutableStateOf<Event<String>?>(null)

    fun onSignUp(userName: String, email: String, pass: String) {
        isProgress.value = true

        db.collection(USERS).whereEqualTo("username", userName).get()
            .addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    handledException(customMessage = "Username already exist")
                    isProgress.value = false
                } else {
                    auth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                singIn.value = true
                                createOrUpdateProfile(userName = userName)
                            } else {
                                handledException(customMessage = "signed failed")
                            }
                            isProgress.value = false
                        }
                        .addOnFailureListener {  }
                }
            }
            .addOnFailureListener {  }
    }

    private fun createOrUpdateProfile(
        name: String? = null,
        userName: String? = null,
        bio: String? = null,
        imageUrl: String? = null) {
        val uid = auth.currentUser?.uid
        val userData = UserData(
            userId = uid,
            name = name ?: userData.value?.name,
            username = userName ?: userData.value?.username,
            imageURL = imageUrl ?: userData.value?.imageURL,
            bio = bio ?: userData.value?.bio,
            following = userData.value?.following
        )
        uid?.let {
            isProgress.value = true
            db.collection(USERS).document(uid).get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        it.reference.update(userData.toMap())
                            .addOnSuccessListener {
                                this.userData.value = userData
                                isProgress.value = false
                            }
                            .addOnFailureListener {
                                handledException(customMessage = "Cannot Update user")
                                isProgress.value =false

                            }
                    } else {
                        db.collection(USERS).document(uid).set(userData)
                        getUserData(uid)
                        isProgress.value = false
                    }
                }
                .addOnFailureListener { exception ->
                    handledException(exception, "Cannot create user")
                    isProgress.value = false
                }
        }
    }

    private fun getUserData(uid: String) {


    }

    private fun handledException(exception: Exception? = null, customMessage: String = "") {
        exception?.printStackTrace()
        val errorMsg =  exception?.message ?: ""
        val message = if (customMessage.isEmpty()) { errorMsg } else { "$customMessage: $errorMsg" }
        popNotification.value = Event(message)
    }

}