package com.example.chatapplication.ui.chat

import androidx.databinding.ObservableField
import com.example.chatapplication.UserProvider
import com.example.chatapplication.base.BaseNavigator
import com.example.chatapplication.base.BaseViewModel
import com.example.chatapplication.database.models.FireStoreUtils
import com.example.chatapplication.database.models.Message
import com.example.chatapplication.database.models.Room
import com.google.firebase.Timestamp

interface Navigator : BaseNavigator

class ChatRoomViewModel : BaseViewModel<Navigator>() {
    var messageField = ObservableField<String>()
    var room: Room? = null

    fun sendMessage() {
        val message = Message(
            content = messageField.get(),
            senderId = UserProvider.user?.id,
            senderName = UserProvider.user?.userName,
            roomId = room?.id,
            dateTime = Timestamp.now()
        )
        if (messageField.get().isNullOrBlank()) return
        FireStoreUtils()
            .sendMessage(message)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    messageField.set("")
                    return@addOnCompleteListener
                } else {
                    navigator?.showMessage("error sending your message",
                        posActionTitle = "try again",
                        posAction = {
                            sendMessage()
                        })

                }
            }


    }
}