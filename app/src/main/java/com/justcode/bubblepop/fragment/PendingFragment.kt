package com.justcode.bubblepop.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.justcode.bubblepop.R
import com.justcode.bubblepop.adapter.PendingAdapter
import com.justcode.bubblepop.model.PendingFinishResponse
import com.justcode.bubblepop.network.NetworkConfig
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_pending.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PendingFragment : Fragment() {

    companion object {
        private val ARG_USER_ID = "user_id"

        fun newInstance(user_id: String) : PendingFragment {
            val fragment = PendingFragment()
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
        return inflater.inflate(R.layout.fragment_pending, container, false)
    }

    private lateinit var userid: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            userid = arguments?.getString(ARG_USER_ID) as String
        }

        // get list pending
        getListPending(userid)
    }

    private fun getListPending(user_id: String) {
        // show loading
//        showPendingLoading()

        // process
        NetworkConfig.service()
            .getListPending(user_id)
            .enqueue(object: Callback<PendingFinishResponse> {
                override fun onFailure(call: Call<PendingFinishResponse>, t: Throwable) {
//                    hidePendingLoading()
                    rvPending.visibility = View.GONE
                    layoutPendingRefresh.visibility = View.VISIBLE
                }

                override fun onResponse(
                    call: Call<PendingFinishResponse>,
                    response: Response<PendingFinishResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.total == 0) {
                            rvPending.visibility = View.GONE
                            layoutPendingEmpty.visibility = View.VISIBLE
                        } else {
                            val data = response.body()?.transaction
                            rvPending.layoutManager = LinearLayoutManager(context)
                            rvPending.adapter = PendingAdapter(context, data)
                        }
                    }
                }
            })
    }

    fun showPendingLoading() {
        toast("loading")
    }

    fun hidePendingLoading() {
        toast("finish")
    }
}