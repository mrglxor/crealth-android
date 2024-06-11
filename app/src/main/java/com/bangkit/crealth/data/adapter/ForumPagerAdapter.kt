package com.bangkit.crealth.data.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bangkit.crealth.view.fragment.PostFragment
import com.bangkit.crealth.view.fragment.ReplyFragment

class ForumPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PostFragment()
            1 -> ReplyFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}