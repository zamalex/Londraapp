import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.gemidroid.londra.R
import com.gemidroid.londra.home.data.main.SliderItem
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.image_slider_layout_item.view.*


class SliderAdapter(
    private val mSliderItems: List<SliderItem>,
    private val onItemClick: (SliderItem) -> Unit
) :
    SliderViewAdapter<SliderAdapter.SliderAdapterVH>() {

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.image_slider_layout_item, parent, false)
        return SliderAdapterVH(view)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val sliderItem = mSliderItems[position]

        viewHolder.itemView.apply {
            txt_description.text = sliderItem.description
            txt_content.text = sliderItem.content

            Glide.with(viewHolder.itemView)
                .load(sliderItem.source)
                .fitCenter()
                .into(viewHolder.itemView.img_banner)

            setOnClickListener {
                onItemClick.invoke(sliderItem)
            }
        }

    }

    override fun getCount(): Int {
        return mSliderItems.size
    }

    class SliderAdapterVH(itemView: View) : ViewHolder(itemView)

}
