# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\android\sdk/tools/proguard/proguard-android.txt
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


-ignorewarnings

##使用注解需要添加
-keepattributes *Annotation*
##指定不混淆所有的JNI方法
-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers public class * extends android.view.View {###所有View的子类及其子类的get、set方法都不进行混淆
   void set*(***);
   *** get*();
}
-keepclassmembers class * extends android.app.Activity {##不混淆Activity中参数类型为View的所有方法
   public void *(android.view.View);
}
-keepclassmembers class * extends android.support.v4.app.Fragment {##不混淆Activity中参数类型为View的所有方法
   public void *(android.view.View);
}


-keepclassmembers enum * {##不混淆Enum类型的指定方法
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

##不混淆Parcelable和它的子类，还有Creator成员变量
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
##不混淆R类里及其所有内部static类中的所有static变量字段
-keepclassmembers class **.R$* {
    public static <fields>;
}
##如果用到了反射需要加入
-keepattributes Signature
-keepattributes EnclosingMethod
-keep public class * extends com.lidroid.xutils.**

-keep class com.lu.takeaway.bean.**{*;}##不混淆所有的com.czy.bean包下的类和这些类的所有成员变量


    ##不混淆Serializable接口的子类中指定的某些成员变量和方法
    -keepclassmembers class * implements java.io.Serializable {
        static final long serialVersionUID;
        private static final java.io.ObjectStreamField[] serialPersistentFields;
        private void writeObject(java.io.ObjectOutputStream);
        private void readObject(java.io.ObjectInputStream);
        java.lang.Object writeReplace();
        java.lang.Object readResolve();
    }
#-libraryjars libs/alipaySDK-20150818.jar
##-libraryjars libs/gson-2.2.4.jar
#-libraryjars libs/xUtils-2.6.14.jar
#-libraryjars com.nineoldandroids:library:2.4.0
#-libraryjars com.android.support:appcompat-v7:23.2.1
#-libraryjars com.android.support:design:23.2.1

################支付宝##################
#-libraryjars libs/alipaysecsdk.jar
#-libraryjars libs/alipayutdid.jar
#-libraryjars libs/alipaysdk.jar
-keep class com.alipay.android.app.IAliPay{*;}
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.lib.ResourceMap{*;}
################gson##################
##-libraryjars libs/gson-2.2.4.jar
-keep class com.google.gson.** {*;}
#-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.google.** {
    <fields>;
    <methods>;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-dontwarn com.google.gson.**
##----------------------------------bmob class start- -------------------------
# 这里根据具体的SDK版本修改

#-libraryjars libs/bmob_v3.2.0.jar
#-libraryjars libs/Bmob_Push_V0.8beta_20160315.jar
#-libraryjars libs/BmobSMS_V1.0.1_20150710.jar


-keepattributes Signature
-keep class cn.bmob.v3.** {*;}

# 保证继承自BmobObject、BmobUser类的JavaBean不被混淆
-keep class com.example.bmobexample.bean.BankCard{*;}
-keep class com.example.bmobexample.bean.GameScore{*;}
-keep class com.example.bmobexample.bean.MyUser{*;}
-keep class com.example.bmobexample.bean.Person{*;}

-keep class com.example.bmobexample.file.Movie{*;}
-keep class com.example.bmobexample.file.Song{*;}

-keep class com.example.bmobexample.relation.Post{*;}
-keep class com.example.bmobexample.relation.Comment{*;}

# 如果你使用了okhttp、okio的包，请添加以下混淆代码
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *;}
-keep interface com.squareup.okhttp.** { *; }
-dontwarn okio.**

##----------------------------------bmob class end- -------------------------