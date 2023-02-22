package com.kalex.dogescollection.common

interface AuthenticationSwitcherNavigator {

    fun onUserAuthenticated()
    fun onCreateNewUser()
    fun onUserCreated()
}