# 1. Type-Driven Error Handling (Errors as Values)

Date: 2026-09-14

## Status

Accepted

## Context

In traditional Android architectures, errors and failures are often represented by throwing unchecked exceptions. While standard in Java, propagating exceptions across architectural layer boundaries (e.g., from network clients or database drivers through the domain layer up to the presentation layer) introduces significant hazards:

1. **Hidden Failure Modes:** Function signatures like `suspend fun getHoldings(): List<Item>` do not communicate to callers that an I/O failure or database disk error can occur. Callers may fail to catch them, resulting in uncaught runtime crashes.
2. **Coroutine Cancellation Pitfalls:** In Kotlin Coroutines, cooperative cancellation relies on `CancellationException`. Catching generic `Exception` or `Throwable` carelessly can swallow `CancellationException`, preventing coroutine cancellation and causing leaks or unresponsive jobs.
3. **Impedance Mismatch with UDF:** Jetpack Compose and Unidirectional Data Flow (UDF) model UI states explicitly as immutable states (e.g., `Loading`, `Success`, `Error`). Communicating failure through thrown exceptions forces awkward try/catch blocks inside ViewModels and makes state reduction error-prone.

## Decision

We adopt the **"Errors as Values"** paradigm across Metalla:

1. **Explicit Return Types:** Suspend operations and reactive streams must return a typed result rather than throwing exceptions across boundaries:
   - One-shot suspend operations return `MetallaResult<T>`.
   - Reactive streams return `Flow<MetallaResult<T>>`.
2. **Boundary Encapsulation:** Repositories and data sources must catch operational exceptions at the boundary (Room queries, HTTP requests, serialization) and encapsulate them into `MetallaResult.Error(throwable)`.
3. **Preservation of Coroutine Cancellation:** Generic exception handlers must never swallow `CancellationException`. For suspend operations, we use a reusable `safeCall` execution pattern:
   ```kotlin
   private suspend inline fun <T> safeCall(crossinline action: suspend () -> T): MetallaResult<T> =
       try {
           MetallaResult.Success(action())
       } catch (e: CancellationException) {
           throw e
       } catch (e: Exception) {
           Timber.e(e, "Safe call failed")
           MetallaResult.Error(e)
       }
   ```
   For reactive streams, we use Flow's `.catch { emit(MetallaResult.Error(it)) }` operator, which natively preserves cancellation.
4. **Functional Composition:** ViewModels consume `MetallaResult` using functional operators (`map`, `onSuccess`, `onError`) or exhaustive `when` expressions to map results directly into UI state representations.

## Consequences

### Positive
- **Compile-Time Safety:** Callers are forced by the type system to acknowledge and handle error branches.
- **Clean UI State Mapping:** Seamlessly integrates with UDF and Compose UI state flows.
- **Lifecycle & Cancellation Safety:** Guarantees that structured concurrency and Coroutine cancellations are respected without leaks.
- **Predictable Diagnostics:** Failures are logged consistently at the boundary via Timber without crashing the application.

### Negative
- **Wrapping Overhead:** Repository implementations must wrap outputs into `MetallaResult.Success` or `MetallaResult.Error`.
- **Domain Wrapping:** Requires domain UseCases and ViewModels to unpack or transform `MetallaResult` instances.
