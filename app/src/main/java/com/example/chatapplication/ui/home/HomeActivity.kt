package com.example.chatapplication.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.chatapplication.R
import com.example.chatapplication.base.BaseActivity
import com.example.chatapplication.database.models.Room
import com.example.chatapplication.databinding.ActivityHomeBinding
import com.example.chatapplication.ui.add_room.AddRoomActivity
import com.example.chatapplication.ui.chat.ChatRoom

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.vm = viewModel
        viewModel.navigator = this
        initializeAdapter()
        subscribeToLiveData()
        viewModel.loadRooms()

    }

    private fun subscribeToLiveData() {
        viewModel.roomsLiveData.observe(this) {
            adapter.changeData(it)
        }
    }

    var adapter = RoomAdapter()
    private fun initializeAdapter() {
        viewBinding.content.roomsRecycle.adapter = adapter
        adapter.onItemClickListener = object : RoomAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, room: Room) {
                val intent = Intent(this@HomeActivity, ChatRoom::class.java)
                intent.putExtra("room", room)
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadRooms()
    }

    override fun generateViewModel(): HomeViewModel {
        return ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun openAddRoom() {
        val intent = Intent(this, AddRoomActivity::class.java)
        startActivity(intent)
        finish()

    }
}