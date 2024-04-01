package com.example.messenger.listener

import com.example.messenger.model.Messenger

interface OnMessageItemListener {
    fun onItemClick(msg: Messenger)
    fun onItemLongClick(msg: Messenger)
}