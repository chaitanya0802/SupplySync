//to manage the inventory

package com.supplysync.android.ui.home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.supplysync.android.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {_binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        val warehouse_id = sharedPreferences.getString("warehouse_id", "abc") ?: "abc"

        //Warehouse
        homeViewModel.getWarehouseData(warehouse_id)
        homeViewModel.warehousename.observe(viewLifecycleOwner) { name ->
            binding.warehousenametv.text = name
        }
        homeViewModel.percentagefilled.observe(viewLifecycleOwner) { pf ->
            updateWarehouseChart(pf)
        }


        //section
        homeViewModel.getSectionData(warehouse_id)
        homeViewModel.total_empty_sections.observe(viewLifecycleOwner) { value ->
            "Total Empty Sections: $value".also { binding.totalemptysectionsTv.text = it }
        }

        homeViewModel.total_filled_sections.observe(viewLifecycleOwner) { value ->
            "Total Filled Sections: $value".also { binding.totalfilledsectionsTv.text = it }
        }
        homeViewModel.percent_section_filled.observe(viewLifecycleOwner) { value ->
            updateSectionChart(value)
        }

        //bar graph
        homeViewModel.sectionBarGraph.observe(viewLifecycleOwner) { data ->
            attachSectionBarGraphData(data)
        }

        //total
        homeViewModel.total_sections.observe(viewLifecycleOwner) { data ->
            data.toString().also { binding.totalsections.text = it }
        }
        homeViewModel.total_racks.observe(viewLifecycleOwner) { data ->
            data.toString().also { binding.totalracks.text = it }
        }


    }

    private fun updateWarehouseChart(percentagefilled: Float) {
        val emptyPercentage = 100 - percentagefilled
        val entries = listOf(
            PieEntry(percentagefilled, "Filled"),
            PieEntry(emptyPercentage, "Empty")
        )

        // Configure the dataset
        val dataSet = PieDataSet(entries, "Warehouse Status").apply {
            colors = listOf(Color.rgb(245, 173, 115), Color.rgb(211, 230, 209))
            valueTextColor = Color.BLACK
            valueTextSize = 15f
        }

        // Set the data
        binding.warehousePc.data = PieData(dataSet)

        // Customize the chart
        binding.warehousePc.description.isEnabled = false
        binding.warehousePc.isDrawHoleEnabled = true
        binding.warehousePc.holeRadius = 50f
        binding.warehousePc.setUsePercentValues(true)
        binding.warehousePc.centerText = "${percentagefilled.toInt()}% Filled"
        binding.warehousePc.setCenterTextSize(18f)

        // Refresh the chart
        binding.warehousePc.invalidate()
    }

    //section
    private fun updateSectionChart(percentagefilled: Float) {
        val emptyPercentage = 100 - percentagefilled
        val entries = listOf(
            PieEntry(percentagefilled, "Filled"),
            PieEntry(emptyPercentage, "Empty")
        )

        // Configure the dataset
        val dataSet = PieDataSet(entries, "Section Status").apply {
            colors = listOf(Color.rgb(189, 154, 245), Color.rgb(211, 230, 209))
            valueTextColor = Color.BLACK
            valueTextSize = 15f
        }

        // Set the data
        binding.sectionPc.data = PieData(dataSet)

        // Customize the chart
        binding.sectionPc.description.isEnabled = false
        binding.sectionPc.isDrawHoleEnabled = true
        binding.sectionPc.holeRadius = 50f
        binding.sectionPc.setUsePercentValues(true)
        binding.sectionPc.centerText = "${percentagefilled.toInt()}% Filled"
        binding.sectionPc.setCenterTextSize(18f)

        // Refresh the chart
        binding.sectionPc.invalidate()
    }

    fun attachSectionBarGraphData(jsonResponse: List<FilledsizeSectionidResponse>) {
        val sectionBarChart = binding.sectionBarChart

        val entries = mutableListOf<BarEntry>()
        val labels = mutableListOf<String>()

        for (item in jsonResponse) {
            val sectionId = item.section_id.toFloat()
            val sizeFilled = item.size_filled

            entries.add(BarEntry(sectionId, sizeFilled))
            labels.add("Sec $sectionId")
        }

        val dataSet = BarDataSet(entries, "Size Filled")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        dataSet.valueTextSize = 12f

        val barData = BarData(dataSet)
        barData.barWidth = 0.3f // Reduce bar width for better spacing
        sectionBarChart.data = barData
        sectionBarChart.setFitBars(true)
        sectionBarChart.invalidate()

        // Enable scrolling
        sectionBarChart.setScaleEnabled(false)
        sectionBarChart.setPinchZoom(false)
        sectionBarChart.isDragEnabled = true
        sectionBarChart.setVisibleXRangeMaximum(4f) // Show 4 bars at a time

        // Customize X-Axis
        val xAxis = sectionBarChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)

        // Customize Y-Axis
        sectionBarChart.axisLeft.axisMinimum = 0f
        sectionBarChart.axisRight.isEnabled = false

        // Remove description
        sectionBarChart.description.isEnabled = false
    }
}