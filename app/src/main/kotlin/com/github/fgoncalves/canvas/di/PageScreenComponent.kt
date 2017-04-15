package com.github.fgoncalves.canvas.di

import com.github.fgoncalves.canvas.di.scopes.PageScreenScope
import com.github.fgoncalves.canvas.presentation.viewmodels.PageViewModel
import dagger.Subcomponent

@PageScreenScope
@Subcomponent(modules = arrayOf(PageScreenModule::class))
interface PageScreenComponent {
    fun getPageScreenViewModel(): PageViewModel
}
