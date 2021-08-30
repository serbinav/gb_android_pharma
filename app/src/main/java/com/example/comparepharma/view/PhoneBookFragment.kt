package com.example.comparepharma.view

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.comparepharma.R
import com.example.comparepharma.databinding.PhoneBookFragmentBinding
import com.example.comparepharma.model.ContactState
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
    ): View? {
        _binding = PhoneBookFragmentBinding.inflate(inflater, container, false)
        binding.contactList.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.contacts.observe(viewLifecycleOwner) {
            renderData(it)
        }
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
                    getContacts()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    AlertDialog.Builder(it)
                        .setTitle(R.string.dialog_read_contacts_title)
                        .setMessage(R.string.dialog_message)
                        .setPositiveButton(R.string.dialog_give_access) { _, _ ->
                            requestPermissionsLauncher.launch(Manifest.permission.READ_CONTACTS)
                        }
                        .setNegativeButton(R.string.dialog_decline) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()
                        .show()
                }
                else -> requestPermissionsLauncher.launch(Manifest.permission.READ_CONTACTS)
            }
        }
    }

    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                getContacts()
            } else {
                context.let {
                    AlertDialog.Builder(it)
                        .setTitle(R.string.dialog_read_contacts_title)
                        .setMessage(R.string.dialog_message)
                        .setNegativeButton(R.string.dialog_button_close) { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
            }
        }

    private fun getContacts() {
        viewModel.getContacts()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PhoneBookFragment()
    }
}