package com.example.messenger.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.conts.Constants
import com.example.messenger.databinding.ItemUserBinding
import com.example.messenger.listener.OnMessageItemListener
import com.example.messenger.model.Messenger

class ChatBoxAdapter(
    private val list: List<Messenger>,
    private val listener: OnMessageItemListener
) : RecyclerView.Adapter<ChatBoxAdapter.MessengerViewHolder>() {

    inner class MessengerViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(msg: Messenger) {
            binding.tvTime.text = msg.time
            if (msg.userSent.id == Constants.otherUser.id) {
                binding.layoutMsgOther.visibility = android.view.View.VISIBLE
                binding.layoutMsgSelf.visibility = android.view.View.GONE
                binding.tvMsgOther.text = msg.msg
            } else {
                binding.layoutMsgOther.visibility = android.view.View.GONE
                if (msg.isImage) {
                    binding.layoutMsgSelf.visibility = android.view.View.GONE
                    binding.imgMsg.visibility = android.view.View.VISIBLE
                } else {
                    binding.imgMsg.visibility = android.view.View.GONE
                    binding.layoutMsgSelf.visibility = android.view.View.VISIBLE
                    binding.tvMsgSelf.text = msg.msg
                }
            }

            binding.root.setOnClickListener {
                listener.onItemClick(msg)
            }

            binding.root.setOnLongClickListener {
                listener.onItemLongClick(msg)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessengerViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessengerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MessengerViewHolder, position: Int) {
        holder.bind(list[position])
    }
}
