package com.github.fgoncalves.canvas.di

import android.content.Context
import android.content.res.AssetManager
import com.github.fgoncalves.canvas.data.ImageLoader
import com.github.fgoncalves.canvas.data.ImageLoaderImpl
import com.github.fgoncalves.canvas.di.qualifiers.ApplicationContext
import com.github.fgoncalves.canvas.di.qualifiers.ReadAssetsSchedulerTransformer
import com.github.fgoncalves.canvas.utils.IoToUiSchedulerTransformer
import com.github.fgoncalves.canvas.utils.SchedulerTransformer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(val context: Context) {
    @Provides @Singleton @ApplicationContext
    fun providesApplicationContext(): Context = context

    @Provides @Singleton
    fun providesAssetManager(@ApplicationContext context: Context): AssetManager = context.assets

    @Provides @Singleton
    fun providesImageLoader(imageLoader: ImageLoaderImpl): ImageLoader = imageLoader

    @Provides @Singleton @ReadAssetsSchedulerTransformer
    fun providesReadAssetsSchedulerTransformer(transformer: IoToUiSchedulerTransformer): SchedulerTransformer = transformer
}
