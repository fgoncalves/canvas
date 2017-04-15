package com.github.fgoncalves.canvas.di

import com.github.fgoncalves.canvas.presentation.viewmodels.SwatchListItemViewModel
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(SwatchesListModule::class))
interface SwatchesListComponent {
    fun getSwatchListItemViewModel(): SwatchListItemViewModel
}
