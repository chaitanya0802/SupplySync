package com.supplysync.android.ui.signUp

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.supplysync.android.R
import com.supplysync.android.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sign-up button click handler
        binding.SignUpButton.setOnClickListener {
            val phoneno = binding.phonenoEditText.text.toString()
            val password1 = binding.password1EditText.text.toString()
            val password2 = binding.password2EditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val warehousename = binding.warehousenameEditText.text.toString()
            val warehouseid = binding.warehouseidEditText.text.toString()
            val location = binding.locationEditText.text.toString()
            val size = binding.warehousesizeEditText.text.toString()

            // Validate input details
            when {
                phoneno.isEmpty() -> {
                    showToast("Phone number cannot be empty.")
                }
                !Patterns.PHONE.matcher(phoneno).matches() || phoneno.length != 10 -> {
                    showToast("Enter a valid 10-digit phone number.")
                }
                email.isEmpty() -> {
                    showToast("Email cannot be empty.")
                }
                warehouseid.isEmpty() -> {
                    showToast("WarehouseID cannot be empty.")
                }
                password1.length < 6 -> {
                    showToast("Password must be at least 6 characters.")
                }
                password1 != password2 -> {
                    showToast("Passwords do not match.")
                    binding.password1EditText.text?.clear()
                    binding.password2EditText.text?.clear()
                }
                warehousename.isEmpty() -> {
                    showToast("warehousename cannot be empty.")
                }
                location.isEmpty() -> {
                    showToast("Location cannot be empty.")
                }
                size.isEmpty() -> {
                    showToast("Size cannot be empty.")
                }
                else -> {
                    val newSignUp = SignUpRequest("Manager", phoneno, password1, email, warehousename,
                        warehouseid, size, location)
                    signUpViewModel.signUpUser(newSignUp)
                }
            }
        }

        // Observe sign-up result
        signUpViewModel.signUpResult.observe(viewLifecycleOwner) { signUpStatus ->
            if (signUpStatus == "success") {
                showToast("Successfully Registered")
                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
            }
            else {
                showToast(signUpStatus)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
