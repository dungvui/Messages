package com.example.messenger

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.databinding.ItemUserBinding

class ChatBoxAdapter(private val list: List<User>) : RecyclerView.Adapter<ChatBoxAdapter.UserViewHolder>() {

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            if (user.id == 1) {
                binding.user1Txt.visibility = android.view.View.VISIBLE
                binding.user2Txt.visibility = android.view.View.GONE
                binding.user1Txt.text = user.message
            } else {
                binding.user1Txt.visibility = android.view.View.GONE
                if (user.isImage) {
                    binding.user2Txt.visibility = android.view.View.GONE
                    binding.user2Img.visibility = android.view.View.VISIBLE
                } else {
                    binding.user2Img.visibility = android.view.View.GONE
                    binding.user2Txt.visibility = android.view.View.VISIBLE
                    binding.user2Txt.text = user.message
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }
}
