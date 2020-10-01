package com.gemidroid.londra.home.ui.myorders

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gemidroid.londra.R

private val TAB_TITLES = arrayOf(
    R.string.title_my_prev_orders,
    R.string.title_my_orders
)

class OrdersPagerAdapter(private val context: Context, fm: FragmentManager, behaviour: Int) :
    FragmentPagerAdapter(fm, behaviour) {

    override fun getItem(position: Int): Fragment {
        return if (position == 0)
            MyPrevOrdersListFragment()
        else MyOrdersListFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }
}