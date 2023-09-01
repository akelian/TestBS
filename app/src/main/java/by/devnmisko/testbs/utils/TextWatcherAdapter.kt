package by.devnmisko.testbs.utils

import android.text.Editable
import android.text.TextWatcher

abstract class TextWatcherAdapter : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //no implementation
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        //no implementation
    }

    override fun afterTextChanged(s: Editable?) {
        //no implementation
    }
}