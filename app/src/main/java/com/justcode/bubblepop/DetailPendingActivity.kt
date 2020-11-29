package com.justcode.bubblepop

import android.graphics.Bitmap
import android.graphics.Color
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.justcode.bubblepop.R.*
import com.justcode.bubblepop.adapter.TransactionPendingMenuAdapter
import com.justcode.bubblepop.model.DetailStatusTransactionResponse
import com.justcode.bubblepop.network.NetworkConfig
import com.justcode.bubblepop.network.SharedPrefManager
import kotlinx.android.synthetic.main.activity_detail_pending.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPendingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_detail_pending)

        // back
        btnToMainFromDetailPending.setOnClickListener {
            finish()
        }

        // get id pending
        val idpending = intent.getStringExtra("idpending") as String
        val totalPrice = intent.getStringExtra("totalPrice") as String
        val current = SharedPrefManager.getInstance(this).user

        val bitmap = generateQRCode(idpending)
        detailPendingQR.setImageBitmap(bitmap)
        detailPendingNoTransaksi.text = idpending
        detailPendingTotalPrice.text = totalPrice

        // proces
        getTransactionMenu(current.id.toString(), idpending.toString())
    }

    private fun generateQRCode(id: String) : Bitmap {
        val width = 250
        val height = 250
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val codeWriter = MultiFormatWriter()
        try {
            val bitMatrix = codeWriter.encode(id, BarcodeFormat.QR_CODE, width, height)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        } catch (e: WriterException) {
            toast("generateQRCode: ${e.message}")
        }

        return bitmap
    }

    private fun getTransactionMenu(user_id: String, pending_id: String) {
        showTransactionLoading()

        NetworkConfig.service()
            .getDetailTransactionPending(user_id, pending_id)
            .enqueue(object: Callback<DetailStatusTransactionResponse> {
                override fun onFailure(call: Call<DetailStatusTransactionResponse>, t: Throwable) {
                    toast(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<DetailStatusTransactionResponse>,
                    response: Response<DetailStatusTransactionResponse>
                ) {
                    if (response.isSuccessful) {
                        hideTransactionLoading()
                        val data = response.body()?.transaction
                        rvDetailPending.layoutManager = LinearLayoutManager(this@DetailPendingActivity)
                        rvDetailPending.adapter = TransactionPendingMenuAdapter(this@DetailPendingActivity, data)
                    }
                }

            })
    }

    fun showTransactionLoading() {
        toast("loading")
    }

    fun hideTransactionLoading() {
        toast("finish")
    }
}