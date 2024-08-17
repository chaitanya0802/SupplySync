//to predict the inventory for future

package com.supplysync.android.ui.predict

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.supplysync.android.databinding.FragmentPredictBinding

//todo create prediction line graph of next 30 days
class PredictFragment : Fragment() {
    private var _binding: FragmentPredictBinding? = null
    private val binding get() = _binding!!
    val predictViewModel: PredictViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentPredictBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.predictButton.setOnClickListener {
            val productCode = binding.pcodeEditText.text.toString()
            val date = binding.dateEditText.text.toString()
            predictViewModel.getPred(PredictionRequest(productCode, date))
        }

        predictViewModel.prediction.observe(viewLifecycleOwner) { prediction ->
            """$prediction products""".also { binding.prediction.text = it }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

