package com.justcode.bubblepop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.justcode.bubblepop.adapter.TransactionPendingMenuAdapter
import com.justcode.bubblepop.model.DetailStatusTransactionResponse
import com.justcode.bubblepop.model.MessageResponse
import com.justcode.bubblepop.network.NetworkConfig
import com.justcode.bubblepop.network.SharedPrefManager
import kotlinx.android.synthetic.main.activity_detail_finish.*
import kotlinx.android.synthetic.main.activity_detail_pending.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_finish)

        // back
        btnToMainFromDetailFinish.setOnClickListener {
            finish()
        }

        // id data
        val idfinish = intent.getStringExtra("idfinish") as String
        val current = SharedPrefManager.getInstance(this).user

        toast(current.id.toString())
        toast(idfinish)

        // proces
        getTransactionFinish(current.id.toString(), idfinish)
    }

    private fun getTransactionFinish(user_id: String, finish_id: String) {
        showLoadingFinish()

        // proces
        NetworkConfig.service()
            .getDetailTransactionFinish(user_id, finish_id)
            .enqueue(object: Callback<DetailStatusTransactionResponse> {
                override fun onFailure(call: Call<DetailStatusTransactionResponse>, t: Throwable) {
                    hideLoadingFinsih()
                }

                override fun onResponse(
                    call: Call<DetailStatusTransactionResponse>,
                    response: Response<DetailStatusTransactionResponse>
                ) {
                    if (response.isSuccessful) {
                        hideLoadingFinsih()
                        val data = response.body()
                        detailFinishNoTransaksi.text = data?.id.toString()
                        detailFinishName.text = data?.name
                        detailFinishTotal.text = data?.total.toString()
                        detailFinishPaid.text = data?.paid.toString()
                        NetworkConfig.service()
                            .getDetailCashier(user_id, data?.id.toString())
                            .enqueue(object: Callback<MessageResponse> {
                                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                                    toast(t.localizedMessage)
                                }

                                override fun onResponse(
                                    call: Call<MessageResponse>,
                                    response: Response<MessageResponse>
                                ) {
                                    if (response.isSuccessful) {
                                        detailFinishCashier.text = response.body()?.message
                                    }
                                }

                            })
                        rvDetailFinish.layoutManager = LinearLayoutManager(this@DetailFinishActivity)
                        rvDetailFinish.adapter = TransactionPendingMenuAdapter(this@DetailFinishActivity, data?.transaction)
                    }
                }

            })
    }

    fun showLoadingFinish() {
        toast("loading")
    }

    fun hideLoadingFinsih() {
        toast("finish")
    }
}