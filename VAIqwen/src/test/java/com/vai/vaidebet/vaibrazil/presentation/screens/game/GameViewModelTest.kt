package com.vai.vaidebet.vaibrazil.presentation.screens.game

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.vai.vaidebet.vaibrazil.domain.model.Block
import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import com.vai.vaidebet.vaibrazil.domain.model.Orientation
import com.vai.vaidebet.vaibrazil.presentation.GameUiState
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GameViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: GameViewModel

    @Mock
    private lateinit var uiStateObserver: Observer<GameUiState>

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be loading`() = runTest {
        // Arrange
        val levelId = 1
        
        // Act
        viewModel = GameViewModel(levelId)
        
        // Assert
        assertEquals(GameUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `moveBlock should update block position`() = runTest {
        // Arrange
        val levelId = 1
        viewModel = GameViewModel(levelId)
        
        val testLevel = GameLevel(
            id = 1,
            name = "Test Level",
            blocks = listOf(
                Block(1, 2, 0, 2, Orientation.HORIZONTAL, true),
                Block(2, 0, 0, 2, Orientation.VERTICAL)
            ),
            minMoves = 10
        )
        
        // Устанавливаем тестовый уровень
        viewModel.uiState.value = GameUiState.Success(
            level = testLevel,
            blocks = testLevel.blocks.toMutableList(),
            moves = 0
        )
        
        // Act
        viewModel.moveBlock(2, 1, 0)
        
        // Assert
        val currentState = viewModel.uiState.value
        assert(currentState is GameUiState.Success)
        if (currentState is GameUiState.Success) {
            val movedBlock = currentState.blocks.find { it.id == 2 }
            assert(movedBlock != null)
            assertEquals(1, movedBlock?.row)
            assertEquals(0, movedBlock?.col)
            assertEquals(1, currentState.moves)
        }
    }
}