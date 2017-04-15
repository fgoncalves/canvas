package com.github.fgoncalves.canvas.utils

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class IoToUiSchedulerTransformer @Inject constructor() : SchedulerTransformer {
    override fun <T> applyObservableSchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
    }
}
