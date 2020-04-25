package com.kelmer.abn.foursquare.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kelmer.abn.foursquare.R
import com.kelmer.abn.foursquare.data.db.model.Venue
import kotlinx.android.synthetic.main.item_venue.view.*

class VenueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(venue: Venue, listener: VenueListAdapter.VenueListener) = with(itemView) {
        item_venue_name.text = venue.name
        item_venue_root.setOnClickListener{
            listener.onClick(venue)
        }
    }

    companion object {
        fun create(parent: ViewGroup): VenueViewHolder {
            return VenueViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_venue, parent, false))
        }
    }
}