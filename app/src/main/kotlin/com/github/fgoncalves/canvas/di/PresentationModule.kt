package com.github.fgoncalves.canvas.di

import android.support.v4.app.FragmentManager
import com.github.fgoncalves.canvas.di.scopes.PresentationScope
import com.github.fgoncalves.canvas.presentation.viewmodels.MainViewModel
import com.github.fgoncalves.canvas.presentation.viewmodels.MainViewModelImpl
import dagger.Module
import dagger.Provides

@Module
class PresentationModule(val fragmentManager: FragmentManager) {
    @Provides @PresentationScope
    fun providesSwatchesListViewModel(viewModel: MainViewModelImpl): MainViewModel = viewModel

    @Provides @PresentationScope
    fun providesFragmentManager(): FragmentManager = fragmentManager
}
