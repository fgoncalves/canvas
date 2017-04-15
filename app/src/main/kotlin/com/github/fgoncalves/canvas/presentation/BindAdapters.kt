package com.github.fgoncalves.canvas.presentation

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView


@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, resource: Int) = imageView.setImageResource(resource)

@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, drawable: Drawable) = imageView.setImageDrawable(drawable)

@BindingAdapter("android:titleTextColor")
fun setTextColor(textView: TextView, rgb: Int) = textView.setTextColor(rgb)
