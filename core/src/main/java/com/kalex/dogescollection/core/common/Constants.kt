package com.kalex.dogescollection.core.common

object Constants {
    const val BASE_URL = "https://todogs.herokuapp.com/api/v1/"

    const val GET_ALL_DOGS_URL = "dogs"
    const val ADD_DOG_TO_USER_URL = "add_dog_to_user"
    const val GET_USER_DOGS_URL = "get_user_dogs"
    const val CREATE_ACCOUNT_URL = "sign_up"
    const val LOGIN_URL = "sign_in"
    const val GET_DOG_BY_ML_ID = "find_dog_by_ml_id"
    const val GET_PREDICTED_QUERY = "ml_id"

    const val MAX_RECOGNITION_DOG_RESULTS = 5
    const val MODEL_PATH = "model.tflite"
    const val LABEL_PATH = "labels.txt"
}
