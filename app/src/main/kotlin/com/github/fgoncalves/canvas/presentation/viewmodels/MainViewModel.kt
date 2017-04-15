package com.github.fgoncalves.canvas.presentation.viewmodels

import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.fgoncalves.canvas.presentation.MainContainer

typealias OnColorPickedCallback = (colorRgb: Int) -> Unit
typealias OnBackPressedCallback = (handled: Boolean) -> Unit

interface MainViewModel {
    fun swatchesAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>

    fun imagesAdapter(): PagerAdapter

    fun onPageChangeListener(): ViewPager.OnPageChangeListener

    fun icon(): ObservableInt

    fun listState(): ObservableField<MainContainer.ListState>

    fun onHeaderClicked(view: View)

    fun onCreate()

    fun onColorPicked(onColorPickedCallback: OnColorPickedCallback)

    fun onBackPressed(onBackPressedCallback: OnBackPressedCallback)
}
