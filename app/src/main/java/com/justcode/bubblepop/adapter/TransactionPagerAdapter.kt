package com.justcode.bubblepop.adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.justcode.bubblepop.R
import com.justcode.bubblepop.fragment.CartFragment
import com.justcode.bubblepop.fragment.FinishFragment
import com.justcode.bubblepop.fragment.PendingFragment

class TransactionPagerAdapter(private val context: Context, fm: FragmentManager, private val userid: String)
    : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tr_keranjang, R.string.tr_pending, R.string.tr_finish)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = CartFragment.newInstance(userid)
            1 -> fragment = PendingFragment.newInstance(userid)
            2 -> fragment = FinishFragment.newInstance(userid)
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }
}