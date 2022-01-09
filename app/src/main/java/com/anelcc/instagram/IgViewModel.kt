package com.anelcc.instagram

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.anelcc.instagram.data.Event
import com.anelcc.instagram.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
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


    var isInProgress = mutableStateOf(false)
    var singIn = mutableStateOf(false)
    var userData = mutableStateOf<UserData?>(null)
    var popNotification = mutableStateOf<Event<String>?>(null)

    init {
//        auth.signOut()
        val currentUser = auth.currentUser
        singIn.value = currentUser != null
        currentUser?.uid?.let { uid ->
           getUserData(uid)
        }
    }
    fun onSignUp(userName: String, email: String, pass: String) {
        isInProgress.value = true

        db.collection(USERS).whereEqualTo("username", userName).get()
            .addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    handledException(customMessage = "Username already exist")
                    isInProgress.value = false
                } else {
                    auth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                singIn.value = true
                                createOrUpdateProfile(userName = userName)
                            } else {
                                handledException(customMessage = "signed failed")
                            }
                            isInProgress.value = false
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
            isInProgress.value = true
            db.collection(USERS).document(uid).get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        it.reference.update(userData.toMap())
                            .addOnSuccessListener {
                                this.userData.value = userData
                                isInProgress.value = false
                            }
                            .addOnFailureListener {
                                handledException(customMessage = "Cannot Update user")
                                isInProgress.value =false

                            }
                    } else {
                        db.collection(USERS).document(uid).set(userData)
                        getUserData(uid)
                        isInProgress.value = false
                    }
                }
                .addOnFailureListener { exception ->
                    handledException(exception, "Cannot create user")
                    isInProgress.value = false
                }
        }
    }

    private fun getUserData(uid: String) {
        isInProgress.value = true
        db.collection(USERS).document(uid).get()
            .addOnSuccessListener {
                val user = it.toObject<UserData>()
                userData.value = user
                isInProgress.value = false
//                popNotification.value = Event("User data retrieve Successfully")
            }
            .addOnFailureListener { exception ->
                handledException(exception, "Cannot retrieve user data")
                isInProgress.value = false
            }
    }

    private fun handledException(exception: Exception? = null, customMessage: String = "") {
        exception?.printStackTrace()
        val errorMsg =  exception?.message ?: ""
        val message = if (customMessage.isEmpty()) { errorMsg } else { "$customMessage: $errorMsg" }
        popNotification.value = Event(message)
    }

}