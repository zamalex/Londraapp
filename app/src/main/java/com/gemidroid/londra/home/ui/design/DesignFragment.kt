package com.gemidroid.londra.home.ui.design

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gemidroid.londra.R
import kotlinx.android.synthetic.main.fragment_design.*


class DesignFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_design, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edt_hair_color.setOnClickListener {
            val popup = PopupMenu(requireActivity(), edt_hair_color)
            popup.menu.add(0,0,0,"one")//(groupId, itemId, order, title)
            popup.menu.add(0,1,1,"two")
            popup.menu.add(0,2,2,"three")

            popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
                override fun onMenuItemClick(p0: MenuItem?): Boolean {
                    Toast.makeText(activity,"id ${p0!!.itemId} title ${p0!!.title}",Toast.LENGTH_SHORT).show()
                    return false
                }
            })
            popup.show()
        }

        edt_body_type.setOnClickListener {
            val popup = PopupMenu(requireActivity(), edt_hair_color)
            popup.menu.add(0,0,0,"four")//(groupId, itemId, order, title)
            popup.menu.add(0,1,1,"five")
            popup.menu.add(0,2,2,"six")

            popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
                override fun onMenuItemClick(p0: MenuItem?): Boolean {
                    Toast.makeText(activity,"id ${p0!!.itemId} title ${p0!!.title}",Toast.LENGTH_SHORT).show()
                    return false
                }
            })
            popup.show()
        }

        edt_skin_color.setOnClickListener {
            val popup = PopupMenu(requireActivity(), edt_hair_color)


            popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
                override fun onMenuItemClick(p0: MenuItem?): Boolean {
                    Toast.makeText(activity,"id ${p0!!.itemId} title ${p0!!.title}",Toast.LENGTH_SHORT).show()
                    return false
                }
            })
            popup.show()
        }
    }
}