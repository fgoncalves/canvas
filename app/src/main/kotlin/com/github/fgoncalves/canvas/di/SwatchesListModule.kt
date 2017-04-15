package com.github.fgoncalves.canvas.di

import com.github.fgoncalves.canvas.presentation.viewmodels.SwatchListItemViewModel
import com.github.fgoncalves.canvas.presentation.viewmodels.SwatchListItemViewModelImpl
import dagger.Module
import dagger.Provides

@Module
class SwatchesListModule {
    @Provides
    fun providesSwatchesListItemViewModel(viewModel: SwatchListItemViewModelImpl): SwatchListItemViewModel = viewModel
}
