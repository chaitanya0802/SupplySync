package com.supplysync.android.ui.product

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.supplysync.android.databinding.FragmentProductBinding
import com.supplysync.android.network.RetrofitInstance
import kotlinx.coroutines.launch


class Product : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addProductButton.setOnClickListener {
            // Safe conversion of inputs
            val rackid = binding.rackIdEditText.text.toString().toIntOrNull() ?: 0
            val productname = binding.productNameEditText.text?.toString()?.trim() ?: ""
            val suppliername = binding.supplierNameEditText.text?.toString()?.trim() ?: ""
            val quantity = binding.quantityEditText.text.toString().toIntOrNull() ?: 0
            val category = binding.categoryEditText.text?.toString()?.trim() ?: ""
            val lotprice = binding.priceEditText.text.toString().toFloatOrNull() ?: 0.0f
            val lotspace = binding.lotSpaceEditText.text.toString().toFloatOrNull() ?: 0.0f

            // Validation to ensure required fields are not empty
            if (productname.isEmpty() || suppliername.isEmpty() || category.isEmpty()) {
                Toast.makeText(context, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
            val warehouse_id = sharedPreferences.getString("warehouse_id", "abc") ?: "abc"

            val productlot = AddProductLotRequest(warehouse_id,
                rackid, productname, suppliername, quantity, category, lotprice, lotspace
            )


            lifecycleScope.launch {
                try {
                    val response = RetrofitInstance.getApiService(requireContext()).addProductLot(productlot)
                    if (response.isSuccessful) {
                        Toast.makeText(context, response.body()?.message ?: "Product Added", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(context, "Failed to add product", Toast.LENGTH_SHORT).show()
                    }
                }
                catch (e: Exception) {
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}