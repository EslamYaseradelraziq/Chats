package com.example.chatapplication.ui.add_room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.example.chatapplication.R
import com.example.chatapplication.databinding.ItemRoomCategoryBinding

class CategoriesAdapter(val items: List<RoomCategory>) : BaseAdapter() {
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var currentItemView = view
        val viewHolder: ViewHolder
        if (currentItemView == null) {
            //create view
            val viewBinding: ItemRoomCategoryBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent?.context),
                R.layout.item_room_category, parent, false
            )
            viewHolder = ViewHolder(viewBinding)
            currentItemView = viewHolder.viewBinding.root
            currentItemView.tag = viewHolder
        } else {
            viewHolder = currentItemView.tag as ViewHolder
        }
        val item = getItem(position) as RoomCategory
        viewHolder.viewBinding.item = item
        viewHolder.viewBinding.invalidateAll()
        return currentItemView


    }

    class ViewHolder(val viewBinding: ItemRoomCategoryBinding)
}