package com.example.chatapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplication.R
import com.example.chatapplication.database.models.Room
import com.example.chatapplication.databinding.ItemRoomBinding

class RoomAdapter(var items: List<Room>? = null) :
    RecyclerView.Adapter<RoomAdapter.viewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val viewBinding: ItemRoomBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_room, parent, false
        )
        return viewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(items!![position])
        holder.viewBinding.root.setOnClickListener {
            onItemClickListener?.onItemClick(position, items!![position])
        }
    }

    fun changeData(newList: List<Room>?) {
        items = newList
        notifyDataSetChanged()
    }

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: Room)
    }


    class viewHolder(val viewBinding: ItemRoomBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(room: Room) {
            viewBinding.item = room
            viewBinding.invalidateAll()
        }
    }
}