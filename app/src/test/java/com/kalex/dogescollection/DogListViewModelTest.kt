package com.kalex.dogescollection

import com.kalex.dogescollection.common.networkstates.UseCaseFlowStatus
import com.kalex.dogescollection.common.networkstates.ViewModelNewsUiState
import com.kalex.dogescollection.dogList.model.data.alldogs.Data
import com.kalex.dogescollection.dogList.model.usecase.DogsUseCase
import com.kalex.dogescollection.dogList.presentation.viewmodel.DogsViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class DogListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var viewModel: DogsViewModel
    private val mockDogsUseCase: DogsUseCase = Mockito.mock(DogsUseCase::class.java)

    @Before
    fun setUp() {
        viewModel = DogsViewModel(mockDogsUseCase)
    }

    private fun fakeDogSuccessFlow() = flow<UseCaseFlowStatus<Data>> {
        emit(UseCaseFlowStatus.Success(data = Data(listOf())))
    }
    private fun fakeDogLoadingFlow() = flow<UseCaseFlowStatus<Data>> {
        emit(UseCaseFlowStatus.Loading("true"))
    }
    private fun fakeDogErrorFlow() = flow<UseCaseFlowStatus<Data>> {
        emit(UseCaseFlowStatus.Error(R.string.unknown_error_message))
    }
    @Test
    fun `initial dogState is Loading`() = runTest {
        assertEquals(ViewModelNewsUiState.Loading<Data>(true),viewModel.dogState.value)
    }

    @Test
    fun `download all dog list is success`() = runTest {
        Mockito.`when`(mockDogsUseCase. getAllDogs()).thenReturn(fakeDogSuccessFlow())
        viewModel.getAllDogs()
        advanceUntilIdle()
        assertEquals(ViewModelNewsUiState.Success(Data(listOf())),viewModel.dogState.value )
    }

    @Test
    fun `download all dog list is Loading`() = runTest {
        Mockito.`when`(mockDogsUseCase. getAllDogs()).thenReturn(fakeDogLoadingFlow())
        viewModel.getAllDogs()
        advanceUntilIdle()
        assertEquals(  ViewModelNewsUiState.Loading<Data>(true),viewModel.dogState.value)
    }
    @Test
    fun `download all dog list is Error`() = runTest {
        Mockito.`when`(mockDogsUseCase. getAllDogs()).thenReturn(fakeDogErrorFlow())
        viewModel.getAllDogs()
        advanceUntilIdle()
        assertEquals(  ViewModelNewsUiState.Error<Data>(R.string.unknown_error_message),viewModel.dogState.value)
    }
}