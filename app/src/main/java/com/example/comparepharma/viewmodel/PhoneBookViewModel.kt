package com.example.comparepharma.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.comparepharma.model.ContactState
import com.example.comparepharma.model.repository.ContactRepository
import com.example.comparepharma.model.repository.ContactRepositoryImpl

class PhoneBookViewModel(
    private val repository: ContactRepository =
        ContactRepositoryImpl()
) : ViewModel() {
    val contacts: MutableLiveData<ContactState> = MutableLiveData()

    fun getContacts(){
        contacts.value = ContactState.Loading
        val answer = repository.getListOfContact()
        contacts.value = ContactState.Success(answer)
    }

}
