package com.gemidroid.londra.home.ui.department;

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
import com.gemidroid.londra.home.ui.department.DepartmentFragment.Companion.SELECTED_PIECE_KEY
import com.gemidroid.londra.home.ui.department.model.ProductDetailsRes
import com.gemidroid.londra.login.ui.model.LoginRes
import com.google.gson.JsonObject
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_piece.*
import kotlinx.android.synthetic.main.piece_item.view.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.net.UnknownHostException


class PieceFragment : Fragment() {

    val loginRes = Paper.book().read<LoginRes>("login", LoginRes())
    private val TAG = "PieceFragment"
    lateinit var adapterNames: ColorNamesAdapter
    var isFavourite = false
    val viewModel by activityViewModels<ProductsViewModel>()

    var jsonObject: JsonObject = JsonObject()
    var selectedColor: Int? = null
    var selectedSize: Int? = null
    var selectedAddition: Int? = null
    var cart: String? = null
    var parts: List<MultipartBody.Part> = ArrayList()


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

        btn_add_to_cart.setOnClickListener {
            (activity as DepartmentActivity).loading?.show()


            cart = Paper.book().read("cart", null)

            jsonObject.addProperty("user_id", loginRes.data.user.id)
            jsonObject.addProperty("product_id", viewModel.getProductResponse.value?.data?.id)
            jsonObject.addProperty("quantity", 1)


            val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("user_id", loginRes.data.user.id.toString())
                .addFormDataPart(
                    "product_id",
                    viewModel.getProductResponse.value?.data?.id.toString()
                )
                .addFormDataPart("quantity", "1")

            if (cart != null)
                builder.addFormDataPart("cart_id", cart!!)
            if (selectedColor != null)
                builder.addFormDataPart("options[]", selectedColor.toString())


            if (selectedSize != null)
                builder.addFormDataPart("options[]", selectedSize.toString())

            if (selectedAddition != null)
                builder.addFormDataPart("options[]", selectedAddition.toString())



            viewModel.addProduct(builder.build())


        }







        setResponse()
        setError()
        setAddResponse()
        setAddError()
    }


    fun setResponse() = viewModel.getProductResponse.observe(viewLifecycleOwner, Observer {
        (activity as DepartmentActivity).loading?.dismiss()

        if (it != null && it.success) {
            it.data.let { r ->

                if (r.in_favourite == 1) {
                    img_add_to_fav.setImageResource(R.drawable.ic_favorite)

                } else
                    img_add_to_fav.setImageResource(R.drawable.ic_un_favorite)

                img_add_to_fav.setOnClickListener {
                    viewModel.addRemoveFavs(
                        "Bearer ${loginRes.data.accessToken}",
                        JsonObject().apply { addProperty("product_id", r.id.toString()) })

                }

                adapterNames = ColorNamesAdapter(
                    r.colors.values
                ) { c ->
                    selectedColor = c.id

                }

                rec_color_names.apply {
                    adapter = adapterNames
                }

                rec_sizes_names.apply {
                    adapter = SizesAdapter(
                        r.sizes.values
                    ) { c ->
                        txt_binch.text = c.hintName1
                        txt_twist.text = c.hintName2
                        txt_leg.text = c.hintName3
                        selectedSize = c.id
                    }
                }


                if (r.additional != null)
                    rd_group.apply {
                        adapter = AdditionalAdapter(
                            r.additional.values
                        ) { c ->
                            selectedAddition = c.id
                        }
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


    fun setAddResponse() = viewModel.addProductResponse.observe(viewLifecycleOwner, Observer {
        (activity as DepartmentActivity).loading?.dismiss()
        if (it != null && it.success) {
            Toast.makeText(activity, "added to cart", Toast.LENGTH_SHORT).show()
            Paper.book().write("cart", it.data.cartId.toString())
        }

    })

    fun setAddError() = viewModel.addProductError.observe(viewLifecycleOwner, Observer {
        (activity as DepartmentActivity).loading?.dismiss()

        if (it != null) {
            Log.e(TAG, "setAddError: ", it)
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
