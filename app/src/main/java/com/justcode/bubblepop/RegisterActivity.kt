package com.justcode.bubblepop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.awesomedialog.*
import com.google.gson.JsonParser
import com.justcode.bubblepop.model.AuthResponse
import com.justcode.bubblepop.model.CurrentUser
import com.justcode.bubblepop.network.NetworkConfig
import com.justcode.bubblepop.network.SharedPrefManager
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import org.xml.sax.Parser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // btn to login
        btnToLogin.setOnClickListener {
            finish()
        }

        // register process
        btnRegister.setOnClickListener {
            val name = etNameRegister.editText?.text.toString()
            val email = etEmailRegister.editText?.text.toString()
            val password = etPasswordRegister.editText?.text.toString()
            if (name.isEmpty()) {
                showDialogError("Name can't blank!")
            } else if (email.isEmpty()) {
                showDialogError("Email can't blank!")
            } else if (!isEmailValid(email)) {
                showDialogError("Email must be a valid email address!")
            } else if (password.isEmpty()) {
                showDialogError("Password can't blank!")
            } else {
                processLogin(name, email, password)
            }
        }
    }

    private fun processLogin(name: String, email: String, password: String) {
        // show loading
        showLoadingLogin()

        // process
        NetworkConfig.service()
            .postRegister(name, email, password)
            .enqueue(object : Callback<AuthResponse> {
                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    hideLoadingLogin()
                    showDialogError(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body()?.status == true) {
                            showDialogSuccess("Create account success!")
                        }
                    } else if (response.code() == 400) {
                        showDialogError("Email has already been taken")
                    }
                }

            })
    }

    fun isEmailValid(email: String) : Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun showLoadingLogin() {
        toast("login in")
    }

    fun hideLoadingLogin() {
        toast("finish")
    }

    fun showDialogError(message: String) {
        AwesomeDialog.build(this)
            .title(
                "Action Failed",
                titleColor = ContextCompat.getColor(this, R.color.colorMatte)
            )
            .body(
                message,
                color = ContextCompat.getColor(this, R.color.colorMatte)
            )
            .position(AwesomeDialog.POSITIONS.BOTTOM)
            .onPositive("OK")
    }

    fun showDialogSuccess(message: String) {
        AwesomeDialog.build(this)
            .title(
                "Action Success",
                titleColor = ContextCompat.getColor(this, R.color.colorMatte)
            )
            .body(
                message,
                color = ContextCompat.getColor(this, R.color.colorMatte)
            )
            .position(AwesomeDialog.POSITIONS.BOTTOM)
            .onPositive("Go to login") {
                finish()
            }
    }
}