package com.gemidroid.londra.home.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gemidroid.londra.R
import com.gemidroid.londra.home.ui.address.UpdateAddressActivity
import com.gemidroid.londra.login.ui.LoginActivity
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private var isInfoShow = true
    private var isAddressesShow = true
    private var isFavShow = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rec_my_addresses.apply {
            adapter = MyAddressesAdapter {
                val intent = Intent(requireActivity(), UpdateAddressActivity::class.java)
                intent.putExtra("addressId", it)
                startActivity(intent)
            }
        }

        rec_my_favourites.apply {
            adapter = MyFavouritesAdapter()
        }


        img_my_information.setOnClickListener {
            if (isInfoShow) {
                ctl_my_information.visibility = View.GONE
                img_my_information.setImageResource(R.drawable.ic_arrow_up)
                isInfoShow = false
            } else {
                ctl_my_information.visibility = View.VISIBLE
                img_my_information.setImageResource(R.drawable.ic_arrow_down)
                isInfoShow = true
            }
        }

        img_my_address.setOnClickListener {
            if (isAddressesShow) {
                rec_my_addresses.visibility = View.GONE
                img_my_address.setImageResource(R.drawable.ic_arrow_up)
                isAddressesShow = false
            } else {
                rec_my_addresses.visibility = View.VISIBLE
                img_my_address.setImageResource(R.drawable.ic_arrow_down)
                isAddressesShow = true
            }
        }

        img_my_favourites.setOnClickListener {
            if (isFavShow) {
                rec_my_favourites.visibility = View.GONE
                img_my_favourites.setImageResource(R.drawable.ic_arrow_up)
                isFavShow = false
            } else {
                rec_my_favourites.visibility = View.VISIBLE
                img_my_favourites.setImageResource(R.drawable.ic_arrow_down)
                isFavShow = true
            }
        }

        txt_logout.setOnClickListener {
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }
}