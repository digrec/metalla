package com.digrec.metalla.core.domain

/**
 * A generic class that holds a value with its loading status.
 *
 * @param <T>
 *
 * Created by Dejan Igrec
 */
sealed class MetallaResult<out T> {

    data class Success<out T>(val data: T) : MetallaResult<T>()

    data class Error(val exception: Throwable) : MetallaResult<Nothing>()

    data object Loading : MetallaResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

/** `true` if [MetallaResult] is of type [Success] & holds non-null [Success.data]. */
val MetallaResult<*>.succeeded
    get() = this is MetallaResult.Success && data != null

fun <T> MetallaResult<T>.getOrNull(): T? {
    return when (this) {
        is MetallaResult.Success -> data
        else -> null
    }
}

fun <T> MetallaResult<T>.exceptionOrNull(): Throwable? {
    return when (this) {
        is MetallaResult.Error -> exception
        else -> null
    }
}

inline fun <T, R> MetallaResult<T>.map(transform: (T) -> R): MetallaResult<R> {
    return when (this) {
        is MetallaResult.Success -> MetallaResult.Success(transform(data))
        is MetallaResult.Error -> MetallaResult.Error(exception)
        MetallaResult.Loading -> MetallaResult.Loading
    }
}

inline fun <T, R> MetallaResult<T>.mapCatching(transform: (T) -> R): MetallaResult<R> {
    return when (this) {
        is MetallaResult.Success -> {
            try {
                MetallaResult.Success(transform(data))
            } catch (e: Exception) {
                MetallaResult.Error(e)
            }
        }
        is MetallaResult.Error -> MetallaResult.Error(exception)
        MetallaResult.Loading -> MetallaResult.Loading
    }
}

inline fun <T> MetallaResult<T>.onSuccess(action: (T) -> Unit): MetallaResult<T> {
    if (this is MetallaResult.Success) action(data)
    return this
}

inline fun <T> MetallaResult<T>.onError(action: (Throwable) -> Unit): MetallaResult<T> {
    if (this is MetallaResult.Error) action(exception)
    return this
}

inline fun <T> MetallaResult<T>.onLoading(action: () -> Unit): MetallaResult<T> {
    if (this is MetallaResult.Loading) action()
    return this
}
