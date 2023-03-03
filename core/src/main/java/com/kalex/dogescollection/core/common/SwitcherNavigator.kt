package com.kalex.dogescollection.core.common

import com.kalex.dogescollection.core.model.data.alldogs.Dog


interface AuthenticationSwitcherNavigator {

    fun onUserAuthenticated()
    fun onCreateNewUser()
    fun onUserCreated()
}

interface CameraSwitcherNavigator{
    fun onDogRecognised(foundDog: Dog)
    fun onDogAddToCollection()
    fun onRetryRecognizeDog()
}