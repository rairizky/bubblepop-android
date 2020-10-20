package com.justcode.bubblepop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.awesomedialog.*
import com.justcode.bubblepop.model.AuthResponse
import com.justcode.bubblepop.model.CurrentUser
import com.justcode.bubblepop.network.NetworkConfig
import com.justcode.bubblepop.network.SharedPrefManager
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // go to register
        goToRegister.setOnClickListener {
            startActivity<RegisterActivity>()
        }

        // login process
        btnLogin.setOnClickListener {
            val email = etEmailLogin.editText?.text.toString()
            val password = etPasswordLogin.editText?.text.toString()
            if (email.isEmpty()) {
                showDialogError("Email can't blank!")
            } else if (!isEmailValid(email)) {
                showDialogError("Email must be a valid email address!")
            } else if (password.isEmpty()) {
                showDialogError("Password can't blank!")
            } else {
                processLogin(email, password)
            }
        }
    }

    private fun processLogin(email: String, password: String) {
        // show loading
        showLoadingLogin()

        // process
        NetworkConfig.service()
            .postLogin(email, password)
            .enqueue(object : Callback<AuthResponse> {
                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    hideLoadingLogin()
                    showDialogError(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    if (response.code() == 200) {
                        if (response.body()?.status == true) {
                            val data = response.body()?.user
                            val current = CurrentUser(
                                data?.id!!.toInt(),
                                data?.name,
                                data?.email,
                                data?.role
                            )

                            // save to preferences
                            SharedPrefManager.getInstance(this@LoginActivity).userLogin(current)
                            showDialogSuccess("Login success!, welcome ${data.name}")
                        } else {
                            showDialogError(response.body()?.message.toString())
                        }
                    } else if (response.code() == 403) {
                        showDialogError("You are not authorize!")
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
            .onPositive("Go to profile") {
                finish()
                startActivity<ProfileActivity>()
            }
    }
}