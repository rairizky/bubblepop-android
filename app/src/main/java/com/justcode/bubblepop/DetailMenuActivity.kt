package com.justcode.bubblepop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import com.bumptech.glide.Glide
import com.justcode.bubblepop.model.MenuDetailResponse
import com.justcode.bubblepop.network.NetworkConfig
import com.justcode.bubblepop.network.SharedPrefManager
import kotlinx.android.synthetic.main.activity_detail_menu.*
import kotlinx.android.synthetic.main.activity_menu.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        // process get detail menu
        detailMenu(menuid)
    }

    private fun detailMenu(menuid: String) {
        showDetailMenuLoading()

        NetworkConfig.service()
            .getDetailMenu(menuid)
            .enqueue(object : Callback<MenuDetailResponse> {
                override fun onFailure(call: Call<MenuDetailResponse>, t: Throwable) {
                    hideErrorDetailMenuLoading()
                    toast(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<MenuDetailResponse>,
                    response: Response<MenuDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        hideDetailMenuLoading()

                        val data = response.body()?.menu
                        Glide.with(applicationContext)
                            .load(BuildConfig.BASE_URL+"uploads/menu/${data?.id}/${data?.image}")
                            .into(ivHeaderDetailMenu)
                        tvNameDetailMenu.text = data?.name
                        tvDescriptionDetailMenu.text = data?.description

                        btnToCart.setOnClickListener {
                            toast("btn cart gaes")
                        }

                        btnAddMenuToCart.setOnClickListener {
                            if (SharedPrefManager.getInstance(applicationContext).isLoggedIn) {
                                toast("udah login gaes")
                            } else {
                                startActivity<LoginActivity>()
                            }
                        }
                    }
                }

            })
    }

    fun showDetailMenuLoading() {
        layoutEventMenu.visibility = View.GONE
        shimmerHeaderDetailMenu.visibility = View.VISIBLE
        shimmerItemDetailMenu.visibility = View.VISIBLE
        shimmerHeaderDetailMenu.startShimmer()
        shimmerItemDetailMenu.startShimmer()
        layoutItemDetailMenu.visibility = View.GONE
    }
    fun hideErrorDetailMenuLoading() {
        layoutEventMenu.visibility = View.GONE
        shimmerHeaderDetailMenu.stopShimmer()
        shimmerItemDetailMenu.stopShimmer()
        shimmerHeaderDetailMenu.visibility = View.GONE
        shimmerItemDetailMenu.visibility = View.GONE
    }

    fun hideDetailMenuLoading() {
        layoutEventMenu.visibility = View.VISIBLE
        btnAddMenuToCart.isClickable = true
        shimmerHeaderDetailMenu.stopShimmer()
        shimmerItemDetailMenu.stopShimmer()
        shimmerHeaderDetailMenu.visibility = View.GONE
        shimmerItemDetailMenu.visibility = View.GONE
        layoutItemDetailMenu.visibility = View.VISIBLE
    }
}