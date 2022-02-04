package com.example.desafio2.SharedPreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.desafio2.R
import org.json.JSONObject

fun getSharedPrefeference(context: Context): SharedPreferences {
    return EncryptedSharedPreferences.create(context.getString(R.string.filePreferences),
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}

fun login(context: Context, data: String) {
    val sharedPreferences = getSharedPrefeference(context)
    with(sharedPreferences.edit()) {
        putString("token", data)
        apply()
    }
}

fun validateSession(context: Context): Boolean {
    val sharedPreferences = getSharedPrefeference(context)
    val token = sharedPreferences.getString("token", "")
    return !token.isNullOrEmpty()
}

fun signOut(context: Context) {
    val sharedPreferences = getSharedPrefeference(context)
    with(sharedPreferences.edit()) {
        clear()
        apply()
    }
}

fun getSessionInfo(context: Context, key: String): String {
    val sharedPreferences = getSharedPrefeference(context)
    return sharedPreferences.getString(key, "").toString()
}