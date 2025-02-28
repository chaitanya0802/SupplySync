package com.supplysync.android.ui.rackupdater

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.supplysync.android.databinding.FragmentRackUpdaterBinding
import com.supplysync.android.network.RetrofitInstance
import com.supplysync.android.ui.racksadder.RackAddRequest
import com.supplysync.android.ui.signUp.SignUpViewModel
import kotlinx.coroutines.launch

class RackUpdaterFragment : Fragment() {
    private var _binding: FragmentRackUpdaterBinding? = null
    private val binding get() = _binding!!
    private val RacksAdderViewModel: RackUpdaterViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentRackUpdaterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addSectionButton.setOnClickListener {
            val section_identifier = binding.sectionEditText.text.toString()
            val size = binding.totalRacksEditText.toString()

            val sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
            val warehouse_id = sharedPreferences.getString("warehouse_id", "abc") ?: "abc"

            val newRack = RackAddRequest(warehouse_id, section_identifier, size)

            lifecycleScope.launch {
                val response = RetrofitInstance.getApiService(requireContext()).addRack(newRack)
                Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}