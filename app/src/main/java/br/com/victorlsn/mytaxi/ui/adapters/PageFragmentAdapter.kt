package br.com.victorlsn.mytaxi.ui.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import java.util.ArrayList

/**
 * Created by victorlsn on 27/02/19.
 *
 */

class PageFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val mFragments = ArrayList<Fragment>()

    fun addFragment(fragment: Fragment) {
        mFragments.add(fragment)
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }


    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return null
    }
}