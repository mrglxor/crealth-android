package com.bangkit.crealth.view.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.bangkit.crealth.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CustomRegisterEmailTextInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextInputLayout(context, attrs, defStyleAttr) {

    private val emailEditText: TextInputEditText
    private val emailLayout: TextInputLayout

    init {
        inflate(context, R.layout.activity_custom_register_email_text_input_layout, this)

        emailEditText = findViewById(R.id.ed_register_email)
        emailLayout = findViewById(R.id.emailTextInputLayout)

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = s.toString().trim()
                val isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                if (!isValidEmail) {
                    emailLayout.error = "Format email salah"
                } else {
                    emailLayout.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }
    fun getEmail(): String {
        return emailEditText.text.toString()
    }
    fun setEmailFormatChangeListener(listener: (Boolean) -> Unit) {
        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isValidEmail = s?.let { android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches() }
                if (isValidEmail != null) {
                    listener.invoke(isValidEmail)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}