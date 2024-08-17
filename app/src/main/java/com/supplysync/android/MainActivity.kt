package com.supplysync.android

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.DrawableCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.supplysync.android.databinding.ActivityMainBinding
import com.supplysync.android.ui.login.TokenManager
import kotlinx.coroutines.Job

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private var searchJob: Job?= null
    private lateinit var sharedPreferences: SharedPreferences
    private val PREF_NAME = "UserPreferences"
    private val ROLE_KEY = "UserRole"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //set Action Bar
        toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        //to Configure backbutton
        val builder1= AppBarConfiguration.Builder(navController.graph)
        val appbarconfiguration1=builder1.build()
        //setup/link navigation controller and backbutton configuration
        toolbar.setupWithNavController(navController, appbarconfiguration1)


        //get token for authentication
        val token = TokenManager.getToken(this)
        if (token != null) {
            navController.navigate(R.id.homeFragment)
        }
        else {
            navController.navigate(R.id.loginFragment, null, navOptions {
                popUpTo(R.id.nav_graph) {
                    inclusive = true
                }
            })
        }

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val userRole = sharedPreferences.getString(ROLE_KEY, "LogisticsProvider") // Default to "RetailStore" if not found

        //set Bottom Nav Bar
        val bottomNavBar = binding.bottomNavBar

        when (userRole) {
            "RetailStore" -> bottomNavBar.inflateMenu(R.menu.menu_bottom_c_d)
            "Company" -> bottomNavBar.inflateMenu(R.menu.menu_bottom_c_d)
            "DistributionCentre" -> bottomNavBar.inflateMenu(R.menu.menu_bottom_c_d)
            "LogisticsProvider" -> bottomNavBar.inflateMenu(R.menu.menu_bottom_lp)
        }
        bottomNavBar.setupWithNavController(navController)

    }

    //hide and show bottom nav bar and toolbar for login purpose
    fun hideBars(){
        binding.toolbar.visibility= View.GONE
        binding.bottomNavBar.visibility= View.GONE
    }
    fun showBars(){
        binding.toolbar.visibility= View.VISIBLE
        binding.bottomNavBar.visibility= View.VISIBLE
    }

    //inflate toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)

        // setting menu icon color with respect to mode
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val isNightMode = currentNightMode == Configuration.UI_MODE_NIGHT_YES
        menu?.let {
            for (i in 0 until it.size()) {
                val menuItem: MenuItem = it.getItem(i)
                menuItem.icon?.let { icon ->
                    val color = if (isNightMode) {
                        getColor(R.color.white) // White for night mode
                    } else {
                        getColor(R.color.black) // Black for day mode
                    }
                    DrawableCompat.setTint(DrawableCompat.wrap(icon), color)
                }
            }
        }
        return true
    }

    //on click toolbar items
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_logout -> {
                logout()
                true
            }
            else -> {
                item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
            }
        }
    }

    private fun logout() {
        // Clear the token
        TokenManager.clearToken(this)

        // Navigate to login screen
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}

