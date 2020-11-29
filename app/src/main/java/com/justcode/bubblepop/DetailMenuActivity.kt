package com.justcode.bubblepop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.awesomedialog.*
import com.justcode.bubblepop.model.MenuDetailResponse
import com.justcode.bubblepop.model.MessageResponse
import com.justcode.bubblepop.network.NetworkConfig
import com.justcode.bubblepop.network.SharedPrefManager
import kotlinx.android.synthetic.main.activity_detail_menu.*
import kotlinx.android.synthetic.main.activity_menu.*
import org.jetbrains.anko.find
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
        val current = SharedPrefManager.getInstance(this).user

        // process get detail menu
        detailMenu(menuid, current.id.toString())
    }

    private fun detailMenu(menuid: String, iduser: String) {
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
                            if (SharedPrefManager.getInstance(applicationContext).isLoggedIn) {
                                startActivity<TransactionActivity>()
                            } else {
                                startActivity<LoginActivity>()
                            }
                        }

                        var size = ""
                        radioGroupMenu.setOnCheckedChangeListener(object: RadioGroup.OnCheckedChangeListener {
                            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                                val getSize : RadioButton = findViewById(p1)
                                size = getSize.text.toString()
                                if (size == "M") {
                                    textPrice.setText("Harga : ${data?.price_m}")
                                } else {
                                    textPrice.setText("Harga : ${data?.price_l}")
                                }
                            }
                        })

                        btnAddMenuToCart.setOnClickListener {
                            if (SharedPrefManager.getInstance(applicationContext).isLoggedIn) {
                                // get data
                                detailJumlah.addTextChangedListener(textWatcher)
                                val totalJumlah = getTotalMount.text.toString()
                                val valueTotal : Int = Integer.parseInt(totalJumlah)
                                if (valueTotal < 1) {
                                    toast("Jumlah tidak boleh kurang dari 1")
                                } else {
                                    if (size == "") {
                                        toast("Size tidak boleh kosong")
                                    } else {
                                        NetworkConfig.service()
                                            .postAddMenuToCart(iduser, size, totalJumlah.toInt(), menuid.toInt())
                                            .enqueue(object: Callback<MessageResponse> {
                                                override fun onFailure(
                                                    call: Call<MessageResponse>,
                                                    t: Throwable
                                                ) {
                                                    toast(t.localizedMessage)
                                                }

                                                override fun onResponse(
                                                    call: Call<MessageResponse>,
                                                    response: Response<MessageResponse>
                                                ) {
                                                    if (response.isSuccessful) {
                                                        AwesomeDialog.build(this@DetailMenuActivity)
                                                            .title(
                                                                "Message",
                                                                titleColor = ContextCompat.getColor(this@DetailMenuActivity, R.color.colorMatte)
                                                            )
                                                            .body(
                                                                "${response.body()?.message}",
                                                                color = ContextCompat.getColor(this@DetailMenuActivity, R.color.colorMatte)
                                                            )
                                                            .position(AwesomeDialog.POSITIONS.BOTTOM)
                                                            .onPositive("OK") {

                                                            }

                                                    }
                                                }
                                            })
                                    }
                                }
                            } else {
                                startActivity<LoginActivity>()
                            }
                        }
                    }
                }

            })
    }

    private val textWatcher = object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            getTotalMount.setText(s.toString())
        }

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