package com.gemidroid.londra.home.ui.department

import android.content.Intent
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
import androidx.recyclerview.widget.GridLayoutManager
import com.gemidroid.londra.R
import com.gemidroid.londra.home.ui.HomeActivity
import com.gemidroid.londra.home.ui.department.model.CatProducstRes
import com.gemidroid.londra.login.ui.LoginActivity
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_department.*
import retrofit2.HttpException
import java.net.UnknownHostException

class DepartmentFragment : Fragment() {

    private val TAG = "DepartmentFragment"
    private var isFavourite = false
    val  viewModel by activityViewModels<ProductsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_department, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().intent?.extras?.getInt("departmentId").let {
            Log.e(TAG, "onViewCreated:  $it ")
        }


        viewModel.getCatProducts()

        setResponse()
        setError()


        img_back.setOnClickListener {
            requireActivity().onBackPressed()
        }


    }

    fun setResponse() = viewModel.getCatProductsResponse.observe(viewLifecycleOwner, Observer {
        if (it != null && it.success) {
            rec_pieces.apply {
                layoutManager =
                    GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
                adapter = PiecesAdapter({
                    val bundle = bundleOf(SELECTED_PIECE_KEY to it)
                    findNavController().navigate(R.id.action_departmentFragment_to_pieceFragment,
                        bundle)

                }, {
                    isFavourite = if (!isFavourite) {
                        it.setImageResource(R.drawable.ic_favorite)
                        true
                    } else {
                        it.setImageResource(R.drawable.ic_un_favorite)
                        false
                    }
                }).apply { setList(it.data.data as ArrayList<CatProducstRes.Data.Data>) }

            }

        }



    })

    fun setError() = viewModel.getCatProductsError.observe(viewLifecycleOwner, Observer {

        if (it != null) {
            if (it is UnknownHostException)
                Toast.makeText(activity, "no internet connection", Toast.LENGTH_SHORT).show()
            else if (it is HttpException) {
                Log.e("eeee",it.response()!!.errorBody()!!.string())

                Toast.makeText(activity, it.response()!!.message().toString(), Toast.LENGTH_SHORT)
                    .show()
            }else
                Toast.makeText(activity, "server response error", Toast.LENGTH_SHORT).show()
        }

    })

    companion object {
        const val SELECTED_PIECE_KEY = "selected_piece_key"
    }
}