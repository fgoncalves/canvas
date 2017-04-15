package com.github.fgoncalves.canvas.presentation.viewmodels

import android.databinding.ObservableField
import android.databinding.ObservableInt
import com.github.fgoncalves.canvas.R
import com.github.fgoncalves.canvas.presentation.models.SwatchItem
import javax.inject.Inject

class SwatchListItemViewModelImpl @Inject constructor() : SwatchListItemViewModel {
    private val color: ObservableInt = ObservableInt(R.color.colorAccent)
    private val titleTextColor: ObservableInt = ObservableInt(R.color.colorPrimary)
    private val bodyTextColor: ObservableInt = ObservableInt(R.color.colorPrimary)
    private val colorHex: ObservableField<String> = ObservableField("")
    private val swatchName: ObservableField<String> = ObservableField("")

    override fun color(): ObservableInt = color

    override fun swatchName(): ObservableField<String> = swatchName

    override fun colorHex(): ObservableField<String> = colorHex

    override fun titleTextColor(): ObservableInt = titleTextColor

    override fun bodyTextColor(): ObservableInt = bodyTextColor

    override fun forModel(model: SwatchItem) {
        val (color, titleTextColor, bodyTextColor, colorHex, name) = model
        this.color.set(color)
        this.titleTextColor.set(titleTextColor)
        this.bodyTextColor.set(bodyTextColor)
        this.colorHex.set(colorHex)
        this.swatchName.set(name)
    }
}
