package com.example.gearnix.repository

import android.content.Context
import android.net.Uri
import com.example.gearnix.model.ProductModel

interface ProductRepository {

    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit)
    fun getFileNameFromUri(context: Context, uri: Uri): String?
}
