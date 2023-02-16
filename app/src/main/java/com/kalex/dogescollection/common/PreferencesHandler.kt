package com.kalex.dogescollection.common

import android.app.Activity
import android.content.Context
import com.kalex.dogescollection.api.ApiServiceInterceptor
import com.kalex.dogescollection.authentication.model.dto.User
import javax.inject.Inject

class PreferencesHandler @Inject constructor(
    private val activity: Activity
){
companion object {
    private const val AUTH_PREFS = "auth_prefs"
    private const val ID_KEY = "id"
    private const val EMAIL_KEY = "email"
    private const val AUTH_TOKEN_KEY = "authenticationToken"
}

    fun setLoggedInUser( user: User) {
        ApiServiceInterceptor.setCurrentToken(user.authenticationToken)
        activity.getSharedPreferences(AUTH_PREFS, Context.MODE_PRIVATE).also {
            it.edit()
                .putLong(ID_KEY, user.id)
                .putString(EMAIL_KEY, user.email)
                .putString(AUTH_TOKEN_KEY, user.authenticationToken)
                .apply()

        }
    }

    fun getLoggedInUser(): User? {
        val prefs =
            activity.getSharedPreferences(AUTH_PREFS, Context.MODE_PRIVATE) ?: return null
        val currentId = prefs.getLong(ID_KEY, 0)
        if (currentId == 0L) return null
        return User(
            authenticationToken = prefs.getString(AUTH_TOKEN_KEY, "") ?: "",
            email = prefs.getString(EMAIL_KEY, "") ?: "",
            id = currentId
        )
    }

    fun onLogOutUser(){
        ApiServiceInterceptor.setCurrentToken(null)
        activity.getSharedPreferences(
            AUTH_PREFS,
            Context.MODE_PRIVATE
        ).also {
            it.edit().clear().apply()
        }
    }
}