package com.github.fgoncalves.canvas.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun plus(module: SwatchesListModule): SwatchesListComponent

    fun plus(module: PresentationModule): PresentationComponent

    fun plus(module: PageScreenModule): PageScreenComponent
}
