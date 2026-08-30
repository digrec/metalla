package com.digrec.metalla.core.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/** Created by Dejan Igrec */
class MetallaResultTest {

    @Test
    fun `Success succeeded should return true`() {
        val result = MetallaResult.Success("data")
        assertTrue(result.succeeded)
    }

    @Test
    fun `Error succeeded should return false`() {
        val result = MetallaResult.Error(Exception())
        assertFalse(result.succeeded)
    }

    @Test
    fun `Loading succeeded should return false`() {
        val result = MetallaResult.Loading
        assertFalse(result.succeeded)
    }

    @Test
    fun `getOrNull should return data on Success`() {
        val result = MetallaResult.Success("data")
        assertEquals("data", result.getOrNull())
    }

    @Test
    fun `getOrNull should return null on Error or Loading`() {
        assertNull(MetallaResult.Error(Exception()).getOrNull())
        assertNull(MetallaResult.Loading.getOrNull())
    }

    @Test
    fun `exceptionOrNull should return exception on Error`() {
        val exception = Exception("error")
        val result = MetallaResult.Error(exception)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `exceptionOrNull should return null on Success or Loading`() {
        assertNull(MetallaResult.Success("data").exceptionOrNull())
        assertNull(MetallaResult.Loading.exceptionOrNull())
    }

    @Test
    fun `map should transform data on Success`() {
        val result = MetallaResult.Success(1).map { it * 2 }
        assertTrue(result is MetallaResult.Success)
        assertEquals(2, (result as MetallaResult.Success).data)
    }

    @Test
    fun `mapCatching should catch exception on Success`() {
        val result = MetallaResult.Success(1).mapCatching { throw RuntimeException("mapped error") }
        assertTrue(result is MetallaResult.Error)
        assertEquals("mapped error", (result as MetallaResult.Error).exception.message)
    }

    @Test
    fun `onSuccess should execute action on Success`() {
        var called = false
        MetallaResult.Success("data").onSuccess { called = true }
        assertTrue(called)
    }

    @Test
    fun `onError should execute action on Error`() {
        var called = false
        MetallaResult.Error(Exception()).onError { called = true }
        assertTrue(called)
    }

    @Test
    fun `onLoading should execute action on Loading`() {
        var called = false
        MetallaResult.Loading.onLoading { called = true }
        assertTrue(called)
    }
}
