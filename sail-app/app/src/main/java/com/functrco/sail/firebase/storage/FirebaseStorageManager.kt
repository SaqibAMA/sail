package com.functrco.sail.firebase.storage

import android.content.ContentResolver
import android.content.res.Resources
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

object FirebaseStorageManager {

    suspend fun uploadImage(resourceId: Int, destDir: String, resources: Resources): String {
        val imageUri = getResourceUri(resourceId, resources)
        Log.d(TAG, imageUri.toString())

        val taskSnapshot = storageRef.child(destDir).child(imageUri.lastPathSegment!!).putFile(imageUri)
            .addOnFailureListener { e ->
                Log.w(TAG, "Image upload task was unsuccessful.", e)
            }.await()


        val uploadedImageUrl = taskSnapshot.metadata!!.reference!!.downloadUrl
            .addOnFailureListener { e ->
                Log.w(TAG, "Image upload task was unsuccessful.", e)
            }.await().toString()

        return uploadedImageUrl
    }

    private fun getResourceUri(resourceId: Int, resources: Resources): Uri {
        return Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(resources.getResourcePackageName(resourceId))
            .appendPath(resources.getResourceTypeName(resourceId))
            .appendPath(resources.getResourceEntryName(resourceId))
            .build()
    }

    private var storageRef = FirebaseStorage.getInstance().reference
    private val TAG = FirebaseStorageManager::class.java.name

}