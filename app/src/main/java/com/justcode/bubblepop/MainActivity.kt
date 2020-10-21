package com.justcode.bubblepop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.justcode.bubblepop.adapter.SelectedMenuAdapter
import com.justcode.bubblepop.model.MenuResponse
import com.justcode.bubblepop.model.PromoResponse
import com.justcode.bubblepop.network.NetworkConfig
import com.justcode.bubblepop.network.SharedPrefManager
import com.ramotion.cardslider.CardSliderLayoutManager
import com.ramotion.cardslider.CardSnapHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // btn search
        btnSearch.setOnClickListener {
            toast("btn search nii")
        }

        // btn profile
        btnProfile.setOnClickListener {
            if (SharedPrefManager.getInstance(this).isLoggedIn) {
                startActivity<ProfileActivity>()
            } else {
                startActivity<LoginActivity>()
            }
        }

        // btn to all menu
        btnAllMenu.setOnClickListener {
            startActivity<MenuActivity>()
        }

        // get header data
        getHeaderMenu()

        // get promo
        getAllPromo()

        // get selected menu
        getSelectedMenu()

    }

    // header
    private fun getHeaderMenu() {
        // shimmer loading get header
        showHeaderLoading()
        // process get header
        NetworkConfig.service()
            .getHeader()
            .enqueue(object: Callback<MenuResponse> {
                override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
                    hideHeaderLoading()
                    toast(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<MenuResponse>,
                    response: Response<MenuResponse>
                ) {
                    if (response.isSuccessful) {
                        hideHeaderLoading()
                        var idHeader = ""
                        var imageHeader = ""
                        var nameHeader = ""
                        var priceHeader = ""
                        response.body()?.menu?.forEach { it ->
                            idHeader = it?.id.toString()
                            imageHeader = it?.image.toString()
                            nameHeader = it?.name.toString()
                            priceHeader = it?.priceM.toString()
                        }

                        Glide.with(applicationContext)
                            .load(BuildConfig.BASE_URL+"uploads/menu/${idHeader}/${imageHeader}")
                            .into(ivHeaderMenu)
                        tvHeaderName.text = nameHeader
                        tvHeaderPrice.setText("Harga mulai dari Rp. ${priceHeader}")

                        headerMenu.setOnClickListener {
                            toast("btn header menu yeuh!")
                        }
                    }
                }
            })
    }

    fun showHeaderLoading() {
        shimmerHeaderMenu.visibility = View.VISIBLE
        shimmerHeaderMenu.startShimmer()
        ivHeaderMenuLayout.visibility = View.GONE
    }

    fun hideHeaderLoading() {
        shimmerHeaderMenu.stopShimmer()
        shimmerHeaderMenu.visibility = View.GONE
        ivHeaderMenuLayout.visibility = View.VISIBLE
    }

    // promo
    private fun getAllPromo() {
        // shimmer loading
        showPromoLoading()

        // process get all promo
        NetworkConfig.service()
            .getPromo()
            .enqueue(object : Callback<PromoResponse> {
                override fun onFailure(call: Call<PromoResponse>, t: Throwable) {
                    hidePromoLoading()
                    toast(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<PromoResponse>,
                    response: Response<PromoResponse>
                ) {
                    if (response.isSuccessful) {
                        hidePromoLoading()

                        val list = mutableListOf<CarouselItem>()
                        response.body()?.promo?.forEach {
                            list.add(
                                CarouselItem(
                                    imageUrl = BuildConfig.BASE_URL+"uploads/promo/${it?.id}/${it?.image}",
                                    caption = it?.title
                                )
                            )
                        }
                        carouselPromo.addData(list)
                    }
                }
            })
    }

    fun showPromoLoading() {
        shimmerPromo.visibility = View.VISIBLE
        shimmerPromo.startShimmer()
        layoutPromo.visibility = View.GONE
    }

    fun hidePromoLoading() {
        shimmerPromo.stopShimmer()
        shimmerPromo.visibility = View.GONE
        layoutPromo.visibility = View.VISIBLE
    }

    // selected menu
    private fun getSelectedMenu() {
        // shimmer loading
        showSelectedMenuLoading()

        // process get selected menu
        NetworkConfig.service()
            .getMenu()
            .enqueue(object : Callback<MenuResponse> {
                override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
                    hideSelectedMenuLoading()
                    toast(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<MenuResponse>,
                    response: Response<MenuResponse>
                ) {
                    if (response.isSuccessful) {
                        hideSelectedMenuLoading()

                        val data = response.body()?.menu
                        rvSelectedMenu.layoutManager = CardSliderLayoutManager(this@MainActivity)
                        CardSnapHelper().attachToRecyclerView(rvSelectedMenu)
                        rvSelectedMenu.adapter = SelectedMenuAdapter(this@MainActivity, data)
                    }
                }
            })
    }

    fun showSelectedMenuLoading() {
        shimmerSelectedMenu.visibility = View.VISIBLE
        shimmerSelectedMenu.startShimmer()
        rvSelectedMenu.visibility = View.GONE
    }

    fun hideSelectedMenuLoading() {
        shimmerSelectedMenu.stopShimmer()
        shimmerSelectedMenu.visibility = View.GONE
        rvSelectedMenu.visibility = View.VISIBLE
    }

}