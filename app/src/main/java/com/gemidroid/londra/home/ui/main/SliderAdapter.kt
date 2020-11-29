import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.gemidroid.londra.R
import com.gemidroid.londra.home.data.main.SliderItem
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_slider_layout_item.view.*


class SliderAdapter(
    private val mSliderItems: List<String>
) :
    SliderViewAdapter<SliderAdapter.SliderAdapterVH>() {


    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.image_slider_layout_item, parent, false)
        return SliderAdapterVH(view)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {

        viewHolder.itemView.apply {
            txt_description.text = ""
            txt_content.text = ""



            if (!mSliderItems[position].isNullOrEmpty())
                Picasso.get().load(mSliderItems[position]).into(viewHolder.itemView.img_banner)


        }

    }

    override fun getCount(): Int {
        return mSliderItems.size
    }

    class SliderAdapterVH(itemView: View) : ViewHolder(itemView)

}
