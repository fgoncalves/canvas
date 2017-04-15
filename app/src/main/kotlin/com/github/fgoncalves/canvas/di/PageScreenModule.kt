package com.github.fgoncalves.canvas.di

import com.github.fgoncalves.canvas.di.scopes.PageScreenScope
import com.github.fgoncalves.canvas.presentation.viewmodels.PageViewModel
import com.github.fgoncalves.canvas.presentation.viewmodels.PageViewModelImpl
import dagger.Module
import dagger.Provides

@Module
class PageScreenModule {
    @Provides @PageScreenScope
    fun provideScreenViewModel(viewModel: PageViewModelImpl): PageViewModel = viewModel
}
