# ==============================================================================
# Metalla Release & R8 Optimization ProGuard Rules
# ==============================================================================

# ------------------------------------------------------------------------------
# Stack Traces & Debugging
# Preserve line numbers and source files for readable stack traces in crash reports.
# ------------------------------------------------------------------------------
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# ------------------------------------------------------------------------------
# Kotlin Coroutines & Flow
# ------------------------------------------------------------------------------
-keepclassmembers class kotlinx.coroutines.android.AndroidDispatcherFactory {
    public <init>();
}

# ------------------------------------------------------------------------------
# Timber Logging Optimization
# Strip verbose and debug log calls (including tagged logging on Trees) in release builds.
# ------------------------------------------------------------------------------
-assumenosideeffects class timber.log.Timber {
    public static *** v(...);
    public static *** d(...);
}
-assumenosideeffects class timber.log.Timber$Tree {
    public *** v(...);
    public *** d(...);
}
