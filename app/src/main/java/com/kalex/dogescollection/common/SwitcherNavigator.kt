package com.kalex.dogescollection.common

import com.kalex.dogescollection.dogList.model.data.alldogs.Dog

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