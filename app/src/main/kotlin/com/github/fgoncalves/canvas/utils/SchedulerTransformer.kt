package com.github.fgoncalves.canvas.utils

import io.reactivex.ObservableTransformer

interface SchedulerTransformer {
    fun <T> applyObservableSchedulers(): ObservableTransformer<T, T>
}
