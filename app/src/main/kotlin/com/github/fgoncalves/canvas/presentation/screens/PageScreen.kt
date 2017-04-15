package com.github.fgoncalves.canvas.presentation.screens

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.fgoncalves.canvas.CanvasApplication
import com.github.fgoncalves.canvas.databinding.PageBinding
import com.github.fgoncalves.canvas.di.PageScreenComponent
import com.github.fgoncalves.canvas.di.PageScreenModule
import com.github.fgoncalves.canvas.presentation.models.Page

class PageScreen(val page: Page) : Fragment() {
    private var _component: PageScreenComponent? = null
    private var component: PageScreenComponent?
        get() {
            if (_component == null) {
                _component = CanvasApplication.component?.plus(PageScreenModule())
            }
            return _component
        }
        set(value) {
            _component = null
        }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = PageBinding.inflate(inflater, container, false)

        binding.viewModel = component?.getPageScreenViewModel()
        binding.viewModel.forModel(page)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        component = null
    }
}
