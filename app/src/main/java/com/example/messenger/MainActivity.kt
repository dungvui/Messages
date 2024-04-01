package com.example.messenger

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messenger.adapter.ChatBoxAdapter
import com.example.messenger.conts.Constants
import com.example.messenger.databinding.ActivityMainBinding
import com.example.messenger.listener.OnMessageItemListener
import com.example.messenger.model.Messenger
import com.example.messenger.model.User
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Random

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = requireNotNull(_binding)
    private val list = arrayListOf<Messenger>()
    private val adapter: ChatBoxAdapter by lazy {
        ChatBoxAdapter(list, object : OnMessageItemListener {
            override fun onItemClick(msg: Messenger) {
                TODO("Not yet implemented")
            }

            override fun onItemLongClick(msg: Messenger) {
                list.remove(msg)
                adapter.notifyItemRemoved(list.size)
                adapter.notifyItemRangeChanged(0, list.size)
            }
        })
    }
    private val date = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Constants.otherUser = User(1, "Martina Wolna")
        Constants.selfUser = User(2, "Maciej Kowalski")

        list.addAll(getList())
        with(binding.chatRecyclerView) {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = this@MainActivity.adapter
        }

        binding.root.setOnClickListener {
            hideKeyboard()
        }

        binding.messageEditText.apply {
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString().isNotEmpty()) {
                        binding.sendTextBtn.isVisible = false
                        (binding.sendImageBtn.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin =
                            10
                        binding.sendBtn.apply {
                            isVisible = true
                            setOnClickListener {
                                val random = Random().nextInt(10)
                                val msg = if (random % 2 == 0) {
                                    Messenger(
                                        1,
                                        s.toString().trim(),
                                        Constants.otherUser,
                                        System.currentTimeMillis().toString(),
                                        false
                                    )
                                } else {
                                    Messenger(
                                        2,
                                        s.toString().trim(),
                                        Constants.selfUser,
                                        System.currentTimeMillis().toString(),
                                        false
                                    )
                                }
                                list.add(msg)
                                adapter.notifyItemInserted(list.size)
                                binding.chatRecyclerView.smoothScrollToPosition(list.size)
                                binding.messageEditText.setText("")
                                hideKeyboard()
                            }
                        }
                    } else {
                        binding.sendTextBtn.isVisible = true
                        binding.sendBtn.isVisible = false
                        (binding.sendImageBtn.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin =
                            0
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    private fun getList(): List<Messenger> {
        return listOf(
            Messenger(
                1,
                "I commented on Figma, I want to add some fancy icons. Do you have any icon set?",
                Constants.otherUser,
                "12:00",
                false
            ),
            Messenger(
                2,
                "I am in a process of designing some. When do you need them?",
                Constants.selfUser,
                "12:00",
                false
            ),
            Messenger(
                1,
                "Next month?",
                Constants.otherUser,
                "12:00",
                false
            ),
            Messenger(
                2,
                "I am almost finish. Please give me your email, I will ZIP them and send you as son as im finish.",
                Constants.selfUser,
                "12:00",
                false
            ),
            Messenger(
                2,
                "?",
                Constants.selfUser,
                "12:00",
                false
            ),
            Messenger(
                1,
                "maciej.kowalski@email.com",
                Constants.otherUser,
                "12:00",
                false
            ),
            Messenger(
                2,
                "",
                Constants.selfUser,
                "12:00",
                true
            ),
        )
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun formatDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return formatter.format(date)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}