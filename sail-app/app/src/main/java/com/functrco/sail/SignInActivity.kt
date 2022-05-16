package com.functrco.sail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.functrco.sail.models.UserModel
import com.functrco.sail.firebase.firebase_repository.UserRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception


class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private companion object {
        private const val RC_SIGN_IN = 100                  // returned from google auth when success
        private const val TAG = "GOOGLE_SIGN_IN_TAG"        // for debugging purposes
    }


    // helper function that tells if user is connected to internet or not
    private fun haveNetworkConnection(): Boolean {
        var haveConnectedWifi = false
        var haveConnectedMobile = false
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.allNetworkInfo
        for (ni in netInfo) {
            if (ni.typeName.equals(
                    "WIFI",
                    ignoreCase = true
                )
            ) if (ni.isConnected) haveConnectedWifi = true
            if (ni.typeName.equals(
                    "MOBILE",
                    ignoreCase = true
                )
            ) if (ni.isConnected) haveConnectedMobile = true
        }
        return haveConnectedWifi || haveConnectedMobile
    }

    // handles cases where user may not be connected to internet
    private fun handleConnectionCoverage() {
        // if the user is not connected to internet, show an error
        findViewById<TextView>(R.id.login_connection_error).apply {
            visibility = if (haveNetworkConnection()) View.GONE else View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        handleConnectionCoverage()

        // initializing firebase app
        FirebaseApp.initializeApp(this)

        // Setting up options for Google authentication
        // Don't worry about default_web_client_id being red. It shows up like this for no reason.
        val googleSignInOptions = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        auth = FirebaseAuth.getInstance()

        // This function redirects the user to next page if they are already logged in.
        checkUser()

        // binding our custom sign-in button to auth function
        val signInButton = findViewById<Button>(R.id.sign_in_btn)
        signInButton.setOnClickListener {
            if (haveNetworkConnection()) {
                val i = googleSignInClient.signInIntent
                startActivityForResult(i, RC_SIGN_IN)
            }
            else {
                handleConnectionCoverage()
            }
        }
    }

    private fun checkUser() {
        val firebaseUser = auth.currentUser
        if (firebaseUser != null) {
            val i = Intent(this, MainActivity::class.java)
            i.putExtra("name", firebaseUser.displayName)
            i.putExtra("dp", firebaseUser.photoUrl)
            i.putExtra("email", firebaseUser.email)
            startActivity(i)
            finish()
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

                GlobalScope.launch {
                    UserRepository().insert(uid!!, UserModel(firebaseUser.displayName, firebaseUser.phoneNumber, email, firebaseUser.photoUrl.toString()))
                }



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
                checkUser()
            }
            .addOnFailureListener { e ->
                // Error handling
                Log.d(TAG, "firebaseWithGoogleAccount: Login failed with ${e.message}")
            }

    }

}