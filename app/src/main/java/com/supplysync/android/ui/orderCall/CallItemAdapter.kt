package com.supplysync.android.ui.orderCall
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.supplysync.android.databinding.ItemCallOrderBinding

class CallItemAdapter : ListAdapter<Item, CallItemAdapter.CallItemViewHolder>(ItemDiffItemCallback()) {

    //this function inflates each item layout and return instance of ViewHolder class(inner)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallItemViewHolder
            = CallItemViewHolder.inflateFrom(parent)

    //when data needs to be displayed in item(populate)
    override fun onBindViewHolder(holder: CallItemViewHolder, position: Int) {
        val item=getItem(position)
        holder.bind(item.title, item.date)     //calling bind()
    }

    //inner class(ViewHolder) of adapter
    class CallItemViewHolder(private val binding: ItemCallOrderBinding): RecyclerView.ViewHolder(binding.root){
        //has inflateFrom function to inflate and return instance of ViewHolder
        companion object{
            fun inflateFrom(parent: ViewGroup): CallItemViewHolder{
                val layoutInflater1= LayoutInflater.from(parent.context)
                val binding=ItemCallOrderBinding.inflate(layoutInflater1,parent,false)
                return CallItemViewHolder(binding)
            }
        }

        //bind function for setting data in ViewItem
        fun bind(title: String, date: String){
            binding.title.text = title
            binding.date.text = date
        }
    }
}