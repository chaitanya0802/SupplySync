package com.supplysync.android.ui.sectionadder

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.supplysync.android.databinding.FragmentSectionAdderBinding
import com.supplysync.android.network.RetrofitInstance
import kotlinx.coroutines.launch

class SectionAdderFragment : Fragment() {
    private var _binding: FragmentSectionAdderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentSectionAdderBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addSectionButton.setOnClickListener {
            val size = binding.sizeEditText.text.toString()

            val sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
            val warehouse_id = sharedPreferences.getString("warehouse_id", "abc") ?: "abc"

            val newSection = SectionAddRequest(warehouse_id, size)

            lifecycleScope.launch {
                try {
                    val response = RetrofitInstance.getApiService(requireContext()).addSection(newSection)

                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), response.body()?.message, Toast.LENGTH_SHORT).show()
                    } else {
                        // Extract error message properly
                        val errorBody = response.errorBody()?.string()
                        Toast.makeText(requireContext(), "Error: $errorBody", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Request failed: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}