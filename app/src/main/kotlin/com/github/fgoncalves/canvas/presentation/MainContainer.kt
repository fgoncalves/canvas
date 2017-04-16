package com.github.fgoncalves.canvas.presentation

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.DecelerateInterpolator
import com.github.fgoncalves.canvas.R

typealias LinearFunction = (Float) -> Float

/**
 * Responsible mainly for expanding and collapsing the list view based on a percentage of its total
 * movement - 100% is fully at the bottom aka collapsed and 0% is at the top, a.k.a at the expanded
 */
class MainContainer(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : ConstraintLayout(context, attrs, defStyleAttr) {
    enum class ListState {
        EXPANDED,
        EXPANDING,
        COLLAPSED,
        COLLAPSING
    }

    private val DEFAULT_DURATION: Long = 300

    private var internalListState: ListState = ListState.EXPANDED
    private var currentMovedPercentage: Float = 0f
    private lateinit var recyclerView: RecyclerView
    private lateinit var header: View
    private val headerYCalculator: LinearFunction  by lazy {
        val initialY = header.y
        val finalY = (bottom - header.height).toFloat()
        curriedRecyclerViewHeightCalculator(initialY, finalY)
    }

    constructor(context: Context?) : this(context, null, 0)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun onFinishInflate() {
        super.onFinishInflate()
        recyclerView = findViewById(R.id.swatches_recyclerview) as RecyclerView
        header = findViewById(R.id.swatches_header)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        recyclerView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                recyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                moveListToBottom()
            }
        })
    }

    fun setListState(state: ListState?) {
        // Need to synchronize this somehow

        if (state == null || state == internalListState) return

//        if (state == ListState.EXPANDING || state == ListState.COLLAPSING)


        when (state) {
            ListState.EXPANDING, ListState.COLLAPSING -> throw IllegalArgumentException("Can only set state to collapsed or expended")
            ListState.EXPANDED -> animateRecyclerViewToTop()
            ListState.COLLAPSED -> animateRecyclerViewToBottom()
        }
    }

    private fun animateRecyclerViewToBottom() {
        moveRecycleViewTo(1f, DEFAULT_DURATION)
    }

    private fun animateRecyclerViewToTop() {
        moveRecycleViewTo(0f, DEFAULT_DURATION)
    }

    private fun moveListToBottom() {
        moveRecycleViewTo(1f, 0)
    }

    private fun moveRecycleViewTo(percentageOfMovement: Float, duration: Long) {
        val recyclerViewAnimator = ObjectAnimator.ofFloat(recyclerView, "y", headerYCalculator(percentageOfMovement) + header.height)
        val headerAnimator = ObjectAnimator.ofFloat(header, "y", headerYCalculator(percentageOfMovement))
        val animator = AnimatorSet()
        animator.interpolator = DecelerateInterpolator()
        animator.duration = duration
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                currentMovedPercentage = percentageOfMovement
                internalListState = if (currentMovedPercentage == 1f) ListState.COLLAPSED else ListState.EXPANDED
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
                internalListState = if (currentMovedPercentage < percentageOfMovement) ListState.COLLAPSING else ListState.EXPANDING
            }
        })
        animator.playTogether(headerAnimator, recyclerViewAnimator)
        animator.start()
    }

    fun curriedRecyclerViewHeightCalculator(initialY: Float, finalY: Float): LinearFunction {
        return { percentage -> maxOf(0f, minOf(finalY, (finalY - initialY) * percentage + initialY)) }
    }
}
