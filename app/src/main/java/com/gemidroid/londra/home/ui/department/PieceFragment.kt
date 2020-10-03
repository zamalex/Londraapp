package com.gemidroid.londra.home.ui.department;

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.gemidroid.londra.R
import com.gemidroid.londra.home.ui.department.DepartmentFragment.Companion.SELECTED_PIECE_KEY
import kotlinx.android.synthetic.main.fragment_piece.*


class PieceFragment : Fragment() {

    private val TAG = "PieceFragment"
    lateinit var adapterNames: ColorNamesAdapter
    var isFavourite = false

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

        rec_pieces_designs.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, true)
            adapter = PiecesDesignsAdapter(listOf(R.drawable.mask,
                R.drawable.mask_style,
                R.drawable.ic_mask))
        }

        rec_clients_ratings.adapter = ClientRatingAdapter()

        adapterNames = ColorNamesAdapter(listOf(ColorNamesAdapter.ColorViewItem("أسود"),
            ColorNamesAdapter.ColorViewItem("أخضر"),
            ColorNamesAdapter.ColorViewItem("أحمر"),
            ColorNamesAdapter.ColorViewItem("موف"),
            ColorNamesAdapter.ColorViewItem("أزرق")))

        rec_color_names.apply {
            adapter = adapterNames
        }
    }
}
