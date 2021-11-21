package com.butterflies.stepaw.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.butterflies.stepaw.network.models.UserModel
import com.google.gson.Gson
import java.util.regex.Pattern

class StepawUtils {
    private val emailAddressPattern: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun validateEmail(email: String): Boolean {
        return emailAddressPattern.matcher(email).matches();
    }

    fun validatePassword(): Boolean = true


    fun extractUser(context:Context): UserModel? {
        val p = context.getSharedPreferences("com.butterflies.stepaw", Context.MODE_PRIVATE)
        val u = p.getString("com.butterflies.stepaw.user", "invalid")
        val j = Gson()
        val d = j.fromJson(u, UserModel::class.java)
      Toast.makeText(context, d.FirstName, Toast.LENGTH_SHORT).show()
        return d
    }



    fun storePreferences(context:Context,key: String, token: String) {
        val sharedPreferences = context.getSharedPreferences("com.butterflies.stepaw", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(key, token)
            apply()
        }
    }


}