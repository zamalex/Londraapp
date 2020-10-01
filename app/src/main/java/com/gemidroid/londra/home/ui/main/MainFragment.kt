package com.gemidroid.londra.home.ui.main

import SliderAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gemidroid.londra.R
import com.gemidroid.londra.home.data.main.SliderItem
import com.gemidroid.londra.home.ui.specialorder.SpecialOrderActivity
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        slideShow()

        rec_orders.apply {
            adapter = SpecialAdapter {}
        }

        rtl_orders.setOnClickListener {
            startActivity(Intent(requireActivity(), SpecialOrderActivity::class.java))
        }
    }

    private fun slideShow() {
        sliderView.setSliderAdapter(
            SliderAdapter(
                mutableListOf(
                    SliderItem(
                        "عروض 2017",
                        R.mipmap.logo,
                        "اكتشفي أفضل العروض لدينا في لُندرا"
                    ), SliderItem(
                        "عروض 2018",
                        R.mipmap.logo,
                        "اكتشفي أفضل العروض لدينا في لُندرا"
                    ), SliderItem(
                        "عروض 2019",
                        R.mipmap.logo,
                        "اكتشفي أفضل العروض لدينا في لُندرا"
                    ), SliderItem(
                        "عروض 2020",
                        R.mipmap.logo,
                        "اكتشفي أفضل العروض لدينا في لُندرا"
                    )
                )
            ) {
                // Handle clicking on banner ....
            })

        sliderView.startAutoCycle()
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
    }
}