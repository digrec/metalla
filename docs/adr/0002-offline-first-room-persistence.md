# 2. Offline-First Local Persistence with Room & KSP

Date: 2026-09-14

## Status

Accepted

## Context

Metalla tracks physical precious metal holdings (bars, coins, rounds) along with their catalog products and suppliers. To provide instant app launch speeds, offline availability, and resilience against connectivity interruptions, Metalla requires an offline-first Single Source of Truth (SSOT) architecture.

Key requirements for the local persistence layer:
1. **Compile-Time Verification:** Query syntax and schema correctness must be validated at compile time.
2. **Reactive Data Streams:** Local database changes must automatically trigger reactive updates to UI state via Kotlin Coroutines `Flow`.
3. **Data Integrity & Relational Modeling:** Foreign key relationships between items, products, and suppliers must be enforced with zero column-clashing hazards.
4. **Precision for Financial & Domain Types:** Safe, lossless storage of monetary prices (`BigDecimal`), exact timestamps (`Instant`), and typed domain enums (`MetalType`, `ProductForm`, `WeightUnit`).
5. **Architectural Purity:** Room entities, DAOs, and SQLite details must remain completely isolated within the `core/data` layer, keeping `core/domain` pure Kotlin without `android.*` imports.

## Decision

We chose **AndroidX Room** backed by the **Kotlin Symbol Processing (KSP)** plugin:

1. **Dependency Modernization (Room 2.8.x & KSP):**
   - We use `androidx.room:room-runtime:2.8.5` with the `com.google.devtools.ksp:2.3.12` annotation processor.
   - We intentionally omit `room-ktx` because starting in Room 2.7.0+, Kotlin Coroutines and `Flow` extensions are natively bundled directly into `room-runtime`.
2. **Unified Domain Repository (`PortfolioRepository`):**
   - Instead of fragmenting data access into three repetitive repositories (`ProductRepository`, `SupplierRepository`, `ItemRepository`), we consolidated operations into a single cohesive `PortfolioRepository` in `core/domain/repository/`.
   - This keeps the domain API surface concise and streamlined while maintaining clean domain models.
3. **Relational Querying without Brittle SQL JOINs:**
   - Products and items share an identical primary key column name (`id`). Standard raw SQL `JOIN` queries risk ambiguous column resolution or require verbose alias mapping.
   - We model aggregated holdings using `@Relation` in `ItemWithProductAndSupplier` combined with Room's `@Transaction` in `ItemDao`.
   - We define explicit foreign keys (`ForeignKey.RESTRICT`) with dedicated indices on `productId` and `supplierId` columns to ensure secondary relational queries remain fast and efficient.
4. **Type-Safe TypeConverters:**
   - Monetary values (`BigDecimal`) are stored losslessly as strings in SQLite (`Converters.fromBigDecimal` / `toBigDecimal`), completely avoiding binary floating-point rounding errors.
   - Timestamps are stored as epoch milliseconds (`Long`), enabling compact 8-byte SQLite `INTEGER` storage and fast, native numerical sorting (`ORDER BY creationDate DESC`).
   - Domain enums (`MetalType`, `ProductForm`, `WeightUnit`) are converted to/from strings.
5. **Bidirectional Data Layer Mappers:**
   - `ProductMapper`, `SupplierMapper`, and `ItemMapper` convert Room entities to/from pure domain models.
   - The presentation layer and domain UseCases consume only pure domain models (`Product`, `Supplier`, `Item`).

## Consequences

### Positive
- **Compile-Time SQL Safety:** Room and KSP validate SQL queries, entity schemas, and type converters during compilation.
- **True Offline-First SSOT:** The UI always observes local Room database `Flow` streams; future remote sync operations will write directly to Room.
- **Relational Integrity:** Foreign keys prevent orphaned holdings if a referenced product or supplier is deleted.
- **Financial Precision:** Guaranteed exact arithmetic and storage for prices and purchase costs.
- **Zero Domain Leakage:** The domain layer remains 100% pure Kotlin.

### Negative
- **Mapping Boilerplate:** Requires explicit bidirectional mapper functions between Room entities and domain entities.
- **Migration Maintenance:** Any future schema modifications will require structured Room migrations.
