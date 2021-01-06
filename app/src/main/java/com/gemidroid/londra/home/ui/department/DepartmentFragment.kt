package com.gemidroid.londra.home.ui.department

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gemidroid.londra.R
import com.gemidroid.londra.api.SuccessResponse
import com.gemidroid.londra.home.ui.HomeActivity
import com.gemidroid.londra.home.ui.department.model.CatProducstRes
import com.gemidroid.londra.login.ui.LoginActivity
import com.gemidroid.londra.login.ui.model.LoginRes
import com.gemidroid.londra.utils.EndlessRecyclerViewScrollListener
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_department.*
import retrofit2.HttpException
import java.net.UnknownHostException

class DepartmentFragment : Fragment() {
    var page: Int = 1
    var cat: Int? = null
    private val TAG = "DepartmentFragment"
    private var isFavourite = false
    val viewModel by activityViewModels<ProductsViewModel>()
    val loginRes = Paper.book().read<LoginRes>("login", LoginRes())
    lateinit var madapter: PiecesAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_department, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent = requireActivity().intent
        if (Intent.ACTION_VIEW == intent.action&&intent.data!=null) {
            val uri: Uri? = intent.data
            val valueOne: String = uri!!.getQueryParameter("keyOne")!!
            Log.e("deeeeeeep", "one is ${valueOne}" )

            return
        }


        madapter = PiecesAdapter({
            val bundle = bundleOf(SELECTED_PIECE_KEY to it)
            findNavController().navigate(
                R.id.action_departmentFragment_to_pieceFragment,
                bundle
            )

        }, { img, id ->
            if(img.drawable.constantState == requireActivity().getDrawable(R.drawable.ic_un_favorite)!!.constantState){
                img.setImageResource(R.drawable.ic_favorite)

            }else
                img.setImageResource(R.drawable.ic_un_favorite)


            viewModel.addRemoveFavs(
                "Bearer ${loginRes.data.accessToken}",
                JsonObject().apply { addProperty("product_id", id.toString()) })
        })

        rec_pieces.apply {
            adapter = this@DepartmentFragment.madapter
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

            addOnScrollListener(object : EndlessRecyclerViewScrollListener() {
                override fun getLayoutManager(): RecyclerView.LayoutManager {
                    return rec_pieces.layoutManager!!

                }

                override fun onLoadMore() {
                    if (page >= 1) {
                        page++
                        viewModel.getCatProducts(page, cat)
                    }
                }
            })
        }

        requireActivity().intent?.extras?.getInt("departmentId").let {
            cat = it
            Log.e(TAG, "onViewCreated:  $cat ")
        }

        requireActivity().intent?.extras?.getString("departmentName").let {
            Log.e(TAG, "onViewCreated:  $it ")
            txt_department_name.text = it
        }

        requireActivity().intent?.extras?.getString("departmentCount").let {
            Log.e(TAG, "onViewCreated:  $it ")
            txt_department_containing.text = "${it} قطعة "
        }


        (activity as DepartmentActivity).loading?.show()

        viewModel.getCatProducts(1, cat)



        setResponse()
        setError()


        img_back.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }


    fun setResponse() = viewModel.getCatProductsResponse.observe(viewLifecycleOwner, Observer {
        (activity as DepartmentActivity).loading?.dismiss()

        if (it != null && it.success) {
            madapter.setList(it.data.data as ArrayList<CatProducstRes.Data.Data>)


        }


    })

    fun setError() = viewModel.getCatProductsError.observe(viewLifecycleOwner, Observer {
        (activity as DepartmentActivity).loading?.dismiss()


        if (it != null) {
            if (it is UnknownHostException)
                Toast.makeText(activity, "no internet connection", Toast.LENGTH_SHORT).show()
            else if (it is HttpException) {
                // Log.e("eeee", it.response()!!.errorBody()!!.string())
                var successResponse = Gson().fromJson(
                    it.response()!!.errorBody()!!.string(),
                    SuccessResponse::class.java
                )
                if (successResponse != null && !successResponse.message.isNullOrEmpty())
                    Toast.makeText(activity, successResponse.message, Toast.LENGTH_SHORT)
                        .show()
                else


                    Toast.makeText(
                        activity,
                        it.response()!!.message().toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
            } else
                Toast.makeText(activity, "server response error", Toast.LENGTH_SHORT).show()
        }

    })

    companion object {
        const val SELECTED_PIECE_KEY = "selected_piece_key"
    }
}