package com.example.chatapplication

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("error")
fun bindErrorInTextInputLayout(
    textInputLayout: TextInputLayout,
    errorMessage: String?
) {
    textInputLayout.error = errorMessage
}


@BindingAdapter("textId")
fun bindStringInTextView(textView: TextView, titleId: Int) {
    textView.setText(titleId)
}

@BindingAdapter("imageId")
fun bindImageUsingImageId(imageView: ImageView, imageId: Int) {
    imageView.setImageResource(imageId)
}