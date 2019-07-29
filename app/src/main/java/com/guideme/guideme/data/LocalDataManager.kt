package com.guideme.guideme.data

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.guideme.guideme.data.models.User

class LocalDataManager(context: Context) {

    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    companion object {
        const val USER_NAME = "user_name"
        const val USER_PHONE = "user_phone"
        const val USER_PHOTO = "user_photo"
    }

    fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        preferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun getStringField(key: String) {
        preferences.getString(key, "")
    }

    fun updateStringField(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun removeField(key: String) {
        preferences.edit().remove(key).apply()
    }

    fun getProfile(): User {
        return User(
            preferences.getString(USER_NAME, "")!!,
            preferences.getString(USER_PHONE, "")!!,
            preferences.getString(USER_PHOTO, "")
        )
    }

    fun saveProfile(name: String, phoneNumber: String, profilePhoto: String? = null) {
        val editor = preferences.edit()
        editor.putString(USER_NAME, name)
        editor.putString(USER_PHONE, phoneNumber)
        if (profilePhoto != null) editor.putString(USER_PHOTO, profilePhoto)
        editor.apply()
    }

    fun deleteProfile() {
        preferences.edit()
            .remove(USER_NAME)
            .remove(USER_PHONE)
            .remove(USER_PHOTO)
            .apply()
    }
}
