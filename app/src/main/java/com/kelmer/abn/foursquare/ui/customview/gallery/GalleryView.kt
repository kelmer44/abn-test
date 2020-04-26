package com.kelmer.abn.foursquare.ui.customview.gallery

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.isVisible
import com.kelmer.abn.foursquare.R

class GalleryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr),
    androidx.viewpager.widget.ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        refreshArrows(position)
    }

    private fun refreshArrows(position: Int) {
        leftArrow.isVisible = position > 0
        rightArrow.isVisible = position < adapter.count - 1
    }

    private val adapter = SlidingImageAdapter(this.context)
    private val pager: androidx.viewpager.widget.ViewPager
    private val rightArrow: ImageView

    private val leftArrow: ImageView

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.view_gallery, this, true)
        pager = findViewById(R.id.gallery_pager)

        rightArrow = findViewById(R.id.gallery_right_arrow)
        leftArrow = findViewById(R.id.gallery_left_arrow)

        pager.adapter = adapter
        pager.addOnPageChangeListener(this)

        rightArrow.setOnClickListener {
            val currentItem = pager.currentItem
            val count = adapter.count
            if (currentItem < count - 1) {
                pager.currentItem = currentItem + 1
            }
        }

        leftArrow.setOnClickListener {
            val currentItem = pager.currentItem
            if (currentItem > 0) {
                pager.currentItem = currentItem - 1
            }
        }
    }

    fun setImages(images: List<GalleryImage>) {
        adapter.updateImages(images)
        rightArrow.isVisible = adapter.count > 1
        refreshArrows(pager.currentItem)
    }

    fun clear() {
        adapter.clear()
        rightArrow.isVisible = false
        leftArrow.isVisible = false
    }


    interface ImageClickListener {
        fun onClick(image: GalleryImage, imageView: ImageView, position: Int, fromMe: Boolean)
        fun onCaptionClick(image: GalleryImage)
    }

}
