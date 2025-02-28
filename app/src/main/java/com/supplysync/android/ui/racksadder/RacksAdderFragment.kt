package com.supplysync.android.ui.racksadder

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.supplysync.android.databinding.FragmentRacksAdderBinding
import com.supplysync.android.network.RetrofitInstance
import kotlinx.coroutines.launch

class RacksAdderFragment : Fragment() {
    private var _binding: FragmentRacksAdderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentRacksAdderBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addSectionButton.setOnClickListener {
            val section_identifier = binding.sectionEditText.text.toString()
            val size = binding.totalRacksEditText.text.toString()

            val sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
            val warehouse_id = sharedPreferences.getString("warehouse_id", "abc") ?: "abc"

            val newRack = RackAddRequest(warehouse_id, section_identifier, size)

            lifecycleScope.launch {
                try{
                    val response = RetrofitInstance.getApiService(requireContext()).addRack(newRack)
                    if (response.isSuccessful){
                        Toast.makeText(requireContext(), response.body()?.message, Toast.LENGTH_SHORT).show()
                    }
                    else {
                        val errorBody = response.errorBody()?.string()
                        Toast.makeText(requireContext(), "$errorBody", Toast.LENGTH_SHORT).show()
                    }
                }
                catch (e: Exception) {
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