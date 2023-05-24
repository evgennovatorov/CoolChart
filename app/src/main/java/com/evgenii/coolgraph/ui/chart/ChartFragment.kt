package com.evgenii.coolgraph.ui.chart

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evgenii.coolgraph.R
import com.evgenii.coolgraph.common.DeviceConfigurationProvider
import com.evgenii.coolgraph.databinding.FragmentChartBinding
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

private const val FILENAME = "CoolChart"
class ChartFragment: Fragment() {

    private val deviceConfigurationProvider: DeviceConfigurationProvider by inject()

    private var _binding: FragmentChartBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ChartFragmentArgs>()

    private val chartMenuProvider = ChartMenuProvider()

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                saveChart()
            } else {
                context?.let {
                    showToast(R.string.write_storage_fail)
                }
            }
    }

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
        setupMenu()
        bindRecyclerView()
    }

    private fun bindRecyclerView() {
        binding.pointView.setText(R.string.x_label, R.string.y_label)
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(
                requireContext(),
                deviceConfigurationProvider.getRecyclerViewOrientation(),
                false
            )
            adapter = PointsAdapter(args.points)
            addItemDecoration(
                DividerItemDecoration(requireContext(),
                    deviceConfigurationProvider.getRecyclerViewOrientation())
            )
        }
    }

    private fun DeviceConfigurationProvider.getRecyclerViewOrientation() =
        if (isLandscape()) {
            RecyclerView.VERTICAL
        } else {
            RecyclerView.HORIZONTAL
        }

    private fun setupMenu() {
        (requireActivity() as? MenuHost)?.addMenuProvider(chartMenuProvider)
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
            color = Color.WHITE
            fillColor = Color.WHITE
            fillAlpha = 100
            setDrawHorizontalHighlightIndicator(false)
            fillFormatter = IFillFormatter { _, _ ->
                binding.chart.axisLeft.axisMinimum
            }
        }

        binding.chart.data = LineData(set).apply {
            setDrawValues(false)
        }
    }

    private fun getData() = args.points.map {
        Entry(it.x, it.y)
    }

    override fun onDestroyView() {
        (requireActivity() as? MenuHost)?.removeMenuProvider(chartMenuProvider)
        resultLauncher.unregister()
        _binding = null
        super.onDestroyView()
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            Snackbar.make(
                binding.chart,
                getString(R.string.write_storage_permission_title),
                Snackbar.LENGTH_INDEFINITE
            ).setAction(android.R.string.ok) {
                resultLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }.show()
        } else {
            showToast(R.string.permission_required)
            resultLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    private fun saveChart() {
        val message =
            if (binding.chart.saveToGallery(FILENAME + System.currentTimeMillis(), 50)) {
                R.string.success_chart_saving
            } else {
                R.string.write_storage_fail
            }
        showToast(message)
    }

    private fun showToast(message: Int) {
        context?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }

    private inner class ChartMenuProvider: MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.chart_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            if (menuItem.itemId == R.id.save_to_gallery) {
                if (ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED) {
                    saveChart()
                } else {
                    requestPermission()
                }
                return true
            }
            return false
        }
    }
}