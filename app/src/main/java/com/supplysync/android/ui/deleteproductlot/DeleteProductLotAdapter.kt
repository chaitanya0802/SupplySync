package com.supplysync.android.ui.deleteproductlot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.supplysync.android.R

class DeleteProductLotAdapter(
    private val productlotList: MutableList<String>,
    private val onDeleteClicked: (String, Int) -> Unit
) : RecyclerView.Adapter<DeleteProductLotAdapter.ProductLotViewHolder>() {

    inner class ProductLotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productlotName: TextView = itemView.findViewById(R.id.item_id)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductLotViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_delete, parent, false)
        return ProductLotViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductLotViewHolder, position: Int) {
        val productlotId = productlotList[position]
        holder.productlotName.text = productlotId
        holder.deleteButton.setOnClickListener {
            onDeleteClicked(productlotId, position)
        }
    }

    override fun getItemCount(): Int = productlotList.size

    fun removeItemAt(position: Int) {
        productlotList.removeAt(position)
        notifyItemRemoved(position)
    }
}
