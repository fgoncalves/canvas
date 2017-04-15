package com.github.fgoncalves.canvas.presentation.viewmodels

import android.databinding.ObservableField
import android.databinding.ObservableInt
import com.github.fgoncalves.canvas.presentation.models.SwatchItem

interface SwatchListItemViewModel {
    fun color(): ObservableInt
    fun swatchName(): ObservableField<String>
    fun colorHex(): ObservableField<String>
    fun titleTextColor(): ObservableInt
    fun bodyTextColor(): ObservableInt
    fun forModel(model: SwatchItem)
}
