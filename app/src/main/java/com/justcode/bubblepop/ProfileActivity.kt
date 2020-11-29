package com.justcode.bubblepop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.awesomedialog.*
import com.justcode.bubblepop.adapter.ProfilePagerAdapter
import com.justcode.bubblepop.model.MessageResponse
import com.justcode.bubblepop.network.NetworkConfig
import com.justcode.bubblepop.network.SharedPrefManager
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val current = SharedPrefManager.getInstance(this).user
        etMemberName.text = current.name

        btnToMainFromProfile.setOnClickListener {
            finish()
        }

        btnLogout.setOnClickListener {
            AwesomeDialog.build(this)
                .title(
                    "Action Pending",
                    titleColor = ContextCompat.getColor(this, R.color.colorMatte)
                )
                .body(
                    "Apa anda yakin ingin keluar?",
                    color = ContextCompat.getColor(this, R.color.colorMatte)
                )
                .position(AwesomeDialog.POSITIONS.BOTTOM)
                .onPositive("OK") {
                    finish()
                    SharedPrefManager.getInstance(this).logout()
                    startActivity<LoginActivity>()
                }
                .onNegative("Cancel")
        }

        NetworkConfig.service()
            .getPoint(current.id.toString())
            .enqueue(object: Callback<MessageResponse> {
                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    toast(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {
                    if (response.isSuccessful) {
                        totalPoinUser.text = response.body()?.message
                    }
                }

            })

        // setup view pager
        val profilePagerAdapter = ProfilePagerAdapter(this, supportFragmentManager, current.id.toString())
        vpProfile.adapter = profilePagerAdapter
        tlProfile.setupWithViewPager(vpProfile)
    }
}