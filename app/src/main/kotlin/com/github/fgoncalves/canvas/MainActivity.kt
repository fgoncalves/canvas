package com.github.fgoncalves.canvas

import android.animation.ObjectAnimator
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.WindowManager
import com.github.fgoncalves.canvas.databinding.ActivityMainBinding
import com.github.fgoncalves.canvas.di.ApplicationComponent
import com.github.fgoncalves.canvas.di.PresentationComponent
import com.github.fgoncalves.canvas.di.PresentationModule


class MainActivity : AppCompatActivity() {
    private var toggle: ActionBarDrawerToggle? = null
    private val applicationComponent: ApplicationComponent? = CanvasApplication.component
    private var binding: ActivityMainBinding? = null
    private var _presentationComponent: PresentationComponent? = null
    var presentationComponent: PresentationComponent?
        get() {
            if (_presentationComponent == null) _presentationComponent = applicationComponent?.plus(PresentationModule(supportFragmentManager))
            return _presentationComponent
        }
        set(value) {
            _presentationComponent = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewModel = presentationComponent?.getSwatchesListViewModel()
        binding.viewModel.onCreate()
        binding.viewModel.onColorPicked(this::setStatusBarColor)

        val toolbar: Toolbar? = findViewById(R.id.toolbar) as Toolbar?
        if (toolbar != null) setSupportActionBar(toolbar)

        val drawer: DrawerLayout? = findViewById(R.id.swatches_drawer) as DrawerLayout?
        if (drawer != null) {
            toggle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)
            drawer.addDrawerListener(toggle!!)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        this.binding = binding
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle?.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (toggle?.onOptionsItemSelected(item) == true)
            return true
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        presentationComponent = null
    }

    override fun onBackPressed() {
        binding?.viewModel?.onBackPressed { handled -> if (!handled) super.onBackPressed() }
    }

    fun setStatusBarColor(colorRgb: Int) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        val animator = ObjectAnimator.ofArgb(window, "statusBarColor", window.statusBarColor, colorRgb)
        animator.duration = 300
        animator.start()
    }
}
