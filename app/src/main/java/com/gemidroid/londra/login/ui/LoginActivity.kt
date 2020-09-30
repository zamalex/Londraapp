package com.gemidroid.londra.login.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.viewpager.widget.ViewPager
import com.gemidroid.londra.R
import com.google.android.material.tabs.TabLayout

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val sectionsPagerAdapter = SectionsPagerAdapter(
            this, supportFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }
}