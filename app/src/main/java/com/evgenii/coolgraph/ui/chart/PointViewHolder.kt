package com.evgenii.coolgraph.ui.chart

import androidx.recyclerview.widget.RecyclerView
import com.evgenii.coolgraph.business.model.Point

class PointViewHolder(
    private val pointView: PointView
): RecyclerView.ViewHolder(pointView) {

    fun bind(point: Point) {
        pointView.setData(point)
    }
}