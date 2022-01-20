package com.example.desafio2.Adapters

import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio2.BookDetail
import com.example.desafio2.Models.Book
import com.example.desafio2.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar

class BookAdapter(val books: MutableList<Book>): RecyclerView.Adapter<BookAdapter.BookHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.BookHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return BookHolder(layoutInflater.inflate(R.layout.book_cell, parent, false))
    }

    override fun onBindViewHolder(holder: BookAdapter.BookHolder, position: Int) {
        holder.render(books[position])
    }

    override fun getItemCount(): Int = books.size

    class BookHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val bookCardView: MaterialCardView = view.findViewById(R.id.book_containerCardView)
        val titleLabel: TextView = view.findViewById(R.id.title_label)
        val authorLabel: TextView = view.findViewById(R.id.cell_author_label)
        val categoryLabel: TextView = view.findViewById(R.id.cell_category_label)
        val bookImageView: ImageView = view.findViewById(R.id.book_imageView)
        val likeImage: ImageView = view.findViewById(R.id.like_image)
        val shareImage: ImageView = view.findViewById(R.id.share_image)

        fun render(book: Book) {
            titleLabel.setText(book.title)
            authorLabel.setText(book.author.name)
            categoryLabel.setText(book.category)
            Glide.with(view).load(book.image).error(R.drawable.blue_book).diskCacheStrategy(
                DiskCacheStrategy.NONE).into(bookImageView)
            setupListeners(book)
        }

        fun setupListeners(book: Book) {
            likeImage.setOnClickListener {
                createSnackBar(view.context.getString(R.string.bookSaved))
            }
            shareImage.setOnClickListener {
                createSnackBar(view.context.getString(R.string.shareBook))
            }
            bookCardView.setOnClickListener {
                val intent = Intent(view.context, BookDetail::class.java)
                intent.putExtra("bookName", book.title)
                intent.putExtra("authorName", book.author.name)
                intent.putExtra("category", book.category)
                intent.putExtra("details", book.details)
                intent.putExtra("bookImage", book.image)
                intent.putExtra("description", book.description)
                intent.putExtra("authorDescription", book.author.about)
                intent.putExtra("authorImage", book.author.image)
                view.context.startActivity(intent)
            }
        }

        fun createSnackBar(message: String) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    fun insertBook(book: Book) {
        this.books.add(book)
        notifyItemInserted(itemCount)
    }
}