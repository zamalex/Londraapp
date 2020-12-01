package com.gemidroid.londra.home.ui.main

import SliderAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.gemidroid.londra.R
import com.gemidroid.londra.home.data.main.SliderItem
import com.gemidroid.londra.home.ui.HomeActivity
import com.gemidroid.londra.home.ui.department.DepartmentActivity
import com.gemidroid.londra.home.ui.main.model.CatRes
import com.gemidroid.londra.home.ui.specialorder.SpecialOrderActivity
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.HttpException
import java.net.UnknownHostException

class MainFragment : Fragment() {

    val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        (activity as HomeActivity).loading!!.show()
        viewModel.getCats()
        viewModel.getSlider()

        viewModel.catsResponse.observe(viewLifecycleOwner, Observer {
            (activity as HomeActivity).loading!!.dismiss()
            if (it!=null&&it.success){
                rec_orders.apply {
                    adapter = DepartmentsAdapter {id,name,count->
                        val intent = Intent(requireActivity(), DepartmentActivity::class.java)
                        intent.putExtra("departmentId", id)
                        intent.putExtra("departmentName", name)
                        intent.putExtra("departmentCount", count)
                        startActivity(intent)
                    }.apply { setCats(it.data as ArrayList<CatRes.Data>) }
                }
            }
        })

        viewModel.catsError.observe(viewLifecycleOwner, Observer {
            (activity as HomeActivity).loading!!.dismiss()

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

        viewModel.sliderResponse.observe(viewLifecycleOwner, Observer {
            (activity as HomeActivity).loading!!.dismiss()
            if (it!=null&&it.success){
                if (!it.data.isNullOrEmpty()){
                    slideShow(it.data)

                }
            }
        })

        viewModel.sliderError.observe(viewLifecycleOwner, Observer {
            (activity as HomeActivity).loading!!.dismiss()

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

        rtl_orders.setOnClickListener {
            startActivity(Intent(requireActivity(), SpecialOrderActivity::class.java))
        }
    }

    private fun slideShow(list:List<String>) {
        sliderView.setSliderAdapter(
            SliderAdapter(list))

        sliderView.startAutoCycle()
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
    }
}