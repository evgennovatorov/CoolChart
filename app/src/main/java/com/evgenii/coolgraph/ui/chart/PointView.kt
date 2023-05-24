package com.evgenii.coolgraph.ui.chart

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.evgenii.coolgraph.business.model.Point
import com.evgenii.coolgraph.databinding.ViewPointBinding
import com.hoc081098.viewbindingdelegate.inflateViewBinding

class PointView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = inflateViewBinding<ViewPointBinding>(true)

    fun setData(point: Point) {
        binding.xText.text = point.xString()
        binding.yText.text = point.yString()
    }

    fun setText(@StringRes x: Int, @StringRes y: Int) {
        binding.xText.text = context.getString(x)
        binding.yText.text = context.getString(y)
    }
}