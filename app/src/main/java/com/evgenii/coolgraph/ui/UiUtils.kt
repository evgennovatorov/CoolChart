package com.evgenii.coolgraph.ui

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

object UiUtils {

    fun EditText.getTextEmptyFlow() = callbackFlow {
        val textWatcher = object : TextWatcher {

            private var isEmpty = true

            override fun afterTextChanged(s: Editable?) {
                if (s?.toString().isNullOrEmpty() != isEmpty) {
                    isEmpty = !isEmpty
                    trySend(isEmpty)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
        addTextChangedListener(textWatcher)
        awaitClose { removeTextChangedListener(textWatcher) }
    }

    fun View.show() {
        visibility = View.VISIBLE
    }

    fun View.hide() {
        visibility = View.GONE
    }

    fun View.setInvisible(isInvisible: Boolean) {
        if (isInvisible) {
            visibility = View.INVISIBLE
        } else {
            visibility = View.VISIBLE
        }
    }
}