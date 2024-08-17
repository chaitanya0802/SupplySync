//all call orders to serve

package com.supplysync.android.ui.orderCall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.supplysync.android.databinding.FragmentOrderCallBinding

class OrderCallFragment : Fragment() {
    private var _binding : FragmentOrderCallBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOrderCallBinding.inflate(inflater, container, false)
        val view =binding.root

        val rv = binding.rv1

        //todo complete

        binding.rv1.adapter = CallItemAdapter()

        val adapter = CallItemAdapter()
        rv.adapter = adapter
        //adapter.submitList(items)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}