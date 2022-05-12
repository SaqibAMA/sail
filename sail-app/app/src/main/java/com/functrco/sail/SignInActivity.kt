package com.functrco.sail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private companion object {
        private const val RC_SIGN_IN = 100                  // returned from google auth when success
        private const val TAG = "GOOGLE_SIGN_IN_TAG"        // for debugging purposes
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Setting up options for Google authentication
        // Don't worry about default_web_client_id being red. It shows up like this for no reason.
        val googleSignInOptions = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        auth = FirebaseAuth.getInstance()

        // This function redirects the user to next page if they are already logged in.
        checkUser()

        // binding our custom sign-in button to auth function
        val signInButton = findViewById<Button>(R.id.sign_in_btn)
        signInButton.setOnClickListener {
            val i = googleSignInClient.signInIntent
            startActivityForResult(i, RC_SIGN_IN)
        }

    }

    private fun checkUser() {
        val firebaseUser = auth.currentUser
        if (firebaseUser != null) {
            // redirect to profile
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Gets the response back from google auth and handles it.
        if (requestCode == RC_SIGN_IN) {
            Log.d(TAG, "onActivityResult: Google Sign In Intent Result")
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)
            }
            catch (e: Exception) {
                Log.d(TAG, "onActivityResult $e")
            }
        }
    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {
        Log.d(TAG, "firebaseAuthWithGoogleAccount: begin firebase auth")

        // Extract credentials that came with google sign in.
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                Log.d(TAG, "firebaseAuthWithGoogleAccount: success")
                val firebaseUser = auth.currentUser
                val uid = firebaseUser?.uid
                val email = firebaseUser?.email

                Log.d(TAG, "firebaseWithGoogleAccount: $uid")
                Log.d(TAG, "firebaseWithGoogleAccount: $email")

                // Executed when the user has never signed up with our app before
                if (authResult.additionalUserInfo?.isNewUser == true) {
                    Log.d(TAG, "firebaseWithGoogleAccount: Account created... $email")
                    Log.d(TAG, "firebaseWithGoogleAccount: Account created... $uid")
                }
                else {
                    // Executed when it's a returning user
                    Log.d(TAG, "firebaseWithGoogleAccount: Logged in... $email")
                    Log.d(TAG, "firebaseWithGoogleAccount: Logged in... $uid")
                }
            }
            .addOnFailureListener { e ->
                // Error handling
                Log.d(TAG, "firebaseWithGoogleAccount: Login failed with ${e.message}")
            }

    }

}