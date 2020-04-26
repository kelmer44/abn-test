package com.kelmer.abn.foursquare.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kelmer.abn.foursquare.R
import com.kelmer.abn.foursquare.common.glide.GlideApp
import com.kelmer.abn.foursquare.common.resource.resolve
import com.kelmer.abn.foursquare.common.util.handleError
import com.kelmer.abn.foursquare.data.db.model.VenueDetails
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel by viewModel<DetailViewModel>()
    private val navController: NavController by lazy {
        findNavController()
    }

    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getVenue(args.venueId).observe(viewLifecycleOwner) { resource ->
            resource.resolve(
                onError = {
                    context?.handleError(it)
                },
                onSuccess = {
                    fillInDetails(it)
                })
        }
    }

    private fun fillInDetails(venue: VenueDetails) = with(venue) {
        venue_details_name.text = this.name

        val backdropPhoto = venue.bestPhoto ?: venue.photos.firstOrNull()
        if (backdropPhoto != null) {
            GlideApp.with(detail_appbar_backdrop)
                .load(backdropPhoto?.url)
                .centerCrop().into(detail_appbar_backdrop)
        }
        else {
            detail_appbar_backdrop.setImageResource(R.drawable.ic_no_photo)
        }
        detail_toolbar.title = venue.name
        venue_details_description.text = venue.description
        venue_details_contactinfo_phone.setText(venue.contactInfo.phone)
        venue_details_contactinfo_phone.setOnClickListener {
           callNumber(venue.contactInfo.phone)
        }
        venue_details_contactinfo_twitter.setText(venue.contactInfo.twitter)
        venue_details_contactinfo_twitter.setOnClickListener {
          openUrl("https://twitter.com/${venue.contactInfo.twitter}")
        }
        venue_details_rating.isVisible = venue.hasRating
        venue_details_rating.rating = venue.rating
        venue_details_address.text = venue.formattedAddress

    }

    private fun openUrl(url: String){
        val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse(url)
        startActivity(openURL)
    }

    private fun callNumber(number: String){
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
        startActivity(intent)
    }
}