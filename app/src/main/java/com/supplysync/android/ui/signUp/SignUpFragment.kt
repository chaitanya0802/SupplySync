package com.supplysync.android.ui.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.supplysync.android.R
import com.supplysync.android.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private var _binding : FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val signUpViewModel: SignUpViewModel by viewModels()
    private var verified: Boolean = false
    val Userrole: String? = null
    var roles = arrayOf<String?>("Company", "DistributionCentre",
        "RetailStore", "LogisticsProvider")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.verifyOTPButton.setOnClickListener {
            //TODO : to be implemented
            verified = true
        }

        //role spinner
        val rolesSpinner = binding.roleSpinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, roles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        rolesSpinner.adapter = adapter

        //on click SignUp button
        binding.SignUpButton.setOnClickListener {
            val phoneno = binding.phonenoEditText.text.toString()
            val password1 = binding.password1EditText.text.toString()
            val password2 = binding.password2EditText.text.toString()
            val email = binding.emailEditText.text.toString()

            val selectedRole = rolesSpinner.selectedItem as String

            //validate the entered details
            when {
                phoneno.isEmpty() -> Toast.makeText(
                    context,
                    "Phone number cannot be empty.",
                    Toast.LENGTH_SHORT)
                    .show()

                !verified -> Toast.makeText(
                    context,
                    "Phone no. not verified",
                    Toast.LENGTH_SHORT)
                    .show()

                password1.length < 8 -> Toast.makeText(
                    context,
                    "Password must be longer than 8 characters.",
                    Toast.LENGTH_SHORT)
                    .show()

                password1 != password2 -> {
                    Toast.makeText(
                        context,
                        "Passwords do not match.",
                        Toast.LENGTH_SHORT)
                        .show()
                    binding.password1EditText.text?.clear()
                    binding.password2EditText.text?.clear()
                }

                email.isEmpty() -> Toast.makeText(
                    context, "Email cannot be empty.",
                    Toast.LENGTH_SHORT)
                    .show()

                else -> {
                    val newSignUp = SignUpRequest(phoneno, password1, email, role = selectedRole)
                    signUpViewModel.signUpUser(newSignUp)
                }
            }


        }

        signUpViewModel.signUpResult.observe(viewLifecycleOwner) { signUpStatus ->
            if (signUpStatus == "success") {
                Toast.makeText(context, "Successfully Registered", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
            }
            else {
                Toast.makeText(context, signUpStatus, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}