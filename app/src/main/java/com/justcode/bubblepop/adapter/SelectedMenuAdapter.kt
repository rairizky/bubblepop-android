package com.justcode.bubblepop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.justcode.bubblepop.BuildConfig
import com.justcode.bubblepop.R
import com.justcode.bubblepop.model.MenuItem
import kotlinx.android.synthetic.main.item_selected_menu.view.*
import org.jetbrains.anko.toast

class SelectedMenuAdapter (private val context: Context?, var data: List<MenuItem?>?)
    : RecyclerView.Adapter<SelectedMenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_selected_menu, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = 3

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menu = data?.get(position)

        Glide.with(holder.itemView.context)
            .load(BuildConfig.BASE_URL+"uploads/menu/${menu?.id}/${menu?.image}")
            .into(holder.imageMenu)
        holder.nameMenu.text = menu?.name
        holder.cardAllMenu.setOnClickListener {
            context?.toast(menu?.id.toString())
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageMenu = itemView.ivSelectedMenu
        var nameMenu = itemView.tvSelectedMenu
        var cardAllMenu = itemView.cardAllMenu
    }
}