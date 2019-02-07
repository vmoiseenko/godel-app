package com.godeltech.simpleapp.utils

import android.util.Patterns

class ValidatorImpl : Validator {
    override fun isUrlValid(url: String): Boolean {
        return Patterns.WEB_URL.matcher(url).matches()
    }
}