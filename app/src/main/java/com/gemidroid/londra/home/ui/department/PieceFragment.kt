package com.gemidroid.londra.home.ui.department;

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.gemidroid.londra.R
import com.gemidroid.londra.api.SuccessResponse
import com.gemidroid.londra.home.ui.HomeActivity
import com.gemidroid.londra.home.ui.department.DepartmentFragment.Companion.SELECTED_PIECE_KEY
import com.gemidroid.londra.home.ui.department.model.ProductDetailsRes
import com.gemidroid.londra.login.ui.LoginActivity
import com.google.gson.Gson
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_piece.*
import retrofit2.HttpException
import java.net.UnknownHostException


class PieceFragment : Fragment() {

    private val TAG = "PieceFragment"
    lateinit var adapterNames: ColorNamesAdapter
    var isFavourite = false
    val viewModel by activityViewModels<ProductsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_piece, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt(SELECTED_PIECE_KEY).let {
            Log.e(TAG, "onViewCreated: $it ")
        }


        (activity as DepartmentActivity).loading?.show()
        viewModel.gwtProduct(1)

        img_back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        img_add_to_fav.setOnClickListener {
            it as ImageView
            isFavourite = if (!isFavourite) {
                it.setImageResource(R.drawable.ic_favorite)
                true
            } else {
                it.setImageResource(R.drawable.ic_un_favorite)
                false
            }
        }








        setResponse()
        setError()
    }


    fun setResponse() = viewModel.getProductResponse.observe(viewLifecycleOwner, Observer {
        (activity as DepartmentActivity).loading?.dismiss()

        if (it != null && it.success) {
            it.data.let { r ->

                adapterNames = ColorNamesAdapter(
                   r.colors[0].values,{c->
                        Toast.makeText(activity,c.label,Toast.LENGTH_SHORT).show()

                    }
                )

                rec_color_names.apply {
                    adapter = adapterNames
                }

                rec_sizes_names.apply {
                    adapter = SizesAdapter(
                        r.sizes[0].values,{c->
                            txt_binch.text = c.hintName1
                            txt_twist.text=c.hintName2
                            txt_leg.text = c.hintName3
                        }
                    )
                }


                rd_group.apply {
                    adapter = AdditionalAdapter(
                        r.additional[0].values,{c->

                        }
                    )
                }

                txt_brand_name.text = r.name
                txt_piece_name.text = r.name
                txt_piece_price.text =
                    if (r.hasOffer) "${r.sellingPrice} ريال " else "${r.price} ريال "
                txt_desc.text = r.description
                txt_material.text = r.material
                txt_factory.text = r.designer
                rec_clients_ratings.adapter =
                    ClientRatingAdapter(r.reviews as ArrayList<ProductDetailsRes.Data.Review>)
                rec_pieces_designs.apply {
                    layoutManager =
                        GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, true)
                    adapter = PiecesDesignsAdapter(
                        r.images
                    )
                }

            }

        }


    })

    fun setError() = viewModel.getProductError.observe(viewLifecycleOwner, Observer {
        (activity as DepartmentActivity).loading?.dismiss()

        if (it != null) {
            if (it is UnknownHostException)
                Toast.makeText(activity, "no internet connection", Toast.LENGTH_SHORT).show()
            else if (it is HttpException) {

                Toast.makeText(activity, it.response()!!.message().toString(), Toast.LENGTH_SHORT)
                    .show()
            } else
                Toast.makeText(activity, "server response error", Toast.LENGTH_SHORT).show()
        }

    })
}
