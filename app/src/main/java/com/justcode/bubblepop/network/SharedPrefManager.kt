package com.justcode.bubblepop.network

import android.content.Context
import com.justcode.bubblepop.LoginActivity
import com.justcode.bubblepop.MainActivity
import com.justcode.bubblepop.model.CurrentUser
import org.jetbrains.anko.startActivity

class SharedPrefManager private constructor(context: Context) {

    // check user logged in
    val isLoggedIn : Boolean
    get() {
        val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences?.getString(KEY_NAME, null) != null
    }

    // get current user
    val user: CurrentUser
    get() {
        val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return CurrentUser(
            sharedPreferences!!.getInt(KEY_ID, -1),
            sharedPreferences.getString(KEY_NAME, null),
            sharedPreferences.getString(KEY_EMAIL, null),
            sharedPreferences.getString(KEY_ROLE, null)
        )
    }

    init {
        ctx = context
    }

    // save user to preferences
    fun userLogin(user: CurrentUser) {
        val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putInt(KEY_ID, user.id)
        editor?.putString(KEY_NAME, user.name)
        editor?.putString(KEY_EMAIL, user.email)
        editor?.putString(KEY_ROLE, user.role)
        editor?.apply()
    }

    // logout
    fun logout() {
        val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()
    }

    companion object {
        private val SHARED_PREF_NAME = "userlogin"
        private val KEY_ID = "keyid"
        private val KEY_NAME = "keyname"
        private val KEY_EMAIL = "keyemail"
        private val KEY_ROLE = "keyrole"

        private var mInstance : SharedPrefManager? = null
        private var ctx: Context? = null

        @Synchronized
        fun getInstance(context: Context) : SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(context)
            }
            return mInstance as SharedPrefManager
        }
    }
}