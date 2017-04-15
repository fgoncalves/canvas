package com.github.fgoncalves.canvas.presentation.viewmodels

import android.content.Context
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.graphics.Palette
import android.view.View
import com.github.fgoncalves.canvas.R
import com.github.fgoncalves.canvas.data.ImageLoader
import com.github.fgoncalves.canvas.di.qualifiers.ApplicationContext
import com.github.fgoncalves.canvas.di.qualifiers.ReadAssetsSchedulerTransformer
import com.github.fgoncalves.canvas.presentation.MainContainer
import com.github.fgoncalves.canvas.presentation.adapters.ImageViewPagerAdapter
import com.github.fgoncalves.canvas.presentation.adapters.SwatchesRecyclerViewAdapter
import com.github.fgoncalves.canvas.presentation.models.Page
import com.github.fgoncalves.canvas.presentation.models.SwatchItem
import com.github.fgoncalves.canvas.utils.SchedulerTransformer
import javax.inject.Inject


class MainViewModelImpl @Inject constructor(
        @ApplicationContext val context: Context,
        val swatchesRecyclerViewAdapter: SwatchesRecyclerViewAdapter,
        val pagerAdapter: ImageViewPagerAdapter,
        val imageLoader: ImageLoader,
        @ReadAssetsSchedulerTransformer val transformer: SchedulerTransformer) : MainViewModel {
    private val defaultStatusBarColor: Int = ContextCompat.getColor(context, R.color.black_50)
    private val icon: ObservableInt = ObservableInt(R.drawable.ic_keyboard_arrow_up)
    private val listState: ObservableField<MainContainer.ListState> = ObservableField(MainContainer.ListState.EXPANDED)
    private val onPageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            onColorPickedCallback?.invoke(defaultStatusBarColor)

            val page = pagerAdapter.items[position]
            val palette = Palette.from(page.drawable.toBitmap()).generate()
            val swatches = mutableListOf<Pair<Palette.Swatch, String>>()
            if (palette.lightVibrantSwatch != null)
                swatches.add(Pair(palette.lightVibrantSwatch!!, "light vibrant"))
            if (palette.vibrantSwatch != null)
                swatches.add(Pair(palette.vibrantSwatch!!, "vibrant"))
            if (palette.darkVibrantSwatch != null)
                swatches.add(Pair(palette.darkVibrantSwatch!!, "dark vibrant"))
            if (palette.lightMutedSwatch != null)
                swatches.add(Pair(palette.lightMutedSwatch!!, "light muted"))
            if (palette.mutedSwatch != null)
                swatches.add(Pair(palette.mutedSwatch!!, "muted"))
            if (palette.darkMutedSwatch != null)
                swatches.add(Pair(palette.darkMutedSwatch!!, "dark muted"))

            forModel(swatches)
        }
    }
    private var onColorPickedCallback: OnColorPickedCallback? = null

    override fun swatchesAdapter(): SwatchesRecyclerViewAdapter = swatchesRecyclerViewAdapter

    override fun onCreate() {
        imageLoader.load()
                .compose(transformer.applyObservableSchedulers<Drawable>())
                .toList()
                .subscribe(
                        { drawables ->
                            pagerAdapter.items = drawables.map(::Page)
                            // force first palette
                            onPageChangeListener.onPageSelected(0)
                            swatchesRecyclerViewAdapter.onItemClicked { (color), _ -> onColorPickedCallback?.invoke(color) }
                        },
                        Throwable::printStackTrace)
    }

    override fun onPageChangeListener(): ViewPager.OnPageChangeListener = onPageChangeListener

    override fun icon(): ObservableInt = icon

    override fun listState(): ObservableField<MainContainer.ListState> = listState

    override fun imagesAdapter(): PagerAdapter = pagerAdapter

    override fun onHeaderClicked(view: View) {
        if (listState.get() == MainContainer.ListState.EXPANDED) {
            listState.set(MainContainer.ListState.COLLAPSED)
            icon.set(R.drawable.ic_keyboard_arrow_up)
            return
        }
        listState.set(MainContainer.ListState.EXPANDED)
        icon.set(R.drawable.ic_keyboard_arrow_down)
    }

    override fun onColorPicked(onColorPickedCallback: OnColorPickedCallback) {
        this.onColorPickedCallback = onColorPickedCallback
    }

    override fun onBackPressed(onBackPressedCallback: OnBackPressedCallback) {
        when (listState.get()) {
            MainContainer.ListState.EXPANDED -> {
                listState.set(MainContainer.ListState.COLLAPSED)
                onBackPressedCallback(true)
                return
            }
            MainContainer.ListState.COLLAPSED -> {
                onBackPressedCallback(false)
                return
            }
            else -> return
        }
    }

    private fun forModel(swatches: List<Pair<Palette.Swatch, String>>) {
        val items: MutableList<SwatchItem> = mutableListOf()

        swatches.forEach { pair ->
            val (swatch, name) = pair
            val item: SwatchItem = SwatchItem(swatch.rgb, swatch.titleTextColor, swatch.bodyTextColor, swatch.rgb.toColorHex(), name)
            items.add(item)
        }

        swatchesRecyclerViewAdapter.items = items
    }

    private fun Int.toColorHex(): String = String.format("#%06X", 0xFFFFFF and this)

    private fun Drawable.toBitmap(): Bitmap {
        val bitmap: Bitmap?

        if (this is BitmapDrawable) {
            if (this.bitmap != null) {
                return this.bitmap
            }
        }

        if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(bitmap)
        setBounds(0, 0, canvas.width, canvas.height)
        draw(canvas)
        return bitmap
    }
}
