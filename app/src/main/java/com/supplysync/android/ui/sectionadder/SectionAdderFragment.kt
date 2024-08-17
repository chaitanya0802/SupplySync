package com.supplysync.android.ui.sectionadder

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.supplysync.android.databinding.FragmentSectionAdderBinding
import com.supplysync.android.network.RetrofitInstance
import com.supplysync.android.ui.signUp.SignUpViewModel
import kotlinx.coroutines.launch

class SectionAdderFragment : Fragment() {
    private var _binding: FragmentSectionAdderBinding? = null
    private val binding get() = _binding!!
    private val sectionAdderViewModel: SignUpViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentSectionAdderBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addSectionButton.setOnClickListener {
            val section_identifier = binding.sectionIdentifierEditText.text.toString()
            val description = binding.descriptionEditText.text.toString()
            val total_racks = binding.totalRacksEditText.text.toString()

            val newSection = SectionAddRequest(section_identifier,description, total_racks)
            lifecycleScope.launch {
                val response = RetrofitInstance.getApiService(requireContext()).addSection(newSection)
                Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}