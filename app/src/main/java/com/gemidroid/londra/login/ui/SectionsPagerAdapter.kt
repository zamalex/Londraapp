package com.gemidroid.londra.login.ui

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gemidroid.londra.R

private val TAB_TITLES = arrayOf(
    R.string.register,
    R.string.login
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager, behaviour: Int) :
    FragmentPagerAdapter(fm, behaviour) {

    override fun getItem(position: Int): Fragment {
        if (position == 0)
            return RegisterFragment()
        else return LoginFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }
}