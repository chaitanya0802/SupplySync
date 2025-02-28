//to predict the inventory for future

package com.supplysync.android.ui.predict

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.supplysync.android.R
import com.supplysync.android.databinding.FragmentPredictBinding
import com.supplysync.android.network.RetrofitInstance
import kotlinx.coroutines.launch


class PredictFragment : Fragment() {
    private var _binding: FragmentPredictBinding? = null
    private val binding get() = _binding!!

    private val predictions = MutableLiveData<LinkedHashMap<String, PredictionDetails>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPredictBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fetch predictions
        binding.predictButton.setOnClickListener {
            binding.ppb.visibility = View.VISIBLE
            // Get the input values from TextViews
            val date = binding.dateEditText.text.toString()
            val productIdText = binding.pcodeEditText.text.toString()

            // Call getPred with the entered values
            getPred(date, productIdText.toInt())
        }

        // Observe predictions LiveData
        predictions.observe(viewLifecycleOwner) { predictionData ->
            binding.cardContainer.removeAllViews()

            predictionData.forEach { (date, details) ->
                val cardView = layoutInflater.inflate(R.layout.card_item, binding.cardContainer, false)

                cardView.findViewById<TextView>(R.id.date).text = "Date: $date"
                "Order Quantity:  ${details.predicted_order_quantity}".also { cardView.findViewById<TextView>(R.id.order).text = it }
                "Warehouse Space (sqft):  ${details.estimated_warehouse_space_sqft}".also { cardView.findViewById<TextView>(R.id.Space).text = it }

                binding.cardContainer.addView(cardView)
            }
        }
    }

    private fun getPred(date: String, productId: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val context = requireContext()
                val response = RetrofitInstance.getApiService(context).getPrediction(date, productId)

                if (response.isSuccessful && response.body() != null) {
                    binding.ppb.visibility = View.GONE

                    val responseBody = response.body()!!
                    val parsedPredictions = LinkedHashMap<String, PredictionDetails>()

                    responseBody.predictions.forEach { predictionItem ->
                        parsedPredictions[predictionItem.date] = PredictionDetails(
                            predicted_order_quantity = predictionItem.predicted_order_quantity,
                            estimated_warehouse_space_sqft = predictionItem.estimated_warehouse_space_sqft
                        )
                    }

                    predictions.postValue(parsedPredictions)
                }
            }
            catch (e: Exception) {
                e.printStackTrace()

                // Handle error by posting an error response
                val errorMap = LinkedHashMap<String, PredictionDetails>().apply {
                    put(
                        "Error",
                        PredictionDetails(
                            predicted_order_quantity = -1f,
                            estimated_warehouse_space_sqft = -1f
                        )
                    )
                }
                predictions.postValue(errorMap)
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


