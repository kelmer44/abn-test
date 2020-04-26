package com.kelmer.abn.foursquare.ui.list

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.kelmer.abn.foursquare.R
import com.kelmer.abn.foursquare.common.resource.resolve
import com.kelmer.abn.foursquare.common.util.handleError
import com.kelmer.abn.foursquare.data.db.model.Venue
import com.kelmer.abn.foursquare.domain.model.LatLon
import com.kelmer.abn.foursquare.ui.detail.DetailFragment
import com.kelmer.abn.foursquare.ui.detail.DetailFragmentArgs
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment(R.layout.fragment_list) {

    private val viewModel by viewModel<ListViewModel>()
    private val navController: NavController by lazy {
        findNavController()
    }

    private val venueAdapter = VenueListAdapter(object : VenueListAdapter.VenueListener {
        override fun onClick(venue: Venue) {
            navController.navigate(ListFragmentDirections.actionListFragmentToDetailFragment(venue.id))
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        venues_list.adapter = venueAdapter

        list_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty() && newText.length >= 3) {
                    viewModel.doSearch(newText, LatLon(52.37, 4.89))
                }
                return true
            }

        })

        viewModel.getVenues().observe(viewLifecycleOwner) { resource ->
            venues_list_progress.isVisible = resource.inProgress
            resource.resolve(
                onError = {
                    context?.handleError(it)
                },
                onSuccess = {
                    venueAdapter.updateVenues(it)
                })
        }
    }


}