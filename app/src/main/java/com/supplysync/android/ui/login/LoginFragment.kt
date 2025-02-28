package com.supplysync.android.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.supplysync.android.MainActivity
import com.supplysync.android.R
import com.supplysync.android.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private val PREF_NAME = "UserPreferences"
    private val ROLE_KEY = "UserRole"
    private val WAREHOUSE_ID_KEY = "warehouse_id"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        // Hide toolbar and bottom nav bar
        (activity as MainActivity).hideBars()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rolesSpinner = binding.roleSpinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrayOf("Manager", "Subordinate"))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        rolesSpinner.adapter = adapter

        // Login Button Click
        binding.loginButton.setOnClickListener {
            val phoneno = binding.phonenoEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val warehouse_id = binding.warehouseidEditText.text.toString()
            val selectedRole = rolesSpinner.selectedItem as String

            //add shares preferences
            val editor = sharedPreferences.edit()
            editor.putString(ROLE_KEY, selectedRole)
            editor.putString(WAREHOUSE_ID_KEY, warehouse_id)
            editor.apply()

            when {
                phoneno.isEmpty() -> {
                    Toast.makeText(context, "Phone number cannot be empty.", Toast.LENGTH_LONG).show()
                }
                !Patterns.PHONE.matcher(phoneno).matches() || phoneno.length != 10 -> {
                    Toast.makeText(context, "Enter a valid 10-digit phone number.", Toast.LENGTH_LONG).show()
                }
                password.length < 6 -> {
                    Toast.makeText(context, "Password must be at least 6 characters.", Toast.LENGTH_LONG).show()
                }
                warehouse_id.isEmpty() -> {
                    Toast.makeText(context, "Warehouse ID cannot be empty.", Toast.LENGTH_LONG).show()
                }
                else -> {
                    // Proceed with login
                    loginViewModel.login(LoginRequest(phoneno, password))
                }
            }
        }

        // Observe login result
        loginViewModel.loginResult.observe(viewLifecycleOwner) { loginResult ->
            val userRole = sharedPreferences.getString(ROLE_KEY, "Manager")

            if (loginResult == "success") {
                Toast.makeText(context, "Successfully Logged In as $userRole.", Toast.LENGTH_SHORT).show()

                // Update bottom navigation menu dynamically
                (activity as MainActivity).updateBottomNavMenu()
                (activity as MainActivity).showBars()

                // Navigate based on role
                if (userRole == "Manager") {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    findNavController().navigate(R.id.action_loginFragment_to_userProfileFragment)
                }
            } else {
                Toast.makeText(context, loginResult, Toast.LENGTH_SHORT).show()
            }
        }

        binding.SubordinateSignUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_subordinateSignUpFragment)
        }

        binding.SignUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
