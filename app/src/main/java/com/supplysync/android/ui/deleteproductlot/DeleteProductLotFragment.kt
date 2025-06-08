
package com.supplysync.android.ui.deleteproductlot

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.supplysync.android.R
import com.supplysync.android.databinding.FragmentDeleteProductLotBinding
import com.supplysync.android.network.RetrofitInstance
import com.supplysync.android.ui.deleteproductlot.DeleteProductLotAdapter
import kotlinx.coroutines.launch


class DeleteProductLotFragment : Fragment() {
    private var _binding: FragmentDeleteProductLotBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: DeleteProductLotAdapter
    private val productlotList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDeleteProductLotBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DeleteProductLotAdapter(productlotList) { productlotId, position ->
            deleteProductLot(productlotId, position)
        }

        binding.rv1.layoutManager = LinearLayoutManager(requireContext())
        binding.rv1.adapter = adapter

        fetchProductLots()
    }

    private fun fetchProductLots() {
        val sharedPreferences = requireContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        val warehouseId = sharedPreferences.getString("warehouse_id", null)

        if (warehouseId == null) {
            Toast.makeText(requireContext(), "Warehouse ID not found", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val productlots = RetrofitInstance.getApiService(requireContext()).getProductLots(warehouseId)
                productlotList.clear()
                productlotList.addAll(productlots)
                adapter.notifyDataSetChanged()

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Failed to load productlots", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun deleteProductLot(productlotId: String, position: Int) {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.getApiService(requireContext()).deleteProductLot(productlotId)
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