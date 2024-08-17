package com.supplysync.android.ui.predictlp

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.supplysync.android.databinding.FragmentPredictLpBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//todo create prediction line graph of next 30 days
class PredictLPFragment : Fragment() {

    private var _binding: FragmentPredictLpBinding? = null
    private val binding get() = _binding!!
    val predictLViewModel: PredictLViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentPredictLpBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.predictButton.setOnClickListener {
            val location = binding.locationEditText.text.toString()
            val date = binding.dateEditText.text.toString()
            //todo : complete

        }

        predictLViewModel.prediction.observe(viewLifecycleOwner) { prediction ->
            """$prediction products""".also { binding.prediction.text = it }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}