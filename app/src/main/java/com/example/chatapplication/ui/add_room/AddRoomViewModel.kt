package com.example.chatapplication.ui.add_room

import androidx.databinding.ObservableField
import com.example.chatapplication.UserProvider
import com.example.chatapplication.base.BaseNavigator
import com.example.chatapplication.base.BaseViewModel
import com.example.chatapplication.database.models.FireStoreUtils
import com.example.chatapplication.database.models.Room

interface Navigator : BaseNavigator {
    fun goBack()
}

class AddRoomViewModel : BaseViewModel<Navigator>() {
    var selectedCategory: RoomCategory = RoomCategory.getCategories()[0]
    val title = ObservableField<String>()
    val titleError = ObservableField<String?>()
    val desc = ObservableField<String>()
    val descError = ObservableField<String?>()


    fun create() {
        val room = Room(
            title = title.get(),
            description = desc.get(),
            categoryId = selectedCategory.id,
            createdBy = UserProvider.user?.id
        )
        if (!validForm()) return
        navigator?.showLoading("loading.....")
        FireStoreUtils()
            .insertRoom(room)
            .addOnCompleteListener { task ->
                navigator?.hideDialogue()
                if (!task.isSuccessful) {
                    navigator?.showMessage(task.exception?.localizedMessage ?: "")
                    return@addOnCompleteListener
                }
                navigator?.showMessage("Room Added",
                    posActionTitle = "ok",
                    posAction = {
                        navigator?.goBack()
                    })


            }


    }

    var isValid = true
    fun validForm(): Boolean {
        if (title.get().isNullOrBlank()) {
            titleError.set("please enter room")
            isValid = false
        } else {
            titleError.set(null)
        }
        if (desc.get().isNullOrBlank()) {
            descError.set("please enter description")
            isValid = false
        } else {
            descError.set(null)
        }

        return isValid
    }
}