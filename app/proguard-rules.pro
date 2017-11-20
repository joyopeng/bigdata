# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/joyopeng/Android/Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#### -- Picasso --
-dontwarn com.squareup.picasso.**
#### -- OkHttp --
-dontwarn com.squareup.okhttp.internal.**
#### -- retrofit --
-dontwarn retrofit2.**
#### -- rxjava --
-dontwarn rx.**
-dontwarn com.baidu.**
#### -- Apache Commons --
-dontwarn org.apache.commons.logging.**
-ignorewarnings
-keep class com.baidu.** { *; }
-keep class vi.com.gdi.bgl.**{*;}
-keep class com.squareup.okhttp.**{*;}
-keep class rx.**{*;}
-keep class retrofit2.**{*;}
-keep class com.bric.kagdatabkt.entry.**{*;}

