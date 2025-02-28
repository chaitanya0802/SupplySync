package com.supplysync.android

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.supplysync.android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar
    private lateinit var navController: NavController
    private lateinit var sharedPreferences: SharedPreferences
    private val PREF_NAME = "UserPreferences"
    private val ROLE_KEY = "UserRole"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Action Bar
        toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        // Set up Navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Set top-level destinations to remove back button
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,          // Manager Home
                R.id.userProfileFragment,   // Subordinate Profile
                R.id.loginFragment          // Login Screen
            )
        )

        toolbar.setupWithNavController(navController, appBarConfiguration)

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val userRole = sharedPreferences.getString(ROLE_KEY, "Manager") // Default to Manager if no role saved

        // Token-based Navigation & Role-based Initial Fragment
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", null)

        if (token != null) {
            if (userRole == "Manager") {
                navController.navigate(R.id.homeFragment)
            } else {
                navController.navigate(R.id.userProfileFragment)
            }
        } else {
            navController.navigate(R.id.loginFragment, null, navOptions {
                popUpTo(R.id.nav_graph) { inclusive = true }
            })
        }


        // Set Bottom Navigation Menu
        updateBottomNavMenu()
    }

    override fun onResume() {
        super.onResume()
        updateBottomNavMenu()  // Ensure menu is updated dynamically
    }

    fun updateBottomNavMenu() {
        val userRole = sharedPreferences.getString(ROLE_KEY, "Manager")
        val bottomNavBar = binding.bottomNavBar

        // Clear existing menu to prevent duplicates
        bottomNavBar.menu.clear()

        // Inflate the correct menu based on user role
        when (userRole) {
            "Manager" -> bottomNavBar.inflateMenu(R.menu.menu_bottom_manager)
            "Subordinate" -> bottomNavBar.inflateMenu(R.menu.menu_bottom_subordinate)
        }

        bottomNavBar.setupWithNavController(navController)
    }

    // Hide and show bars for login screen
    fun hideBars() {
        binding.toolbar.visibility = View.GONE
        binding.bottomNavBar.visibility = View.GONE
    }

    fun showBars() {
        binding.toolbar.visibility = View.VISIBLE
        binding.bottomNavBar.visibility = View.VISIBLE
    }

    // Inflate toolbar menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    // Handle toolbar item clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                logout()
                true
            }
            else -> item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        // Clear shared preferences
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        // Navigate back to the login screen
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()

        // Show logout success message
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_LONG).show()
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}





