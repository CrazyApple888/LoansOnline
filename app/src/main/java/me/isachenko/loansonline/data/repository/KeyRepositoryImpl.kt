package me.isachenko.loansonline.data.repository

import android.content.SharedPreferences
import me.isachenko.loansonline.domain.repository.KeyRepository

class KeyRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val apiKeyPreferenceName: String
) : KeyRepository {

    override fun hasKey(): Boolean =
        sharedPreferences.contains(apiKeyPreferenceName)

    override fun getKey(): String? =
        sharedPreferences.getString(apiKeyPreferenceName, null)

    override fun saveKey(key: String) {
        sharedPreferences.edit().putString(apiKeyPreferenceName, key).apply()
    }

    override fun deleteKey() {
        sharedPreferences.edit().remove(apiKeyPreferenceName).apply()
    }
}