package com.example.tickets.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tickets.api.SchoolDetailData
import com.example.tickets.repo.SchoolRepository
import com.example.tickets.repo.SchoolsRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolDetViewModel @Inject constructor(
    private val repo: SchoolRepository
) : ViewModel() {

    private val _noSchool = MutableStateFlow(false)
    val noSchool = _noSchool.asStateFlow()

    private val _error = MutableStateFlow(false)
    val error = _error.asStateFlow()

    private val _schoolDetail = MutableStateFlow(SchoolDetailData())
    val schoolDetail = _schoolDetail.asStateFlow()

    fun getSchoolDetail(dbn: String) {
        viewModelScope.launch {
            repo.getSchoolDet(dbn,
                object : SchoolsRepoImpl.SchoolDetailListener {
                    override fun onDetails(detail: SchoolDetailData) {
                        _error.value = false
                        _schoolDetail.value = detail
                    }

                    override fun onError(e: String) {
                        _error.value = true
                    }

                    override fun empty(b: Boolean) {
                        _noSchool.value = true
                        _error.value = false
                    }
                })
        }
    }
}