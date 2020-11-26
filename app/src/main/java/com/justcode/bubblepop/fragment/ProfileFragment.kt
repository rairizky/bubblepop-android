package com.justcode.bubblepop.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.justcode.bubblepop.R
import kotlinx.android.synthetic.main.fragment_profile.*
import org.jetbrains.anko.support.v4.toast


class ProfileFragment : Fragment() {

    companion object {
        private val ARG_USER_ID = "user_id"

        fun newInstance(user_id: String) : ProfileFragment {
            val fragment = ProfileFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USER_ID, user_id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    private lateinit var userid: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            userid = arguments?.getString(ARG_USER_ID) as String
        }

        getIdProfile.text = userid
    }

}