package com.justcode.bubblepop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.justcode.bubblepop.model.MenuItem
import com.justcode.bubblepop.BuildConfig
import com.justcode.bubblepop.R
import kotlinx.android.synthetic.main.item_list_menu.view.*
import org.jetbrains.anko.toast

class AllMenuAdapter (private val context: Context?, var data: List<MenuItem?>?)
    : RecyclerView.Adapter<AllMenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_menu, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data?.size ?:0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menu = data?.get(position)

        Glide.with(holder.itemView.context)
            .load(BuildConfig.BASE_URL+"uploads/menu/${menu?.id}/${menu?.image}")
            .into(holder.ivBackground)
        Glide.with(holder.itemView.context)
            .load(BuildConfig.BASE_URL+"uploads/menu/${menu?.id}/${menu?.image}")
            .into(holder.ivMenu)
        holder.tvName.text = menu?.name
        holder.tvDesc.text = menu?.description

        holder.cardItem.setOnClickListener{
            context?.toast(menu?.id.toString())
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivBackground = itemView.ivListBackgroundMenu
        var ivMenu = itemView.ivListImageMenu
        var tvName = itemView.tvListNameMenu
        var tvDesc = itemView.tvListDescMenu
        var cardItem = itemView.goToDetailMenu
    }
}