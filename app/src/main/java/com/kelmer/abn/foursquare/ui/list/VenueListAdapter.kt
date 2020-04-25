package com.kelmer.abn.foursquare.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kelmer.abn.foursquare.data.db.model.Venue

class VenueListAdapter(val listener: VenueListener) : RecyclerView.Adapter<VenueViewHolder>() {

    private var venues : List<Venue> = listOf()
    interface VenueListener{
        fun onClick(venue: Venue)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder = VenueViewHolder.create(parent)

    override fun getItemCount(): Int  = venues.size

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        holder.bind(venues[position], listener)
    }

    fun updateVenues(it: List<Venue>) {
        venues = it
        notifyDataSetChanged()
    }
}