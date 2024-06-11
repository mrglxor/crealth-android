package com.bangkit.crealth.view.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.bangkit.crealth.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CustomRegisterNameTextInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextInputLayout(context, attrs, defStyleAttr) {
    private val nameEditText: TextInputEditText
    private val nameLayout: TextInputLayout

    init {
        inflate(context, R.layout.activity_custom_register_name_text_input_layout, this)

        nameEditText = findViewById(R.id.ed_register_name)
        nameLayout = findViewById(R.id.nameTextInputLayout)

        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().length < 4) {
                    nameLayout.error = "Nama minimal harus 4 karakter"
                } else {
                    nameLayout.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    fun getName(): String {
        return nameEditText.text.toString()
    }
    fun setTextChangeListener(listener: (Boolean) -> Unit) {
        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isValid = s.toString().length >= 4
                listener.invoke(isValid)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}