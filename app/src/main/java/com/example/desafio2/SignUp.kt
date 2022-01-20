package com.example.desafio2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import com.example.desafio2.Utils.User
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    var isValidData: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        setupView()
        setupListeners()
        setupEvents()
    }

    fun setupView() {
        signup_button.isEnabled = false
        signup_button.setBackgroundColor(resources.getColor(R.color.background_gray))
    }

    fun setupListeners() {
        tiet_userName.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun afterTextChanged(editText: Editable?) {
                if (editText.toString().trim().isEmpty()) {
                    tiel_userName.error = getString(R.string.invalidUserName)
                    validateFields()
                } else {
                    tiel_userName.error = null
                    validateFields()
                }
            }
        })
        tiet_signup_email.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun afterTextChanged(editText: Editable?) {
                if (editText.toString().trim().isEmpty()) {
                    til_signup_email.error = getString(R.string.invalidEmail)
                    validateFields()
                } else {
                    til_signup_email.error = null
                    validateFields()
                }
            }
        })
        tiet_signup_password.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun afterTextChanged(editText: Editable?) {
                if (editText.toString().trim().isEmpty()) {
                    til_signup_password.error = getString(R.string.invalidPassword)
                    validateFields()
                } else {
                    til_signup_password.error = null
                    validateFields()
                }
            }
        })
        tiet_confirm_password.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun afterTextChanged(editText: Editable?) {
                if (editText.toString().trim().isEmpty() ||
                    editText.toString().trim() != tiet_signup_password.text.toString().trim()) {
                     til_confirm_password.error = getString(R.string.invalidConfirmPassword)
                    validateFields()
                } else {
                    til_confirm_password.error = null
                    validateFields()
                }
            }
        })
    }

    fun validateFields() {
        isValidData = true
        if (tiet_userName.text.toString().isEmpty()) {
            isValidData = false
        }
        if (tiet_signup_email.text.toString().isEmpty()) {
            isValidData = false
        }
        if (tiet_signup_password.text.toString().isEmpty()) {
            isValidData = false
        }
        if (tiet_confirm_password.text.toString().isEmpty() ||
            tiet_confirm_password.text.toString() != tiet_signup_password.text.toString()) {
            isValidData = false
        }
        signup_button.isEnabled = isValidData
        if (isValidData) {
            signup_button.setBackgroundColor(resources.getColor(R.color.dark_blue))
        } else {
            signup_button.setBackgroundColor(resources.getColor(R.color.background_gray))
        }
    }

    fun setupEvents() {
        signUp_containerView.setOnClickListener {
            hideKeyboard()
        }
        signup_button.setOnClickListener {
            User.name = tiet_userName.text.toString()
            startActivity(Intent(this, Home::class.java))
        }
        backToLogin_button.setOnClickListener {
            finish()
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