package com.bangkit.crealth.view.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.bangkit.crealth.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CustomRegisterPasswordTextInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextInputLayout(context, attrs, defStyleAttr) {

    private val passwordEditText: TextInputEditText
    private val passwordLayout: TextInputLayout

    init {
        inflate(context, R.layout.activity_custom_register_password_text_input_layout, this)

        passwordEditText = findViewById(R.id.ed_register_password)
        passwordLayout = findViewById(R.id.passwordTextInputLayout)

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().length < 8) {
                    passwordLayout.error = "Kata sandi minimal harus 8 karakter"
                } else {
                    passwordLayout.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    fun getPassword(): String {
        return passwordEditText.text.toString()
    }
    fun setTextChangeListener(listener: (Boolean) -> Unit) {
        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isValid = s.toString().length >= 8
                listener.invoke(isValid)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}