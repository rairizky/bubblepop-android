package com.justcode.bubblepop.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.justcode.bubblepop.R
import com.justcode.bubblepop.TransactionActivity
import com.justcode.bubblepop.adapter.CartAdapter
import com.justcode.bubblepop.model.CartResponse
import com.justcode.bubblepop.model.MessageResponse
import com.justcode.bubblepop.network.NetworkConfig
import kotlinx.android.synthetic.main.fragment_cart.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CartFragment : Fragment() {

    companion object {
        private val ARG_USER_ID = "user_id"

        fun newInstance(user_id: String) : CartFragment {
            val fragment = CartFragment()
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
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    private lateinit var userid: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            userid = arguments?.getString(ARG_USER_ID) as String
        }

        // get list cart
        getCart(userid)
    }

    private fun getCart(user_id: String) {
        // loading
        showCartLoading()

        // process
        NetworkConfig.service()
            .getListCart(user_id)
            .enqueue(object: Callback<CartResponse> {
                override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                    hideCartLoading()
                    rvCart.visibility = View.GONE
                    layoutCartRefresh.visibility = View.VISIBLE
                }

                override fun onResponse(
                    call: Call<CartResponse>,
                    response: Response<CartResponse>
                ) {
                    if (response.isSuccessful) {
                        hideCartLoading()

                        val detail = response.body()
                        if (detail?.total == 0) {
                            rvCart.visibility = View.GONE
                            layoutCartEmpty.visibility = View.VISIBLE
                        } else {
                            val data = response.body()?.transaction
                            totalPriceCart.text = response.body()?.tprice.toString()
                            rvCart.layoutManager = LinearLayoutManager(context)
                            rvCart.adapter = CartAdapter(context, data)

                            // btn checkout
                            btnCheckout.setOnClickListener {
                                NetworkConfig.service()
                                    .postCheckout(user_id, response.body()?.tprice)
                                    .enqueue(object: Callback<MessageResponse> {
                                        override fun onFailure(
                                            call: Call<MessageResponse>,
                                            t: Throwable
                                        ) {
                                            toast(t.localizedMessage)
                                        }

                                        override fun onResponse(
                                            call: Call<MessageResponse>,
                                            responseCheckout: Response<MessageResponse>
                                        ) {
                                            if (responseCheckout.isSuccessful) {
                                                if (responseCheckout.body()?.status == false) {
                                                    toast(responseCheckout.body()?.message.toString())
                                                } else {
                                                    toast(responseCheckout.body()?.message.toString())
                                                    startActivity<TransactionActivity>()
                                                    activity?.finish()
                                                }
                                            }
                                        }
                                    })
                            }
                        }
                    }
                }
            })
    }

    fun showCartLoading() {
        toast("loading")
    }

    fun hideCartLoading() {
        toast("finish")
    }
}