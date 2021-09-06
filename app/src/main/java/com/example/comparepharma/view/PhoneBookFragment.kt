package com.example.comparepharma.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.comparepharma.BuildConfig
import com.example.comparepharma.R
import com.example.comparepharma.databinding.PhoneBookFragmentBinding
import com.example.comparepharma.model.ContactState
import com.example.comparepharma.utils.DialogAlert
import com.example.comparepharma.utils.hide
import com.example.comparepharma.utils.show
import com.example.comparepharma.viewmodel.PhoneBookViewModel

class PhoneBookFragment : Fragment() {

    private var _binding: PhoneBookFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PhoneBookViewModel by lazy {
        ViewModelProvider(this).get(PhoneBookViewModel::class.java)
    }

    private val adapter: ContactAdapter by lazy {
        ContactAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PhoneBookFragmentBinding.inflate(inflater, container, false)
        binding.contactList.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.contacts.observe(viewLifecycleOwner) {
            renderData(it)
        }
        binding.buildType.text =
            String.format(getString(R.string.build_type_output), BuildConfig.MY_BUILD_TYPE)
        checkPermission()
    }

    private fun renderData(data: ContactState) {
        when (data) {
            is ContactState.Success -> {
                binding.contactList.show()
                binding.includeLoadingLayout.loadingLayout.hide()
                adapter.contacts = data.data
            }
            is ContactState.Loading -> {
                binding.contactList.hide()
                binding.includeLoadingLayout.loadingLayout.show()
            }
        }
    }

    private fun checkPermission() {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_CONTACTS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    viewModel.getContacts()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    DialogAlert(it, R.string.dialog_read_contacts_title, R.string.dialog_message)
                        .showDialogAlertWithPositiveButton(
                            R.string.dialog_give_access,
                            R.string.dialog_decline
                        ) { requestPermissionsLauncher.launch(Manifest.permission.READ_CONTACTS) }
                }
                else -> requestPermissionsLauncher.launch(Manifest.permission.READ_CONTACTS)
            }
        }
    }

    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                viewModel.getContacts()
            } else {
                context?.let {
                    DialogAlert(it, R.string.dialog_read_contacts_title, R.string.dialog_message)
                        .showBaseDialogAlert(R.string.dialog_button_close)
                }
            }
        }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}