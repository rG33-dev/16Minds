# Lottie ProGuard Rules
-keep class com.airbnb.lottie.** { *; }
-keep interface com.airbnb.lottie.** { *; }

# Keep Compose Internals
-keep class androidx.compose.ui.platform.** { *; }
-keep class androidx.compose.runtime.** { *; }

# Keep ViewModel
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    public <init>(...);
}

# Preserve line numbers for crash reporting
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Fix for missing javax.annotation.Nullable
-dontwarn javax.annotation.**
