package com.justcode.bubblepop.adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.justcode.bubblepop.R
import com.justcode.bubblepop.fragment.ProfileFragment
import com.justcode.bubblepop.fragment.SettingFragment

class ProfilePagerAdapter(private val context: Context, fm: FragmentManager, private val userid: String)
    : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.profile, R.string.setting)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = ProfileFragment.newInstance(userid)
            1 -> fragment = SettingFragment.newInstance(userid)
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }

}