package com.vai.vaidebet.vaibrazil.presentation.screens.game

import androidx.lifecycle.SavedStateHandle
import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import com.vai.vaidebet.vaibrazil.domain.usecase.GetLevelUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GameViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getLevelUseCase: GetLevelUseCase
    private lateinit var viewModel: GameViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test level loading success`() = runTest {
        // Given
        val level = GameLevel(1, emptyList(), 10)
        getLevelUseCase = object : GetLevelUseCase(null) {
            override suspend fun invoke(id: Int): GameLevel? {
                return level
            }
        }
        val savedStateHandle = SavedStateHandle().apply { set("levelId", 1) }

        // When
        viewModel = GameViewModel(getLevelUseCase, savedStateHandle)

        // Then
        val state = viewModel.uiState.value
        assertEquals(GameUiState.Success(level, 0, true), state)
    }

    @Test
    fun `test level loading error`() = runTest {
        // Given
        getLevelUseCase = object : GetLevelUseCase(null) {
            override suspend fun invoke(id: Int): GameLevel? {
                return null
            }
        }
        val savedStateHandle = SavedStateHandle().apply { set("levelId", 1) }

        // When
        viewModel = GameViewModel(getLevelUseCase, savedStateHandle)

        // Then
        val state = viewModel.uiState.value
        assertEquals(GameUiState.Error("Level not found"), state)
    }
}
