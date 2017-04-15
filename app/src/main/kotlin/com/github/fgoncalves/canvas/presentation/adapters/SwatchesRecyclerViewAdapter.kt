package com.github.fgoncalves.canvas.presentation.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.fgoncalves.canvas.CanvasApplication
import com.github.fgoncalves.canvas.databinding.SwatchListItemBinding
import com.github.fgoncalves.canvas.di.SwatchesListComponent
import com.github.fgoncalves.canvas.di.SwatchesListModule
import com.github.fgoncalves.canvas.presentation.models.SwatchItem
import com.github.fgoncalves.canvas.presentation.viewmodels.SwatchListItemViewModel
import javax.inject.Inject
import kotlin.properties.Delegates

typealias OnItemClickedCallback = (item: SwatchItem, position: Int) -> Unit

class SwatchesRecyclerViewAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items: List<SwatchItem> by Delegates.observable(emptyList()) {
        _, _, _ ->
        notifyDataSetChanged()
    }
    private var _component: SwatchesListComponent? = null
    private var component: SwatchesListComponent? = null
        get() {
            if (_component == null) {
                _component = CanvasApplication.component?.plus(SwatchesListModule())
            }
            return _component
        }
    private var onItemClickedCallback: OnItemClickedCallback? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as ItemViewHolder).bind(items[position])
        holder.itemView.setOnClickListener { onItemClickedCallback?.invoke(items[holder.adapterPosition], holder.adapterPosition) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val binding: SwatchListItemBinding = SwatchListItemBinding
                .inflate(LayoutInflater.from(parent?.context), parent, false)
        return ItemViewHolder(binding, component?.getSwatchListItemViewModel()!!)
    }

    override fun getItemCount(): Int = items.size

    fun onItemClicked(onItemClickedCallback: OnItemClickedCallback) {
        this.onItemClickedCallback = onItemClickedCallback
    }

    class ItemViewHolder(val binding: SwatchListItemBinding, val viewModel: SwatchListItemViewModel) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SwatchItem) {
            binding.viewModel = viewModel
            binding.executePendingBindings()
            viewModel.forModel(item)
        }
    }
}
