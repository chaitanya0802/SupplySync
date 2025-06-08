package com.supplysync.android.ui.deleterack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.supplysync.android.R

class DeleteRackAdapter(
    private val rackList: MutableList<String>,
    private val onDeleteClicked: (String, Int) -> Unit
) : RecyclerView.Adapter<DeleteRackAdapter.RackViewHolder>() {

    inner class RackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rackName: TextView = itemView.findViewById(R.id.item_id)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RackViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_delete, parent, false)
        return RackViewHolder(view)
    }

    override fun onBindViewHolder(holder: RackViewHolder, position: Int) {
        val rackId = rackList[position]
        holder.rackName.text = rackId
        holder.deleteButton.setOnClickListener {
            onDeleteClicked(rackId, position)
        }
    }

    override fun getItemCount(): Int = rackList.size

    fun removeItemAt(position: Int) {
        rackList.removeAt(position)
        notifyItemRemoved(position)
    }
}
