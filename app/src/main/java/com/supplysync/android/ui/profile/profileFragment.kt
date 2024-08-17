package com.supplysync.android.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.supplysync.android.R
import com.supplysync.android.databinding.FragmentProfileBinding

//todo enhance
class profileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addSectionButton.setOnClickListener {
            findNavController().navigate(R.id.action_userProfileFragment_to_sectionAdderFragment)
        }

        binding.addRackButton.setOnClickListener {
            findNavController().navigate(R.id.action_userProfileFragment_to_racksAdderFragment2)
        }
        binding.updateRackButton.setOnClickListener {
            findNavController().navigate(R.id.action_userProfileFragment_to_rackUpdaterFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}