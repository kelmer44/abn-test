package com.kelmer.abn.foursquare.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kelmer.abn.foursquare.R
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
    }
}