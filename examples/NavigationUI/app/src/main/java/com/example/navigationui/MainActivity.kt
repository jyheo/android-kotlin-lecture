package com.example.navigationui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setup actionbar with nav controller to show up button
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        // CAUTION: findNavController(R.id.fragment) in on Create will fail.
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)

        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite ->
                OkCancelDialogFragment().show(supportFragmentManager, "OkCancelDialog")
                //findNavController(R.id.fragment).navigate(R.id.action_homeFragment_to_okCancelDialogFragment)
            R.id.action_datepicker ->
                DatePickerFragment().show(supportFragmentManager, "DatePickerDialog")
            R.id.action_settings ->
                MyBottomSheetDialog().show(supportFragmentManager, "MyBottomSheetDialog")
            R.id.navDrawer ->
                startActivity(Intent(this, MainActivityDrawer::class.java))
            R.id.navBottom ->
                startActivity(Intent(this, MainActivityBottomNav::class.java))
            R.id.homeFragment, R.id.page1Fragment, R.id.page2Fragment, R.id.page3Fragment ->
                item.onNavDestinationSelected(findNavController(R.id.fragment))
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}