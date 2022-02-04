package com.example.desafio2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.desafio2.Entity.Book
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_book_detail.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.book_cell.*

class BookDetail : AppCompatActivity() {

    private var parent_view: View? = null
    private lateinit var book: Book
    var details: String = ""
    var description: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)
        parent_view = findViewById(android.R.id.content)
        setupView()
    }

    fun setupView() {
        intent.extras.let {
            if (it != null) {
                book = it.getSerializable("book") as Book
                setBookData()
            }
        }
        setupListeners()
    }

    fun setBookData() {
        book_title_label.text = book.attributes.title
        book_author_label.text = book.relationships.authors.links.self
        book_category_label.text = book.relationships.categories.links.self
        book_description_label.text = book.attributes.content
        author_name_label.text = book.relationships.authors.links.self
        author_description_label.text = book.relationships.authors.links.related
        description = book.attributes.content
        details = book.attributes.createdAt
        formatImage(book.links.self ?: "", book_image_view)
        formatImage(book.relationships.authors.links.related ?: "", author_profile_imageView)
    }

    fun formatImage(image: String, container: ImageView) {
        Glide.with(this).load(image).error(R.drawable.yellow_book).diskCacheStrategy(
            DiskCacheStrategy.NONE).into(container)
    }

    fun setupListeners() {
        back_button.setOnClickListener {
            finish()
        }
        detail_share_image.setOnClickListener {
            makeSnackBar(this.getString(R.string.shareBook))
        }
        detail_like_image.setOnClickListener {
            makeSnackBar(this.getString(R.string.bookSaved))
        }
        description_button.setOnClickListener {
            book_description_label.text = description
        }
        details_button.setOnClickListener {
            book_description_label.text = details
        }
    }

    fun makeSnackBar(message: String) {
        Snackbar.make(parent_view!!, message, Snackbar.LENGTH_SHORT).show()
    }
}