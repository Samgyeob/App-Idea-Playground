package com.example.quietlist

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var input: EditText
    private lateinit var addButton: Button
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val items = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input = findViewById(R.id.inputText)
        addButton = findViewById(R.id.addButton)
        listView = findViewById(R.id.listView)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        listView.adapter = adapter

        loadItems()

        addButton.setOnClickListener {
            val text = input.text.toString()
            if (text.isNotBlank()) {
                items.add(text)
                adapter.notifyDataSetChanged()
                saveItems()
                input.text.clear()
            }
        }
    }

    private fun saveItems() {
        val prefs = getSharedPreferences("quiet_list", MODE_PRIVATE)
        prefs.edit().putStringSet("items", items.toSet()).apply()
    }

    private fun loadItems() {
        val prefs = getSharedPreferences("quiet_list", MODE_PRIVATE)
        val saved = prefs.getStringSet("items", emptySet())
        items.addAll(saved ?: emptySet())
    }
}
