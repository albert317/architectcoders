package com.app.featureplaces.presentation.ui.listplaces

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.domain.Place
import com.app.presentation.R
import com.app.presentation.databinding.ViewPlaceBinding
import com.app.featureplaces.presentation.ui.common.basicDiffUtil
import com.app.featureplaces.presentation.ui.common.inflate

class PlacesAdapter(private val listener: (Place) -> Unit) :
    ListAdapter<Place, PlacesAdapter.ViewHolder>(basicDiffUtil { old, new -> old.id == new.id }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_place, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = getItem(position)
        holder.bind(place)
        holder.itemView.setOnClickListener { listener(place) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewPlaceBinding.bind(view)
        fun bind(place: Place) {
            binding.place = place
            if (place.images.isNotEmpty()) {
                binding.randomImage = place.images.first().url
            }
        }
    }
}