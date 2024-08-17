package com.supplysync.android.ui.login

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.supplysync.android.MainActivity
import com.supplysync.android.R
import com.supplysync.android.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()
    var roles = arrayOf<String?>("Company", "DistributionCentre",
        "RetailStore", "LogisticsProvider")
    private lateinit var sharedPreferences: SharedPreferences
    private val PREF_NAME = "UserPreferences"
    private val ROLE_KEY = "UserRole"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)


        //hide toolbar and bottom nav bar
        (activity as MainActivity).hideBars()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val rolesSpinner = binding.roleSpinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, roles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        rolesSpinner.adapter = adapter

        //Login Button Click
        binding.loginButton.setOnClickListener {
            val phoneno = binding.phonenoEditText.text.toString()   //username a backend
            val password = binding.passwordEditText.text.toString()

            val selectedRole = rolesSpinner.selectedItem as String

            val editor = sharedPreferences.edit()
            editor.putString(ROLE_KEY, selectedRole)
            editor.apply()

            if (phoneno.isNotEmpty() and password.isNotEmpty()) {
                loginViewModel.login(LoginRequest(phoneno, password))
            } else {
                Toast.makeText(context, "Please Enter Phoneno. and Password", Toast.LENGTH_LONG)
                    .show()
            }
        }

        //Forgot Password? click
        binding.ForgotPasswordTV.setOnClickListener {
            //TODO: complete
            Toast.makeText(context, "service not available...", Toast.LENGTH_LONG).show()
        }

        //SignUp Button Click
        binding.SignUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        //navigate to Home Fragment after Login
        loginViewModel.loginResult.observe(viewLifecycleOwner) { loginResult ->
            if (loginResult == "success") {
                Toast.makeText(context, "Successfully Logged In.", Toast.LENGTH_SHORT).show()
                (activity as MainActivity).showBars()
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                Toast.makeText(context, loginResult, Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
