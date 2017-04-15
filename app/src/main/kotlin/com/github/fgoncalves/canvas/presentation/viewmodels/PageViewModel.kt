package com.github.fgoncalves.canvas.presentation.viewmodels

import android.databinding.ObservableField
import android.graphics.drawable.Drawable
import com.github.fgoncalves.canvas.presentation.models.Page

interface PageViewModel {
    fun drawable(): ObservableField<Drawable>

    fun forModel(page: Page)
}
