package com.example.desafio2

import android.content.Context
import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.cardview.widget.CardView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.desafio2.Entity.ApiError
import com.example.desafio2.Entity.Token
import com.example.desafio2.Extensions.isOnline
import com.example.desafio2.Extensions.showMessage
import com.example.desafio2.SharedPreferences.login
import com.example.desafio2.SharedPreferences.validateSession
import com.example.desafio2.Utils.User
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject

class SignUp : AppCompatActivity() {

    var isValidData: Boolean = true
    private val TAG = SignUp::class.qualifiedName
    private lateinit var loaderView: CardView
    private lateinit var tietUserName: TextInputEditText
    private lateinit var tietEmail: TextInputEditText
    private lateinit var tietPassword: TextInputEditText
    private lateinit var tietConfirmPassword: TextInputEditText

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
        loaderView = findViewById(R.id.loader_view)
        tietUserName = findViewById(R.id.tiet_userName)
        tietEmail = findViewById(R.id.tiet_signup_email)
        tietPassword = findViewById(R.id.tiet_signup_password)
        tietConfirmPassword  = findViewById(R.id.tiet_confirm_password)
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
            requestSignUp()
        }
        backToLogin_button.setOnClickListener {
            finish()
        }
    }

    fun requestSignUp() {
        if (isOnline(applicationContext)) {
            sendSignUpRequest()
        } else {
            showMessage(this, getString(R.string.noInternetConnection))
        }
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        val view = this.currentFocus
        if (view != null) {
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun sendSignUpRequest() {
        val json = JSONObject()
        json.put(getString(R.string.userNameApiKey), tietUserName.text)
        json.put(getString(R.string.emailApiKey), tietEmail.text)
        json.put(getString(R.string.passwordApiKey), tietPassword.text)
        json.put(getString(R.string.confirmPasswordApiKey), tietConfirmPassword.text)
        json.put(getString(R.string.deviceNameApiKey), android.os.Build.MODEL)
        val tail = Volley.newRequestQueue(applicationContext)
        val url = getString(R.string.baseUrl) + getString(R.string.registerUrl)
        loaderView.visibility = View.VISIBLE
        val request = JsonObjectRequest(Request.Method.POST, url, json, { response ->
            Log.d(TAG, response.toString())
            val token = Json.decodeFromString<Token>(response.toString())
            login(applicationContext, token.token)
            loaderView.visibility = View.GONE
            if (validateSession(applicationContext)) {
                startActivity(Intent(this, Home::class.java))
            }
        }, { error ->
            val apiError = Json.decodeFromString<ApiError>(error.toString()).errors.first()
            Log.e(TAG, apiError.detail)
            loaderView.visibility = View.GONE
        })
        tail.add(request)
    }
}