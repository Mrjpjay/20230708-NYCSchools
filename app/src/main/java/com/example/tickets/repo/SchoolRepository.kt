package com.example.tickets.repo

interface SchoolRepository {
    suspend fun getSchools(listener: SchoolsRepoImpl.SchoolsListener)
    suspend fun getSchoolDet(dbn: String, listener: SchoolsRepoImpl.SchoolDetailListener)
}