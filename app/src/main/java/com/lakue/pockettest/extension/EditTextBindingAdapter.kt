package com.lakue.pockettest.extension

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter

@BindingAdapter("onEditorTextChangeAction")
fun EditText.onEditorTextChangeAction(f: Function1<String, Unit>?) {

    if (f == null) addTextChangedListener(null)
    else addTextChangedListener {
        f(it.toString())
    }
}