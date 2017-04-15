package com.github.fgoncalves.canvas

import android.app.Application
import com.github.fgoncalves.canvas.di.ApplicationComponent
import com.github.fgoncalves.canvas.di.ApplicationModule
import com.github.fgoncalves.canvas.di.DaggerApplicationComponent

class CanvasApplication : Application() {
    companion object {
        var component: ApplicationComponent? = null
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}
