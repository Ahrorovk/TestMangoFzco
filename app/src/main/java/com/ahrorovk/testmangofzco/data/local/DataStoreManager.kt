package com.ahrorovk.testmangofzco.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("preferences_name")
        val AVATAR_FILENAME_KEY =stringPreferencesKey("avatar_filename_key")
        val AVATAR_BASE64_KEY =stringPreferencesKey("avatar_base64_key")
        val BIRTHDAY_KEY=stringPreferencesKey("birthday_key")
        val CITY_KEY= stringPreferencesKey("city_key")
        val INSTAGRAM_KEY=stringPreferencesKey("instagram_key")
        val NAME_KEY=stringPreferencesKey("name_key")
        val STATUS_KEY=stringPreferencesKey("status_key")
        val USERNAME_KEY=stringPreferencesKey("username_key")
        val VK_KEY=stringPreferencesKey("vk_key")
        val REGISTER_PHONE_KEY = stringPreferencesKey("register_phone")
        val AUTH_CODE_KEY = stringPreferencesKey("auth_code")
        val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
        val ACCESS_TOKEN_LIFE_KEY = longPreferencesKey("access_token_life")
    }
    suspend fun updateRegisterPhone(phone: String) {
        context.dataStore.edit { preferences ->
            preferences[REGISTER_PHONE_KEY] = phone
        }
    }

    suspend fun updateAvatarFilename(avatar: String) {
        context.dataStore.edit { preferences ->
            preferences[AVATAR_FILENAME_KEY] = avatar
        }
    }
    suspend fun updateAvatarBase64(base64: String) {
        context.dataStore.edit { preferences ->
            preferences[AVATAR_BASE64_KEY] = base64
        }
    }
    suspend fun updateBirthday(birthday: String) {
        context.dataStore.edit { preferences ->
            preferences[BIRTHDAY_KEY] = birthday
        }
    }
    suspend fun updateCity(city: String) {
        context.dataStore.edit { preferences ->
            preferences[CITY_KEY] = city
        }
    }
    suspend fun updateInstagram(instagram: String) {
        context.dataStore.edit { preferences ->
            preferences[INSTAGRAM_KEY] = instagram
        }
    }
    suspend fun updateName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
            Log.e("NAME_S", name)
        }
    }

    suspend fun updateStatus(status: String) {
        context.dataStore.edit { preferences ->
            preferences[STATUS_KEY] = status
        }
    }
    suspend fun updateUsername(username:String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
        }
    }
    suspend fun updateVK(vk: String) {
        context.dataStore.edit { preferences ->
            preferences[VK_KEY] = vk
        }
    }

    suspend fun updateAuthCode(code: String) {
        context.dataStore.edit { preferences ->
            preferences[AUTH_CODE_KEY] = code
        }
    }

    suspend fun updateAccessTokenLife(tokenLife: Long) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_LIFE_KEY] = tokenLife
        }
    }
    suspend fun updateAccessToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = token
        }
    }
    suspend fun updateRefreshToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN_KEY] = token
        }
    }
    val getRegisterPhone = context.dataStore.data.map {
        it[REGISTER_PHONE_KEY] ?: ""
    }
    val getAuthCode = context.dataStore.data.map {
        it[AUTH_CODE_KEY] ?: ""
    }
    val getAccessToken = context.dataStore.data.map {
        it[ACCESS_TOKEN_KEY] ?: ""
    }
    val getRefreshToken = context.dataStore.data.map {
        it[REFRESH_TOKEN_KEY] ?: ""
    }
    val getAccessTokenLife = context.dataStore.data.map {
        it[ACCESS_TOKEN_LIFE_KEY] ?: 0
    }
    val getName = context.dataStore.data.map {
        it[NAME_KEY] ?: ""
    }
    val getAvatar = context.dataStore.data.map {
        it[AVATAR_FILENAME_KEY] ?: ""
    }
    val getAvatarBase64 = context.dataStore.data.map {
        it[AVATAR_BASE64_KEY] ?: ""
    }
    val getBirthday = context.dataStore.data.map {
        it[BIRTHDAY_KEY] ?: ""
    }
    val getCity = context.dataStore.data.map {
        it[CITY_KEY] ?: ""
    }
    val getInstagram = context.dataStore.data.map {
        it[INSTAGRAM_KEY] ?: ""
    }
    val getStatus = context.dataStore.data.map {
        it[STATUS_KEY] ?: ""
    }
    val getUsername = context.dataStore.data.map {
        it[USERNAME_KEY] ?: ""
    }
    val getVK = context.dataStore.data.map {
        it[VK_KEY] ?: ""
    }

}