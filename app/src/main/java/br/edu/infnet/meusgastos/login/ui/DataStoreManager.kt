package br.edu.infnet.meusgastos.login.ui

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class DataStoreManager(context: Context) {

    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "userSettings")
    private val dataStore = context.dataStore

    companion object{
        val emailKey = stringPreferencesKey("EMAIL_KEY")
        val passwordKey = stringPreferencesKey("PASSWORD_KEY")
        val checkBoxKey = booleanPreferencesKey("REMEMBER_KEY")
    }

    private suspend fun saveData(key: String, value: String){
        val prefsKey = stringPreferencesKey(key)
        dataStore.edit { userSettings ->
            userSettings[prefsKey] = value
        }
    }

    private suspend fun readData(key: String): String?{
        val prefsKey = stringPreferencesKey(key)
        val prefs = dataStore.data.first()
        return prefs.get(prefsKey)
    }

}