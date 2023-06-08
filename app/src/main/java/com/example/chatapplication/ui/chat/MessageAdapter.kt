package com.example.chatapplication.ui.chat


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chatapplication.R
import com.example.chatapplication.UserProvider
import com.example.chatapplication.database.models.Message
import com.example.chatapplication.databinding.ItemRecievedMessageBinding
import com.example.chatapplication.databinding.ItemSendMessageBinding

enum class MessageType(val value: Int) {
    Received(1),
    Sent(2)
}

class MessageAdapter(var messages: MutableList<Message>) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        if (message.senderId == UserProvider.user?.id) {
            return MessageType.Received.value
        }
        return MessageType.Sent.value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == MessageType.Sent.value) {
            val viewBinding: ItemSendMessageBinding =
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_send_message,
                    parent,
                    false
                )
            return SentMessageViewHolder(viewBinding)
        }
        val viewBinding: ItemRecievedMessageBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_recieved_message, parent, false
            )
        return ReceivedMessageViewHolder(viewBinding)


    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is SentMessageViewHolder) {
            holder.bind(messages[position])
        }
        if (holder is ReceivedMessageViewHolder) {
            holder.bind(messages[position])

        }


    }

    fun addMessage(message: Message) {
        messages.add(message)
        notifyItemInserted(messages.size)

    }

    class SentMessageViewHolder(val viewBinding: ItemSendMessageBinding) :
        ViewHolder(viewBinding.root) {

        fun bind(message: Message) {
            viewBinding.message = message
            viewBinding.invalidateAll()

        }
    }

    class ReceivedMessageViewHolder(val viewBinding: ItemRecievedMessageBinding) :
        ViewHolder(viewBinding.root) {

        fun bind(message: Message) {
            viewBinding.message = message
            viewBinding.invalidateAll()

        }
    }


}


