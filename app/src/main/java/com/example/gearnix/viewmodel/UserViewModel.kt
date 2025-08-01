package com.example.gearnix.viewmodel



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gearnix.model.UserModel
import com.example.gearnix.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel(val repo : UserRepository) : ViewModel() {



    fun updateProfile(userID : String, userData: MutableMap<String, Any?>, callback : (Boolean, String) ->Unit) {
        repo.updateProfile(userID, userData, callback)
    }

    fun getUserData(email: String, callback: (UserModel?) -> Unit) {
        // Replace with your actual implementation to fetch user data
        // For Firebase example:
        /*
        firestore.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val userData = documents.first().toObject(UserModel::class.java)
                    callback(userData)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                callback(null)
            }
        */

        // Temporary simulation - replace with actual implementation
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            // Return sample data for testing
            val userData = UserModel(
                fullName = "Gaming Pro", // This should come from your database
                email = email,
                phoneNumber = "+1234567890", // This should come from your database
                address = "123 Gaming Street" // This should come from your database
            )
            callback(userData)
        }, 500)
    }

}