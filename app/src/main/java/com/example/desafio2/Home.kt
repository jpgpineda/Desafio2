package com.example.desafio2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.desafio2.Adapters.BookAdapter
import com.example.desafio2.Entity.Book
import com.example.desafio2.Entity.BookData
import com.example.desafio2.Extensions.isOnline
import com.example.desafio2.Extensions.showMessage
import com.example.desafio2.SharedPreferences.getSessionInfo
import com.example.desafio2.Utils.User
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class Home : AppCompatActivity() {

    var booksList: MutableList<Book> = mutableListOf()
    var adapterBook = BookAdapter(booksList)
    var parentView: View? = null
    private val TAG = Home::class.qualifiedName


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
        requestBookData()
    }

    fun requestBookData() {
        if (isOnline(applicationContext)) {
            sendBookDataRequest()
        } else {
            showMessage(this, getString(R.string.noInternetConnection))
        }
    }

    fun setupRecylclerView(books: MutableList<Book>) {
        books_reclyclerView.layoutManager = LinearLayoutManager(this)
        books_reclyclerView.setHasFixedSize(true)
        booksList = books
        adapterBook = BookAdapter(books)
        books_reclyclerView.adapter = adapterBook
        adapterBook.notifyDataSetChanged()
    }

    fun sendBookDataRequest() {
        val tail = Volley.newRequestQueue(applicationContext)
        val url = getString(R.string.baseUrl) + getString(R.string.booksUrl)
        val request = object : JsonObjectRequest(Request.Method.GET, url, null, { response ->
            Log.d(TAG, response.toString())
            val bookData = Json.decodeFromString<BookData>(response.toString())
            setupRecylclerView(bookData.data)
        }, { error ->
            Log.e(TAG, error.localizedMessage)
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] =
                    "Bearer ${getSessionInfo(applicationContext, getString(R.string.token))}"
                headers["Accept"] = "application/json"
                headers["Content-type"] = "application/json"
                return headers
            }
        }
        tail.add(request)
    }
}