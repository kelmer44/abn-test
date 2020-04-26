package com.kelmer.abn.foursquare.ui.customview.contactview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.kelmer.abn.foursquare.R

class ContactView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    private val textView: TextView
    private val iconView: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.view_contact_info, this, true)
        textView = findViewById(R.id.contactview_text)
        iconView = findViewById(R.id.contactview_icon)


        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.ContactView,
            defStyleAttr,
            0
        )
        val text = a.getString(R.styleable.ContactView_cv_text)
        val icon = a.getDrawable(R.styleable.ContactView_cv_icon)
        if (text != null) {
            setText(text)
        }
        if (icon != null) {
            setIcon(icon)
        }
        a.recycle()
    }


    fun setText(@StringRes text: Int) {
        textView.setText(text)
    }

    fun setText(text: String) {
        textView.text = text
    }


    fun setIcon(@DrawableRes drawable: Int) {
        iconView.setImageResource(drawable)
    }

    fun setIcon(drawable: Drawable) {
        iconView.setImageDrawable(drawable)
    }
}