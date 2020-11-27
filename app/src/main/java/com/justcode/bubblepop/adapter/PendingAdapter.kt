package com.justcode.bubblepop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.justcode.bubblepop.R
import com.justcode.bubblepop.model.PendingFinishResponse
import com.justcode.bubblepop.model.TransactionPendingFinishItem
import kotlinx.android.synthetic.main.item_list_pending.view.*

class PendingAdapter(private val context: Context?, var data: List<TransactionPendingFinishItem?>?)
    : RecyclerView.Adapter<PendingAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_pending, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data?.size ?:0

    override fun onBindViewHolder(holder: PendingAdapter.ViewHolder, position: Int) {
        val item = data?.get(position)
        holder.noTransaksi.text = item?.id.toString()
        holder.totalTransaksi.text = item?.total.toString()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var noTransaksi = itemView.listPendingNoTransaksi
        var totalTransaksi = itemView.listPendingTotal
    }
}