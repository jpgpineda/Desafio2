package com.example.desafio2

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.text.TextWatcher
import android.text.Editable
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var isValidData: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val emailText: String = til_email.editText?.text?.trim().toString()
        val password: String = til_password.editText?.text?.trim().toString()

        setupView()
        setupListeners()
        setupEvents()

    }

    fun setupView() {
        login_button.isEnabled = false
        login_button.setBackgroundColor(resources.getColor(R.color.background_gray))
    }

    private fun setupListeners() {
        tiet_email.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun afterTextChanged(text: Editable?) {
                if (text.toString().trim().isEmpty()) {
                    til_email.error = getString(R.string.invalidEmail)
                    validateFields()
                } else {
                    til_email.error = null
                    validateFields()
                }
            }
        })
        tiet_password.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun afterTextChanged(text: Editable?) {
                if (text.toString().trim().isEmpty()) {
                    til_password.error = getString(R.string.invalidPassword)
                    validateFields()
                } else {
                    til_password.error = null
                    validateFields()
                }
            }
        })
    }

    fun validateFields() {
        isValidData = true
        if (tiet_email.text.toString().isEmpty()) {
            isValidData = false
        }
        if (tiet_password.text.toString().isEmpty()) {
            isValidData = false
        }
        login_button.isEnabled = isValidData
        if (isValidData) {
            login_button.setBackgroundColor(resources.getColor(R.color.dark_blue))
        } else {
            login_button.setBackgroundColor(resources.getColor(R.color.background_gray))
        }
    }

    fun setupEvents() {
        container_view.setOnClickListener {
            hideKeyboard()
        }
        tv_signUp.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        val view = this.currentFocus
        if (view != null) {
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}