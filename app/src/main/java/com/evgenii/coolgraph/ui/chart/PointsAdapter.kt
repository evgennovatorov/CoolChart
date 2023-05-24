package com.evgenii.coolgraph.ui.chart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.evgenii.coolgraph.business.model.Point

class PointsAdapter(
    private val items: Array<Point>
): RecyclerView.Adapter<PointViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PointViewHolder(PointView(parent.context))

    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() =
        items.size
}
