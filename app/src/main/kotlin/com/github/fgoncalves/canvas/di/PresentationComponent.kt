package com.github.fgoncalves.canvas.di

import com.github.fgoncalves.canvas.di.scopes.PresentationScope
import com.github.fgoncalves.canvas.presentation.viewmodels.MainViewModel
import dagger.Subcomponent

@PresentationScope
@Subcomponent(modules = arrayOf(PresentationModule::class))
interface PresentationComponent {
    fun getSwatchesListViewModel(): MainViewModel
}
