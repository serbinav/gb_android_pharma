package com.example.comparepharma.view

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.lifecycle.Observer
import com.example.comparepharma.R
import com.example.comparepharma.databinding.MainFragmentBinding
import com.example.comparepharma.model.AppState
import com.example.comparepharma.model.Constants
import com.example.comparepharma.viewmodel.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private var _binding: MainFragmentBinding? = null
    private val binding
        get() = _binding!!
    private val adapter = PharmaAdapter()
    private var isDataSetAptekaRu: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter.setOnItemViewClickListener { cost ->
            requireActivity().supportFragmentManager.apply {
                val bundle = Bundle()
                bundle.putParcelable(DetailsFragment.BUNDLE_EXTRA, cost)
                beginTransaction()
                    .add(
                        R.id.container,
                        DetailsFragment.newInstance(Bundle().apply {
                            putParcelable(
                                DetailsFragment.BUNDLE_EXTRA,
                                cost
                            )
                        })
                    )
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }

        binding.recyclerView.adapter = adapter
        binding.floatingActionButton.setOnClickListener {
            changeAptekaDataSet()
            saveListOfPharma()
        }

        super.onViewCreated(view, savedInstanceState)

        val observer = Observer<AppState> { renderData(it) }
        viewModel.getDate().observe(viewLifecycleOwner, observer)
        loadListofPharma()
        showAptekaDataSet()
    }

    private fun loadListofPharma() {
        requireActivity().apply {
            isDataSetAptekaRu = getPreferences(Context.MODE_PRIVATE)
                .getBoolean(Constants.IS_APTEKA_RU_KEY, true)
        }
    }

    private fun saveListOfPharma() {
        requireActivity().apply {
            getPreferences(Context.MODE_PRIVATE).edit {
                putBoolean(Constants.IS_APTEKA_RU_KEY, isDataSetAptekaRu)
                apply()
            }
        }
    }

    private fun changeAptekaDataSet() {
        isDataSetAptekaRu = !isDataSetAptekaRu
        showAptekaDataSet()
    }

    private fun showAptekaDataSet() {
        if (isDataSetAptekaRu) {
            viewModel.getPharmaFromLocalAptekaRu()
            binding.floatingActionButton.setImageResource(R.drawable.apteka_april)
        } else {
            viewModel.getPharmaFromLocalAptekaApril()
            binding.floatingActionButton.setImageResource(R.drawable.apteka_ru)
        }
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.Success -> {
                binding.loadingLayout.hide()
                adapter.setPharma(data.pharmaData)
            }
            is AppState.Loading -> {
                binding.loadingLayout.show()
            }
            is AppState.Error -> {
                binding.loadingLayout.hide()
                binding.floatingActionButton.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload)
                ) {
                    if (isDataSetAptekaRu) {
                        viewModel.getPharmaFromLocalAptekaRu()
                    } else {
                        viewModel.getPharmaFromLocalAptekaApril()
                    }
                }
            }
        }
    }
}