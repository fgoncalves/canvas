package com.github.fgoncalves.canvas.presentation.viewmodels

import android.databinding.ObservableField
import android.graphics.drawable.Drawable
import com.github.fgoncalves.canvas.presentation.models.Page
import javax.inject.Inject

class PageViewModelImpl @Inject constructor() : PageViewModel {
    private val drawable = ObservableField<Drawable>()

    override fun drawable(): ObservableField<Drawable> = drawable

    override fun forModel(page: Page) {
        drawable.set(page.drawable)
    }
}
