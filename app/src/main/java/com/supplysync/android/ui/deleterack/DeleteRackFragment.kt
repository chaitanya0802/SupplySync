package com.supplysync.android.ui.deleterack

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.supplysync.android.databinding.FragmentDeleteRackBinding
import com.supplysync.android.network.RetrofitInstance
import kotlinx.coroutines.launch

class DeleteRackFragment : Fragment() {
    private var _binding: FragmentDeleteRackBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: DeleteRackAdapter
    private val rackList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDeleteRackBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DeleteRackAdapter(rackList) { rackId, position ->
            deleteRack(rackId, position)
        }

        binding.rv1.layoutManager = LinearLayoutManager(requireContext())
        binding.rv1.adapter = adapter

        fetchRacks()
    }

    private fun fetchRacks() {
        val sharedPreferences = requireContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        val warehouseId = sharedPreferences.getString("warehouse_id", null)

        if (warehouseId == null) {
            Toast.makeText(requireContext(), "Warehouse ID not found", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val racks = RetrofitInstance.getApiService(requireContext()).getRacks(warehouseId)
                rackList.clear()
                rackList.addAll(racks)
                adapter.notifyDataSetChanged()

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Failed to load racks", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun deleteRack(rackId: String, position: Int) {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.getApiService(requireContext()).deleteRack(rackId)
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