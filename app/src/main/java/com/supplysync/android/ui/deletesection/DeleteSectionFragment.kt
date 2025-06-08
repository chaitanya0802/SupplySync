package com.supplysync.android.ui.deletesection

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.supplysync.android.databinding.FragmentDeleteSectionBinding
import com.supplysync.android.network.RetrofitInstance
import kotlinx.coroutines.launch

class DeleteSectionFragment : Fragment() {
    private var _binding: FragmentDeleteSectionBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: DeleteSectionAdapter
    private val sectionList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDeleteSectionBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DeleteSectionAdapter(sectionList) { sectionId, position ->
            deleteSection(sectionId, position)
        }

        binding.rv1.layoutManager = LinearLayoutManager(requireContext())
        binding.rv1.adapter = adapter

        fetchSections()
    }

    private fun fetchSections() {
        val sharedPreferences = requireContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        val warehouseId = sharedPreferences.getString("warehouse_id", null)

        if (warehouseId == null) {
            Toast.makeText(requireContext(), "Warehouse ID not found", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val sections = RetrofitInstance.getApiService(requireContext()).getSections(warehouseId)
                sectionList.clear()
                sectionList.addAll(sections)
                adapter.notifyDataSetChanged()

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Failed to load sections", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun deleteSection(sectionId: String, position: Int) {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.getApiService(requireContext()).deleteSection(sectionId)
                if (response.isSuccessful) {
                    adapter.removeItemAt(position)
                    Toast.makeText(requireContext(), "Deleted successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to delete", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}