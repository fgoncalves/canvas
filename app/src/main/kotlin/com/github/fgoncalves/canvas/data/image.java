package com.github.fgoncalves.canvas.data;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 *
 */

public class image implements ImageLoader {
    private AssetManager assetManager;

    @NotNull
    @Override
    public Observable<Drawable> load() {
        return Observable.fromCallable(new Callable<String[]>() {
            @Override
            public String[] call() throws Exception {
                return assetManager.list("backgrounds");
            }
        }).flatMap(new Function<String[], ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull String[] strings) throws Exception {
                return Observable.fromArray(strings);
            }
        }).map(new Function<String, Drawable>() {
            @Override
            public Drawable apply(@NonNull String s) throws Exception {
                InputStream inputStream = null;
                try {
                    inputStream = assetManager.open(s);
                    return Drawable.createFromStream(inputStream, null);
                } finally {
                    if (inputStream != null) inputStream.close();
                }
            }
        });
    }
}
