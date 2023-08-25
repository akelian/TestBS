package by.devnmisko.testbs.utils

import android.app.AlertDialog
import android.content.Context
import android.view.View

fun View.hide(){
    this.visibility = View.GONE
}

fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.setOnSafeClickListener(
    onSafeClick: (View) -> Unit
) {
    setOnClickListener(
        SafeClickListener { v ->
            onSafeClick(v)
        }
    )
}
