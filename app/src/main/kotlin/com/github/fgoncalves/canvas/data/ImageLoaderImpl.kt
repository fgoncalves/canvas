package com.github.fgoncalves.canvas.data

import android.content.res.AssetManager
import android.graphics.drawable.Drawable
import io.reactivex.Observable
import java.io.InputStream
import javax.inject.Inject

class ImageLoaderImpl @Inject constructor(val assetManager: AssetManager) : ImageLoader {
    override fun load(): Observable<Drawable> {
        return Observable.fromCallable { assetManager.list("backgrounds") }
                .flatMap { imageNames -> Observable.fromArray(*imageNames) }
                .map({ imageName ->
                    var inputStream: InputStream? = null
                    try {
                        inputStream = assetManager.open("backgrounds/$imageName")
                        return@map Drawable.createFromStream(inputStream, null)
                    } finally {
                        inputStream?.close()
                    }
                })
    }
}
