package com.example.desafio2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafio2.Adapters.BookAdapter
import com.example.desafio2.Models.Book
import com.example.desafio2.Utils.Books
import com.example.desafio2.Utils.User
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

    var booksList: MutableList<Book> = mutableListOf()
    var adapterBook = BookAdapter(booksList)
    var parentView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        parentView = findViewById(android.R.id.content)
        setUpView()
    }

    fun setUpView() {
        userName_label.text = User.name
        books_reclyclerView.layoutManager = LinearLayoutManager(this)
        books_reclyclerView.setHasFixedSize(true)
        booksList = Books.initBookArray()
        adapterBook = BookAdapter(booksList)
        books_reclyclerView.adapter = adapterBook
    }
}