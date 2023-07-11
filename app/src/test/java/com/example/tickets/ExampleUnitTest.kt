package com.example.tickets

import com.example.tickets.api.SchoolData
import com.example.tickets.repo.SchoolRepository
import com.example.tickets.repo.SchoolsRepoImpl
import com.example.tickets.viewmodel.SchoolsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import org.mockito.kotlin.any

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private lateinit var viewModel: SchoolsViewModel
    private val repository = mock(SchoolRepository::class.java)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = TestCoroutineDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SchoolsViewModel(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun getSchools_success() = runBlocking{

        val schools = mutableListOf<SchoolData>()
        doAnswer { invocation ->
            val callback = invocation.getArgument<SchoolsRepoImpl.SchoolsListener>(0)
            callback.onSuccess(schools)
            null
        }.`when`(repository).getSchools(any())

        viewModel.getSchools()

        Assert.assertFalse(viewModel.loading.value)
        Assert.assertEquals(schools, viewModel.list.value)
        Assert.assertFalse(viewModel.error.value)
    }
}