package com.evgenii.coolgraph.ui.chart

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.evgenii.coolgraph.databinding.FragmentChartBinding
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter


class ChartFragment: Fragment() {

    private var _binding: FragmentChartBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ChartFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChartBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindChart()
    }

    private fun bindChart() {
        with(binding.chart) {
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(false)
            legend.isEnabled = false
            setViewPortOffsets(0f, 0f, 0f, 0f)
            setBackgroundColor(Color.BLACK)
            description.isEnabled = false

            xAxis.settingAxis()
            xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE

            axisLeft.settingAxis()
            axisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)

            setData()

            animateXY(500, 2000)

            invalidate()
        }
    }

    private fun AxisBase.settingAxis() {
        setLabelCount(6, false)
        textColor = Color.WHITE
        setDrawGridLines(false)
        axisLineColor = Color.WHITE
    }

    private fun setData() {
        val set = LineDataSet(getData(), "").apply {
            mode = LineDataSet.Mode.CUBIC_BEZIER
            cubicIntensity = 0.2f
            setDrawFilled(true)
            setDrawCircles(false)
            lineWidth = 1.8f
            circleRadius = 2f
            setCircleColor(Color.WHITE)
            highLightColor = Color.rgb(244, 117, 117)
            color = Color.WHITE
            fillColor = Color.WHITE
            fillAlpha = 100
            setDrawHorizontalHighlightIndicator(false)
            fillFormatter = IFillFormatter { dataSet, dataProvider ->
                binding.chart.axisLeft.axisMinimum
            }
        }

        val data = LineData(set)
        data.setValueTextSize(9f)
        data.setDrawValues(false)

        binding.chart.data = data
    }

    private fun getData() = args.points.map {
        Entry(it.x, it.y)
    }
}