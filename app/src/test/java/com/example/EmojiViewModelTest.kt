package com.example

import com.example.model.EmojiCategory
import com.example.model.EmojiRepository
import com.example.ui.EmojiViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EmojiViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state has default hello world emoji`() {
        val viewModel = EmojiViewModel()
        val state = viewModel.uiState.value

        assertEquals("👋", state.currentEmoji.emoji)
        assertEquals(0, state.totalTapCount)
        assertEquals(EmojiCategory.ALL, state.selectedCategory)
    }

    @Test
    fun `onScreenTap increases tap count and spawns particles`() {
        val viewModel = EmojiViewModel()
        viewModel.onScreenTap(100f, 200f)

        val state = viewModel.uiState.value
        assertEquals(1, state.totalTapCount)
        assertFalse(state.particles.isEmpty())
        assertNotNull(state.currentEmoji)
    }

    @Test
    fun `selectCategory filters random emojis to that category`() {
        val viewModel = EmojiViewModel()
        viewModel.selectCategory(EmojiCategory.FOOD)

        val state = viewModel.uiState.value
        assertEquals(EmojiCategory.FOOD, state.selectedCategory)
        assertEquals(EmojiCategory.FOOD, state.currentEmoji.category)
    }

    @Test
    fun `toggleSound toggles sound state`() {
        val viewModel = EmojiViewModel()
        assertTrue(viewModel.uiState.value.isSoundEnabled)
        viewModel.toggleSound()
        assertFalse(viewModel.uiState.value.isSoundEnabled)
        viewModel.toggleSound()
        assertTrue(viewModel.uiState.value.isSoundEnabled)
    }

    @Test
    fun `repository returns valid emojis for all categories`() {
        EmojiCategory.entries.forEach { category ->
            val emoji = EmojiRepository.getRandomEmoji(category)
            assertNotNull(emoji)
            if (category != EmojiCategory.ALL) {
                assertEquals(category, emoji.category)
            }
        }
    }
}
