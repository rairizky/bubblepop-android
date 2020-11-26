package com.justcode.bubblepop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.justcode.bubblepop.adapter.TransactionPagerAdapter
import com.justcode.bubblepop.network.SharedPrefManager
import kotlinx.android.synthetic.main.activity_transaction.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class TransactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        // btn back
        btnToMainFromTransaction.setOnClickListener {
            finish()
        }

        // get current id
        val current = SharedPrefManager.getInstance(this).user

        // viewpager setup
        val transactionPagerAdapter = TransactionPagerAdapter(this, supportFragmentManager, current.id.toString())
        vpTransaction.adapter = transactionPagerAdapter
        tlTransaction.setupWithViewPager(vpTransaction)
    }
}