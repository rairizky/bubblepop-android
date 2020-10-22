package com.justcode.bubblepop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.justcode.bubblepop.adapter.AllMenuAdapter
import com.justcode.bubblepop.model.MenuResponse
import com.justcode.bubblepop.network.NetworkConfig
import kotlinx.android.synthetic.main.activity_menu.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // btn back
        btnToMainFromMenu.setOnClickListener {
            finish()
        }

        // process get all menu
        getAllMenu()
    }

    private fun getAllMenu() {
        // shimmer loading
        showListMenuLoading()

        // get all menu
        NetworkConfig.service()
            .getMenu()
            .enqueue(object : Callback<MenuResponse> {
                override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
                    hideListMenuLoading()
                    toast(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<MenuResponse>,
                    response: Response<MenuResponse>
                ) {
                    if (response.isSuccessful) {
                        hideListMenuLoading()

                        val data = response.body()?.menu
                        totalAllMenu.setText("${response.body()?.total} menu")
                        rvAllMenu.layoutManager = LinearLayoutManager(this@MenuActivity)
                        rvAllMenu.adapter = AllMenuAdapter(this@MenuActivity, data)
                    }
                }
            })
    }

    fun showListMenuLoading() {
        shimmerAllMenu.visibility = View.VISIBLE
        shimmerAllMenu.startShimmer()
        rvAllMenu.visibility = View.GONE
    }

    fun hideListMenuLoading() {
        shimmerAllMenu.stopShimmer()
        shimmerAllMenu.visibility = View.GONE
        rvAllMenu.visibility = View.VISIBLE
    }
}