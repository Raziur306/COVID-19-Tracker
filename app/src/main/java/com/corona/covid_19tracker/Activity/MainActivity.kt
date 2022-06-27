package com.corona.covid_19tracker.Activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import com.corona.covid_19tracker.Activity.Fragment.*
import com.corona.covid_19tracker.Encryption.Encrypter
import com.corona.covid_19tracker.R
import com.corona.covid_19tracker.Units.Unit
import com.corona.covid_19tracker.databinding.ActivityMainBinding
import com.corona.covid_19tracker.utils.AdsTask
import com.corona.covid_19tracker.utils.NetworkUtils
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var toggle: ActionBarDrawerToggle? = null
    private var transaction: FragmentTransaction? = null
    private var themeSwitchCompat: SwitchCompat? = null
    private var locationSwitchCompat: SwitchCompat? = null
    private var fragmentIndex = 0
    private val networkUtils = NetworkUtils
    private val handler: Handler = Handler(Looper.myLooper()!!)
    private val allFragment = arrayOf(
        HomeActivity(),
        HealthCareActivity(),
        PreventionActivity(),
        SymptomActivity(),
        BDdataActivity(),
        WebFragment(),
        // P_QuestionActivity(),
        AboutActivity()
    )

    //saving current Fragment activity
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("current_fragment", fragmentIndex)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if ((application as Encrypter).getMode() == true) {
            setTheme(R.style.Theme_Dark)
        } else {
            setTheme(R.style.Theme_Light)
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) window.statusBarColor =
            ContextCompat.getColor(this, R.color.black)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adsTask = AdsTask(this)
        binding.toolbar.title = ""
        setSupportActionBar(binding.toolbar)
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle!!)
        toggle!!.syncState()
        if (savedInstanceState != null) {
            fragmentIndex = savedInstanceState.getInt("current_fragment", 0)
        }
        transaction = supportFragmentManager.beginTransaction()
        transaction!!.replace(R.id.fragmentViewer, allFragment[fragmentIndex]).commit()
        binding.navMenu.menu.getItem(0).isChecked = true
        binding.navMenu.setNavigationItemSelectedListener { item ->
            transaction = supportFragmentManager.beginTransaction()
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            Handler(Looper.getMainLooper()).postDelayed({
                when (item.itemId) {
                    R.id.nav_home -> {
                        adsTask.showInterstitialAds()
                        fragmentIndex = 0
                        transaction!!.replace(R.id.fragmentViewer, allFragment[fragmentIndex])
                            .commit()
                    }
                    R.id.nav_health_status -> {
                        adsTask.showInterstitialAds()
                        fragmentIndex = 1
                        transaction!!.replace(R.id.fragmentViewer, allFragment[fragmentIndex])
                            .commit()
                    }
                    R.id.nav_prevention -> {
                        adsTask.showInterstitialAds()
                        fragmentIndex = 2
                        transaction!!.replace(R.id.fragmentViewer, allFragment[fragmentIndex])
                            .commit()
                    }
                    R.id.nav_symptoms -> {
                        adsTask.showInterstitialAds()
                        fragmentIndex = 3
                        transaction!!.replace(R.id.fragmentViewer, allFragment[fragmentIndex])
                            .commit()
                    }
                    R.id.nav_state_data -> {
                        adsTask.showInterstitialAds()
                        fragmentIndex = 4
                        transaction!!.replace(R.id.fragmentViewer, allFragment[fragmentIndex])
                            .commit()
                    }
//                    R.id.nav_popular_question -> {
//                        fragmentIndex = 5
//                        transaction!!.replace(R.id.fragmentViewer, allFragment[fragmentIndex])
//                            .commit()
                    //       }
                    R.id.nav_policy -> {
                        adsTask.showInterstitialAds()
//                        val intent = Intent(this, WebViewActivity::class.java)
//                        intent.putExtra("url", Unit.PRIVACY_POLICY)
//                        startActivity(intent)
                        fragmentIndex = 5
                        transaction!!.replace(R.id.fragmentViewer, allFragment[fragmentIndex])
                            .commit()
                    }

                    R.id.nav_about -> {
                        adsTask.showInterstitialAds()
                        fragmentIndex = 6
                        transaction!!.replace(R.id.fragmentViewer, allFragment[fragmentIndex])
                            .commit()
                    }

                }
            }, 350)
            true
        }
        binding.navMenu.getHeaderView(0).findViewById<ImageView>(R.id.drawer_nav_closer)
            .setOnClickListener {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
//settings
        //dark_mode
        themeSwitchCompat =
            binding.navMenu.menu.findItem(R.id.dark_menu).actionView?.findViewById<View>(R.id.nav_switch_item) as SwitchCompat
        if ((application as Encrypter).getMode() == true) {
            themeSwitchCompat!!.isChecked = true
        }
        themeSwitchCompat!!.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                (application as Encrypter).setMode(true)
                Toast.makeText(this@MainActivity, "Dark Mode Enabled", Toast.LENGTH_SHORT).show()
            } else {
                (application as Encrypter).setMode(false)
                Toast.makeText(this@MainActivity, "Dark Mode Disabled", Toast.LENGTH_SHORT).show()
            }
            recreate()
        }
        //location
//        locationSwitchCompat =
//            binding.navMenu.menu.findItem(R.id.location_menu).actionView.findViewById<View>(R.id.nav_switch_item) as SwitchCompat
//        locationSwitchCompat!!.setOnCheckedChangeListener { _, _ -> }


        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                binding.toolbar.findViewById<View>(R.id.toolbar_data).visibility = View.GONE
                supportActionBar!!.setDisplayShowTitleEnabled(true)
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                binding.toolbar.setNavigationOnClickListener { onBackPressed() }
            } else {
                binding.toolbar.findViewById<View>(R.id.toolbar_data).visibility = View.VISIBLE
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                supportActionBar!!.setDisplayShowTitleEnabled(false)
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                toggle!!.syncState()
                binding.toolbar.setNavigationOnClickListener {
                    binding.drawerLayout.openDrawer(
                        GravityCompat.START
                    )
                }
            }
        }
//internet status
        networkUtils.connection.observe(this) {
            if (it) {
                binding.connectivityLayout.visibility = View.INVISIBLE
                binding.connectivityLayout.layoutParams = RelativeLayout.LayoutParams(0, 0)
                val params = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                params.addRule(RelativeLayout.BELOW, R.id.toolbar)
                binding.fragmentViewer.layoutParams = params

            } else {
                binding.connectivityLayout.visibility = View.VISIBLE
                val params1 =
                    RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                    )
                params1.addRule(RelativeLayout.BELOW, R.id.toolbar)
                binding.connectivityLayout.layoutParams = params1
                val params = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                params.addRule(RelativeLayout.BELOW, R.id.connectivity_layout)
                binding.fragmentViewer.layoutParams = params
            }

        }
    }

    //runnable to update widget
    private var updateWidgetRunnable: Runnable = Runnable {
        run {
            networkUtils.checkInternetConnection()
            handler.postDelayed(updateWidgetRunnable, 2000)
        }

    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(updateWidgetRunnable, 2000)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(updateWidgetRunnable);
    }


    fun clickDrawerCloser() {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

}