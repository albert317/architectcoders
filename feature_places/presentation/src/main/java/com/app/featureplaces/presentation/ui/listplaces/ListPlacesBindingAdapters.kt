package com.app.featureplaces.presentation.ui.listplaces

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.domain.Place
import com.app.featureplaces.presentation.data.loadUrl

@BindingAdapter("items")
fun RecyclerView.setItems(places: List<Place>?) {
    if (places != null) {
        (adapter as? PlacesAdapter)?.submitList(places)
    }
}

@BindingAdapter("url")
fun ImageView.bindUrl(url: String?) {
    if (url != null) loadUrl(url)
}