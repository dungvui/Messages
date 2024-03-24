package com.example.messenger

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messenger.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = requireNotNull(_binding)
    private val list = arrayListOf<User>()
    private val adapter: ChatBoxAdapter by lazy {
        ChatBoxAdapter(list)
    }
    private val date = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                                list.add(
                                    User(
                                        2,
                                        "Maciej Kowalski",
                                        s.toString(),
                                        formatDate(),
                                        false,
                                        null
                                    )
                                )
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

    private fun getList(): List<User> {
        return listOf(
            User(
                1,
                "Martina Wolna",
                "I commented on Figma, I want to add some fancy icons. Do you have any icon set?",
                formatDate(),
                false,
                null
            ),
            User(
                2,
                "Maciej Kowalski",
                "I am in a process of designing some. When do you need them?",
                formatDate(),
                false,
                null
            ),
            User(
                1,
                "Martina Wolna",
                "Next month?",
                formatDate(),
                false,
                null
            ),
            User(
                2,
                "Maciej Kowalski",
                "I am almost finish. Please give me your email, I will ZIP them and send you as son as im finish.",
                formatDate(),
                false,
                null
            ),
            User(
                2,
                "Maciej Kowalski",
                "?",
                formatDate(),
                false,
                null
            ),
            User(
                1,
                "Martina Wolna",
                "maciej.kowalski@email.com",
                formatDate(),
                false,
                null
            ),
            User(
                2,
                "Maciej Kowalski",
                "",
                formatDate(),
                true,
                R.drawable.ic_like
            )
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