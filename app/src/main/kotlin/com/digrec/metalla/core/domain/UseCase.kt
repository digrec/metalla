package com.digrec.metalla.core.domain

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

/**
 * Base class for Use Cases that return a [Flow] of [MetallaResult].
 *
 * Created by Dejan Igrec
 */
abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    operator fun invoke(parameters: P): Flow<MetallaResult<R>> =
        execute(parameters).catch { e -> emit(MetallaResult.Error(e)) }.flowOn(coroutineDispatcher)

    protected abstract fun execute(parameters: P): Flow<MetallaResult<R>>
}

/**
 * Base class for Use Cases that return a [MetallaResult].
 *
 * Created by Dejan Igrec
 */
abstract class UseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(parameters: P): MetallaResult<R> {
        return try {
            withContext(coroutineDispatcher) {
                execute(parameters).let { MetallaResult.Success(it) }
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            MetallaResult.Error(e)
        }
    }

    @Throws(RuntimeException::class) protected abstract suspend fun execute(parameters: P): R
}
