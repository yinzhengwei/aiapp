# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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

#    -keep	防止该类所有内容被移除或重命名
#    -keepnames	防止类和成员被重命名
#    -keepclassmembers	防止成员被移除或者被重命名
#    -keepclasseswithmembers	防止拥有该成员的类和成员被移除或者被重命名
#    -keepclasseswithmembernames	防止拥有该成员的类和成员被重命名
#
#    不混淆某个类	-keep public class packageName.className{ *; }
#    不混淆某个包的所有类	-keep class packageName.**{ *; }
#    不混淆某个类的子类	-keep public class * extends packageName.className{ *; }
#    不混淆某个接口的子类	-keep public class * implements packageName.className{ *; }
#    不混淆某个类的构造方法	-keepclassmembers class packageName.className{ public <init style="box-sizing: border-box;">(); }</init>
#    不混淆某个类的某个方法	-keepclassmembers class packageName.className{ public void methodName(…); }
#    不混淆某个类的内部类	-keep class packageName.className$*{ *; }

#---------这里提供一份这个lib中最好不要混淆的地方，前边的配置都差不多，主要是第三方包以及其他不需要混淆的代码----
#---------------------------------基本指令以及一些固定不混淆的代码--开始--------------------------------
#<基本指令>
#代码混淆压缩笔记，在0~7之间
-optimizationpasses 5
#不去忽略非公共的库的类
-dontskipnonpubliclibraryclassmembers
#混淆后类名都小写
-dontskipnonpubliclibraryclasses
#生成原类名和混淆后的类名的映射文件
-optimizations !code/simplification/cast,!field/*,!class/merging/*
#内部类不混淆
-keepattributes *Annotation*,InnerClasses
#注解不混淆
-keepattributes Signature
#泛型不混淆
-keepattributes SourceFile,LineNumberTable
#忽略警告
-ignorewarnings
#记录生成的日志数据,gradle build时在本项目根目录输出apk 包内所有 class 的内部结构
-dump class_files.txt
#未混淆的类和成员
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt
#混淆前后的映射
-printmapping mapping.txt
#</基本指令>

#打印混淆的详细信息
-verbose
 #不进行优化，建议使用此选项，
-dontoptimize
 #不进行预校验,Android不需要,可加快混淆速度。
-dontpreverify

#<基础>
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.support.multidex.MultiDexApplication*
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService*
-keep class android.support.** {*;}
#</基础>

#<view相关>
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * {
   public void *(android.view.View);
}
#</view相关>

#<Serializable、Parcelable>
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep public class * implements java.io.Serializable {*;}

-keep public class * implements android.os.Parcelable {*;}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#</Serializable、Parcelable>

#3.webview
-keepclassmembers class fqcn.of.javascript.interface.for.webview* {
   public *;
}
-keepclassmembers class * extends android.webkit.webViewClient* {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient* {
    public void *(android.webkit.webView*, jav.lang.String*);
}

#<R文件>
-keep class **.R$* {
 *;
}
#</R文件>

#<enum>
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#</enum>

#<natvie>
-keepclasseswithmembernames class * {
    native <methods>;
}
#</natvie>

#Log
-assumenosideeffects class android.util.Log {
    public static *** v(...);
    public static *** d(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}

# Keep the BuildConfig
-keep class com.aiforward.guardian.BuildConfig* { *; }


#---------------------------------基本指令以及一些固定不混淆的代码--结束-----------

#---------------------------------第三方包--开始-------------------------------

#<okhttp3.x>
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**
#</okhttp3.x>

#<retrofit2.x>
-keep class retrofit2.**{ *; }
-dontnote retrofit2.Platform
#-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions
-dontwarn okio.**
#</retrofit2.x>

#<eventbus 3.0>
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
#</eventbus 3.0>

#<Gson>
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe.** { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
#</Gson>

#<glide>
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#</glide>

#<Rxjava RxAndroid>
-dontwarn rx.*
-dontwarn sun.misc.**

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef* {
    rx.internal.util.atomic.LinkedQueueNode* producerNode;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef* {
    rx.internal.util.atomic.LinkedQueueNode* consumerNode;
}
#</Rxjava RxAndroid>

# ARouter
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

# 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

# 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
-keep class * implements com.alibaba.android.arouter.facade.template.IProvider

#baseQuickAdapter
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}


#保留自定义的xlog文件夹下面的类、类成员和方法不被混淆
-keep class com.text.xlog.** {<fields>;<methods>;
}

# coroutines协程管理类
#----------------------------------第三方包--结束--------------------------



#---------------------------------一些不要混淆的代码--开始-------------------
#<自定义View的类>
#-keep class net.arvin.afbaselibrary.ui.views.** {*;}
#</自定义View的类>

#---------------------------------一些不要混淆的代码--结束-------------------

