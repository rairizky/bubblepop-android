package com.justcode.bubblepop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail_menu.*
import org.jetbrains.anko.toast

class DetailMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)

        // btn back to main
        btnToMainFromDetailMenu.setOnClickListener {
            finish()
        }

        // get id menu
        val menuid = intent.getStringExtra("idMenu") as String
        toast(menuid)
    }
}