package com.justcode.bubblepop.adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.justcode.bubblepop.R
import com.justcode.bubblepop.model.TransactionPendingFinishItem
import kotlinx.android.synthetic.main.item_list_finish.view.*

class FinishAdapter (private val context: Context?, var data: List<TransactionPendingFinishItem?>?)
    : RecyclerView.Adapter<FinishAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinishAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_finish, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data?.size ?:0

    override fun onBindViewHolder(holder: FinishAdapter.ViewHolder, position: Int) {
        val item = data?.get(position)

        holder.noTransaksi.text = item?.id.toString()
        holder.totalTransaksi.text = 0.toString()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var noTransaksi = itemView.listFinishNoTransaksi
        var totalTransaksi = itemView.listFinishTotal
    }
}