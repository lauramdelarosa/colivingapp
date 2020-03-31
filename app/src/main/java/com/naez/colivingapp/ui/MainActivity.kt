package com.naez.colivingapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.naez.colivingapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        when (NavHostFragment.findNavController(navHostFragment).currentDestination?.id) {
            R.id.spaceFragment -> finish()
            else -> super.onBackPressed()
        }
    }
}

