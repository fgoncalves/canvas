package com.github.fgoncalves.canvas.presentation.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.github.fgoncalves.canvas.presentation.models.Page
import com.github.fgoncalves.canvas.presentation.screens.PageScreen
import javax.inject.Inject
import kotlin.properties.Delegates

class ImageViewPagerAdapter @Inject constructor(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    var items: List<Page> by Delegates.observable(emptyList()) {
        _, _, _ ->
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return PageScreen.newInstance(items[position])
    }

    override fun getCount(): Int = items.size
}
