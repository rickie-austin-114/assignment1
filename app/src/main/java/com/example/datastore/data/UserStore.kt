package com.example.datastore.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userToken")
        private val USER_ID = stringPreferencesKey("id")
        private val USER_NAME = stringPreferencesKey("username")
        private val USER_COURSE_NAME = stringPreferencesKey("course_name")

    }

    suspend fun reset() {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = ""
            preferences[USER_NAME] = ""
            preferences[USER_COURSE_NAME] = ""
        }
    }


    val getId: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_ID] ?: ""
    }

    suspend fun saveId(id: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = id
        }
    }

    val getUserName: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_NAME] ?: ""
    }

    suspend fun saveUserName(user_name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = user_name
        }
    }

    val getCourseName: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_COURSE_NAME] ?: ""
    }

    suspend fun saveCourseName(course_name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_COURSE_NAME] = course_name
        }
    }
}