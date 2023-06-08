package com.example.chatapplication.ui.home

import androidx.lifecycle.MutableLiveData
import com.example.chatapplication.base.BaseNavigator
import com.example.chatapplication.base.BaseViewModel
import com.example.chatapplication.database.models.FireStoreUtils
import com.example.chatapplication.database.models.Room

interface Navigator : BaseNavigator {
    fun openAddRoom()
}

class HomeViewModel : BaseViewModel<Navigator>() {
    val roomsLiveData = MutableLiveData<List<Room>>()
    fun addRoomAction() {
        navigator?.openAddRoom()
    }

    fun loadRooms() {
        FireStoreUtils()
            .getAllRooms()
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    navigator?.showMessage("error loading rooms")
                    return@addOnCompleteListener

                }
                val rooms = it.result.toObjects(Room::class.java)
                roomsLiveData.value = rooms
            }

    }

}