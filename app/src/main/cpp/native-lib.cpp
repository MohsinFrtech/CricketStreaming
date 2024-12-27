#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_dream_live_cricket_score_hd_streaming_ui_activities_HomeScreen_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}