package com.supplysync.android.ui.deletesection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.supplysync.android.R

class DeleteSectionAdapter(
    private val sectionList: MutableList<String>,
    private val onDeleteClicked: (String, Int) -> Unit
) : RecyclerView.Adapter<DeleteSectionAdapter.SectionViewHolder>() {

    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sectionName: TextView = itemView.findViewById(R.id.item_id)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_delete, parent, false)
        return SectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val sectionId = sectionList[position]
        holder.sectionName.text = sectionId
        holder.deleteButton.setOnClickListener {
            onDeleteClicked(sectionId, position)
        }
    }

    override fun getItemCount(): Int = sectionList.size

    fun removeItemAt(position: Int) {
        sectionList.removeAt(position)
        notifyItemRemoved(position)
    }
}
