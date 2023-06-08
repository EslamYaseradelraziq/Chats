package com.example.chatapplication.ui.chat

import android.os.Build
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapplication.R
import com.example.chatapplication.base.BaseActivity
import com.example.chatapplication.database.models.FireStoreUtils
import com.example.chatapplication.database.models.Message
import com.example.chatapplication.database.models.Room
import com.example.chatapplication.databinding.ActivityChatRoomBinding
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot

class ChatRoom : BaseActivity<ActivityChatRoomBinding, ChatRoomViewModel>(),
    Navigator {
    var room: Room? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeRoom()
        initializeMessagingAdapter()
        viewBinding.vm = viewModel
        viewModel.navigator = this
        subscrieToMessagesChanges()
    }

    lateinit var layoutManager: LinearLayoutManager

    val messageAdapter = MessageAdapter(mutableListOf())
    fun initializeMessagingAdapter() {
        viewBinding.messageRecycler.adapter = messageAdapter
        layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        viewBinding.messageRecycler.layoutManager = layoutManager

    }

    override fun onStart() {
        super.onStart()
        subscrieToMessagesChanges()
    }

    var listener: ListenerRegistration? = null
    override fun onStop() {
        super.onStop()
        listener?.remove()

    }

    fun subscrieToMessagesChanges() {
        if (listener == null) {
            listener = FireStoreUtils()
                .getRoomMessages(room?.id ?: "")
                .addSnapshotListener(EventListener<QuerySnapshot> { value, error ->
                    if (error != null) {
                        error.localizedMessage?.let {
                            showMessage(
                                it,
                                posActionTitle = "Try Again",
                                posAction = {
                                    subscrieToMessagesChanges()
                                })
                        }
                        return@EventListener
                    }
                    value?.documentChanges
                        ?.forEach {
                            val message = it.document.toObject(Message::class.java)
                            messageAdapter.addMessage(message)
                            viewBinding.messageRecycler.smoothScrollToPosition(messageAdapter.itemCount)

                        }

                })

        }

    }

    fun initializeRoom() {
        room = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("room", Room::class.java)!!
        } else {
            intent.getParcelableExtra("room")!!
        }
        viewModel.room = room
        viewBinding.invalidateAll()
    }

    override fun generateViewModel(): ChatRoomViewModel {
        return ViewModelProvider(this)[ChatRoomViewModel::class.java]
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_chat_room
    }
}