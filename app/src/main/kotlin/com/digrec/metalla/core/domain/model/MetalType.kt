package com.digrec.metalla.core.domain.model

/**
 * Precious metal type.
 *
 * See docs/GLOSSARY.md for definitions.
 */
enum class MetalType(val symbol: String) {
    GOLD("Au"),
    SILVER("Ag"),
    PLATINUM("Pt"),
    PALLADIUM("Pd"),
}
