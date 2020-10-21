package com.justcode.bubblepop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.awesomedialog.*
import com.justcode.bubblepop.network.SharedPrefManager
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity

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
    }
}