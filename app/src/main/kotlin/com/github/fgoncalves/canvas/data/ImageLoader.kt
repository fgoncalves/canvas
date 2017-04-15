package com.github.fgoncalves.canvas.data

import android.graphics.drawable.Drawable
import io.reactivex.Observable

interface ImageLoader {
    /**
     * Emit the images one by one
     */
    fun load(): Observable<Drawable>
}
