package com.example.comparepharma.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.comparepharma.R
import com.example.comparepharma.databinding.FavoritesFragmentBinding
import com.example.comparepharma.model.AppState
import com.example.comparepharma.viewmodel.FavoritesViewModel

class FavoritesFragment : Fragment() {

    private var _binding: FavoritesFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoritesViewModel by lazy {
        ViewModelProvider(this).get(FavoritesViewModel::class.java)
    }

    private val adapter: FavoritesAdapter by lazy {
        FavoritesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FavoritesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favoriteFragmentRecyclerview.adapter = adapter
        viewModel.favoritesLiveData.observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getAllFavorites()
    }

    private fun renderData(appState: AppState?) {
        when (appState) {
            is AppState.Success -> {
                binding.favoriteFragmentRecyclerview.show()
                binding.includeLoadingLayout.loadingLayout.hide()
                adapter.setData(appState.pharmaData)
            }
            is AppState.Loading -> {
                binding.favoriteFragmentRecyclerview.hide()
                binding.includeLoadingLayout.loadingLayout.show()
            }
            is AppState.Error -> {
                binding.favoriteFragmentRecyclerview.show()
                binding.includeLoadingLayout.loadingLayout.hide()
                binding.favoriteFragmentRecyclerview.showSnackBar(
                    getString(R.string.error_title),
                    getString(R.string.reload)
                ) {
                    viewModel.getAllFavorites()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FavoritesFragment()
    }
}