package com.digrec.metalla.core.domain

import app.cash.turbine.test
import com.digrec.metalla.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

/** Created by Dejan Igrec */
@OptIn(ExperimentalCoroutinesApi::class)
class UseCaseTest {

    @get:Rule val mainDispatcherRule = MainDispatcherRule()

    private val testDispatcher = mainDispatcherRule.testDispatcher

    private val successUseCase =
        object : UseCase<Unit, String>(testDispatcher) {
            override suspend fun execute(parameters: Unit): String = "success"
        }

    private val errorUseCase =
        object : UseCase<Unit, String>(testDispatcher) {
            override suspend fun execute(parameters: Unit): String = throw Exception("error")
        }

    private val flowUseCase =
        object : FlowUseCase<Unit, String>(testDispatcher) {
            override fun execute(parameters: Unit): Flow<MetallaResult<String>> = flow {
                emit(MetallaResult.Loading)
                emit(MetallaResult.Success("flow success"))
            }
        }

    private val flowErrorUseCase =
        object : FlowUseCase<Unit, String>(testDispatcher) {
            override fun execute(parameters: Unit): Flow<MetallaResult<String>> = flow {
                emit(MetallaResult.Loading)
                throw Exception("flow error")
            }
        }

    @Test
    fun `UseCase should return Success on success`() = runTest {
        val result = successUseCase(Unit)
        assertTrue(result is MetallaResult.Success)
        assertEquals("success", (result as MetallaResult.Success).data)
    }

    @Test
    fun `UseCase should return Error on error`() = runTest {
        val result = errorUseCase(Unit)
        assertTrue(result is MetallaResult.Error)
        assertEquals("error", (result as MetallaResult.Error).exception.message)
    }

    @Test
    fun `FlowUseCase should emit Loading and Success`() = runTest {
        flowUseCase(Unit).test {
            assertTrue(awaitItem() is MetallaResult.Loading)
            val success = awaitItem()
            assertTrue(success is MetallaResult.Success)
            assertEquals("flow success", (success as MetallaResult.Success).data)
            awaitComplete()
        }
    }

    @Test
    fun `FlowUseCase should catch errors and emit Error`() = runTest {
        flowErrorUseCase(Unit).test {
            assertTrue(awaitItem() is MetallaResult.Loading)
            val error = awaitItem()
            assertTrue(error is MetallaResult.Error)
            assertEquals("flow error", (error as MetallaResult.Error).exception.message)
            awaitComplete()
        }
    }
}
