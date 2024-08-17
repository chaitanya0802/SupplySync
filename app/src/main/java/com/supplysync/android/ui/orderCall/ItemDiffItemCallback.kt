package com.supplysync.android.ui.orderCall

import androidx.recyclerview.widget.DiffUtil

class ItemDiffItemCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item) = (oldItem.title == newItem.title)

    override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem
}