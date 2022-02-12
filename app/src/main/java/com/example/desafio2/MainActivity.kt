package com.example.desafio2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.text.TextWatcher
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.cardview.widget.CardView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.desafio2.Entity.ApiError
import com.example.desafio2.Entity.Token
import com.example.desafio2.Entity.User
import com.example.desafio2.Extensions.isOnline
import com.example.desafio2.Extensions.showMessage
import com.example.desafio2.SharedPreferences.getSessionInfo
import com.example.desafio2.SharedPreferences.login
import com.example.desafio2.Utils.isEmailValid
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var isValidData: Boolean = true
    private val TAG = SignUp::class.qualifiedName
    private lateinit var loaderView: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loaderView = findViewById(R.id.login_progressBar)

        setupView()
        setupListeners()
        setupEvents()
        setupSora()

        /*
        Lorem Ipsum is simply dummy text of the printing and
        typesetting industry. Lorem Ipsum has been the industry's
         standard dummy text ever since the 1500s, when an unknown
         printer took a galley of type and scrambled it to make a
         type specimen book. It has survived not only five centuries,
         but also the leap into electronic typesetting, remaining essentially
          unchanged. It was popularised in the 1960s with the release of Letraset
           sheets containing Lorem Ipsum passages, and more recently with desktop
            publishing software like Aldus PageMaker including versions of Lorem Ipsum. */
    }

    private fun setupSora() {
        //cambios de sora para actividad de taller
        login_button.isEnabled = true
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
                } else if (!text.toString().trim().isEmailValid()) {
                    til_email.error = getString(R.string.invalidEmailFormat)
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
                    validateFields()
                    setupEvents()
                } else {
                    til_password.error = null
                    validateFields()
                    validateFields()
                    setupEvents()
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
        login_button.setOnClickListener {
            requestLogin()
        }
    }

    fun requestLogin() {
        if (isOnline(applicationContext)) {
            sendLoginRequest()
        } else {
            showMessage(this, getString(R.string.noInternetConnection))
        }
    }

    fun sendLoginRequest() {
        val tail = Volley.newRequestQueue(applicationContext)
        val json = JSONObject()
        json.put(getString(R.string.emailApiKey), tiet_email.text)
        json.put(getString(R.string.passwordApiKey), tiet_password.text)
        json.put(getString(R.string.deviceNameApiKey), android.os.Build.MODEL)
        val url = getString(R.string.baseUrl) + getString(R.string.loginUrl)
        manageLoader(true)
        val request = JsonObjectRequest(Request.Method.POST , url , json, { response ->
            Log.d(TAG, response.toString())
            val token = Json.decodeFromString<Token>(response.toString())
            login(applicationContext, token.token)
            manageLoader(false)
            startActivity(Intent(this, Home::class.java))
        }, { error ->
            manageLoader(false)
            val apiError = Json.decodeFromString<ApiError>(error.toString()).errors.first()
            Log.e(TAG, apiError.detail)
            showMessage(this, apiError.detail)
        })
        tail.add(request)
    }

    fun manageLoader(isOn: Boolean) {
        if (isOn) {
            loaderView.visibility = View.VISIBLE
        } else {
            loaderView.visibility = View.GONE
        }
        login_button.isEnabled = !isOn
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        val view = this.currentFocus
        if (view != null) {
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun sendUserInfoRequest() {
        val tail = Volley.newRequestQueue(applicationContext)
        val url = getString(R.string.baseUrl) + getString(R.string.userUrl)
        val request = object: StringRequest(Request.Method.POST, url, { response ->
            Log.d(TAG, response.toString())
            manageLoader(false)
            val user = Json.decodeFromString<User>(response.toString())
            val intent = Intent(this, Home::class.java)
            val bundle = Bundle()
            bundle.putSerializable("user", user)
            intent.putExtras(bundle)
            startActivity(intent)
        }, { error ->
            val apiError = Json.decodeFromString<ApiError>(error.toString())
            val errorDetail = apiError.errors.first()
            Log.e(TAG, errorDetail.detail)
            manageLoader(false)
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                headers["Content-Type"] = "application/json"
                headers["Authorization"] = "Bearer" + getSessionInfo(applicationContext, "token")
                return headers
            }
        }
        tail.add(request)
    }
 }