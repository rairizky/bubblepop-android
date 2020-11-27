package com.justcode.bubblepop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.justcode.bubblepop.BuildConfig
import com.justcode.bubblepop.R
import com.justcode.bubblepop.model.MenuDetailResponse
import com.justcode.bubblepop.model.TransactionCartItem
import com.justcode.bubblepop.network.NetworkConfig
import kotlinx.android.synthetic.main.item_list_cart.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartAdapter(private val context: Context?, var data: List<TransactionCartItem?>?)
    : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_cart , parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data?.size ?:0

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        val cart = data?.get(position)

        holder.sizeMenu.text = cart?.size
        holder.priceMenu.text = cart?.price.toString()
        holder.mountMenu.setText(cart?.mount.toString())
        NetworkConfig.service()
            .getDetailMenu(cart?.menuId.toString())
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
        var imageMenu = itemView.listCartImageMenu
        var nameMenu = itemView.listCartNameMenu
        var sizeMenu = itemView.listCartSize
        var priceMenu = itemView.listCartPrice
        var mountMenu = itemView.listCartMount
        var minButton = itemView.listCartMinButton
        var plusButton = itemView.listCartPlusButton
        var editButton = itemView.listCartEdit
        var deleteButton = itemView.listCartDelete
    }
}