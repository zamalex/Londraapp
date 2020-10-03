package com.gemidroid.londra.home.ui.department

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.gemidroid.londra.R
import kotlinx.android.synthetic.main.fragment_department.*

class DepartmentFragment : Fragment() {

    private val TAG = "DepartmentFragment"
    private var isFavourite = false

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

        img_back.setOnClickListener {
            requireActivity().onBackPressed()
        }

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
            })
        }
    }

    companion object {
        const val SELECTED_PIECE_KEY = "selected_piece_key"
    }
}