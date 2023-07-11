package com.example.tickets.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tickets.api.SchoolData
import com.example.tickets.repo.SchoolsRepoImpl
import com.example.tickets.repo.SchoolRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolsViewModel @Inject constructor(
    private val repository: SchoolRepository
): ViewModel(){

    private val _list = MutableStateFlow(listOf<SchoolData>())
    val list = _list.asStateFlow()

    private val _error = MutableStateFlow(false)
    val error = _error.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {
        getSchools()
    }
    fun getSchools(){
        _loading.value = true
        viewModelScope.launch {
            repository.getSchools(object : SchoolsRepoImpl.SchoolsListener{
                override fun onSuccess(list: MutableList<SchoolData>) {
                    _loading.value = false
                    _list.value = list
                    _error.value = false

                }

                override fun onError(e: String) {
                    _error.value = true
                    _loading.value = false
                }
            })
        }
    }
}