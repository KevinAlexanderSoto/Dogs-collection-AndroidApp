package com.kalex.dogescollection.authentication

object RegexPatterns {
    const val PASSWORD_REGEX_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{5,}$"
}
