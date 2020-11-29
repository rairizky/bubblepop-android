package com.justcode.bubblepop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.justcode.bubblepop.BuildConfig
import com.justcode.bubblepop.DetailMenuActivity
import com.justcode.bubblepop.R
import com.justcode.bubblepop.model.DetailStatusTransactionResponse
import com.justcode.bubblepop.model.MenuDetailResponse
import com.justcode.bubblepop.model.TransactionDetailItem
import com.justcode.bubblepop.network.NetworkConfig
import kotlinx.android.synthetic.main.item_list_transaction_menu.view.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionPendingMenuAdapter (private val context: Context?, var data: List<TransactionDetailItem?>?)
    : RecyclerView.Adapter<TransactionPendingMenuAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionPendingMenuAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_transaction_menu, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data?.size ?:0

    override fun onBindViewHolder(holder: TransactionPendingMenuAdapter.ViewHolder, position: Int) {
        val item = data?.get(position)
        holder.intentMenu.setOnClickListener {
            context?.startActivity<DetailMenuActivity>("idMenu" to item?.menuId.toString())
        }
        holder.sizeMenu.text = item?.size
        holder.mountMenu.text = item?.mount.toString()
        holder.priceMenu.text = item?.price.toString()
        NetworkConfig.service()
            .getDetailMenu(item?.menuId.toString())
            .enqueue(object: Callback<MenuDetailResponse> {
                override fun onFailure(call: Call<MenuDetailResponse>, t: Throwable) {
                    t.localizedMessage
                }

                override fun onResponse(
                    call: Call<MenuDetailResponse>,
                    responseMenu: Response<MenuDetailResponse>
                ) {
                    if (responseMenu.isSuccessful) {
                        val menu = responseMenu.body()?.menu
                        holder.nameMenu.text = menu?.name
                        Glide.with(holder.itemView.context)
                            .load(BuildConfig.BASE_URL+"uploads/menu/${menu?.id}/${menu?.image}")
                            .into(holder.imageMenu)
                    }
                }

            })
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameMenu = itemView.listDetailTransaksiName
        var imageMenu = itemView.listDetailTransaksiImage
        var sizeMenu = itemView.listDetailTransaksiSize
        var priceMenu = itemView.listDetailTransaksiPrice
        var mountMenu = itemView.listDetailTransaksiMount
        var intentMenu = itemView.btnToDetailMenu
    }
}