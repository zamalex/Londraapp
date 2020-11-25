package com.gemidroid.londra.utils

import android.text.TextUtils
import android.util.Patterns


object Validator {
    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target)
            .matches()
    }

    fun isValidPhone(target: CharSequence): Boolean {
        return target.matches("^(009665|9665|\\+9665|05|5)(5|0|3|6|4|9|1|8|7)([0-9]{7})$".toRegex())

    }
}