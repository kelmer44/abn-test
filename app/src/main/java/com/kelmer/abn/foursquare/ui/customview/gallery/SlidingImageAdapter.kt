package com.kelmer.abn.foursquare.ui.customview.gallery

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.kelmer.abn.foursquare.R
import com.kelmer.abn.foursquare.common.glide.GlideApp

class SlidingImageAdapter(val context: Context) : androidx.viewpager.widget.PagerAdapter() {

    var images: MutableList<GalleryImage> = mutableListOf()
    var views = SparseArray<View>()

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.item_sliding_image, container, false)
        val imageView = view.findViewById<ImageView>(R.id.preview_image)
        val galleryImage = images[position]

        GlideApp.with(context).load(galleryImage.url).into(imageView)

        val viewPager = container as androidx.viewpager.widget.ViewPager
        viewPager.addView(view, 0)

        views.put(position, view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        val vp = container as androidx.viewpager.widget.ViewPager
        val view = obj as View
        views.remove(position)
        vp.removeView(view)
    }

    fun updateImages(listOf: List<GalleryImage>) {
        images = listOf.toMutableList()
        notifyDataSetChanged()
    }

    fun clear() {
        images.clear()
        notifyDataSetChanged()
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
}
