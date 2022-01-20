package com.example.desafio2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_book_detail.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.book_cell.*

class BookDetail : AppCompatActivity() {

    private var parent_view: View? = null
    var details: String = ""
    var description: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)
        parent_view = findViewById(android.R.id.content)
        setupView()
    }

    fun setupView() {
        val intent = intent
        book_title_label.text = intent.getStringExtra("bookName")
        book_author_label.text = intent.getStringExtra("authorName")
        book_category_label.text = intent.getStringExtra("category")
        details = intent.getStringExtra("details") ?: getString(R.string.lorem)
        val bookImage = intent.getStringExtra("bookImage")
        description = intent.getStringExtra("description") ?: getString(R.string.lorem)
        book_description_label.text = description
        author_name_label.text = intent.getStringExtra("authorName")
        author_description_label.text = intent.getStringExtra("authorDescription")
        val authorImage = intent.getStringExtra("authorImage")
        formatImage(bookImage ?: "", book_image_view)
        formatImage(authorImage ?: "", author_profile_imageView)
        setupListeners()
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