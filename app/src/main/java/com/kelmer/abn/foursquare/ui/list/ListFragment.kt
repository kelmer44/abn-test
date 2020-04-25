package com.kelmer.abn.foursquare.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.kelmer.abn.foursquare.R
import com.kelmer.abn.foursquare.ui.detail.DetailViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.compat.ScopeCompat
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment(R.layout.fragment_list) {

    private val viewModel by viewModel<ListViewModel>()
    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        go_detail.setOnClickListener {
            navController.navigate(R.id.action_listFragment_to_detailFragment)
        }
    }


}