//
// Created by FrTech on 21-Jan-23.
//
#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jobjectArray JNICALL
Java_com_dream_live_cricket_score_hd_MainActivity_getStringArray1(JNIEnv *env, jobject thiz) {
    jobjectArray strarr = env->NewObjectArray(19, env->FindClass("java/lang/String"), nullptr);
    std::string vs1 = "I95JjzV16YHotJQLTw0kMD5QxKpQ5YlkQyRCyPTb";
    std::string KDa = "wnls9qbnlT4jEp2lIlLfVjtCs9PenXl7s8bL83Qy";
    std::string Ks5 = "QHivMRBVrBHkK21NPxjyibc7fFudJIaDXa5WZJbN";
    std::string Sdj = "URPnB5nYjWGEmp5tJf6pZ3YOYfnGBj0kJlzmB5Bx";
    std::string N71 = "LS6ApW9BU4s7Bq8fJ45XYxsQiyR2ZsimFIZznnba";
    std::string FPT = "i1643BjkWwx4kKA6NNgvbLPQVsHb9s5rPGZa8Ix1";
    std::string unQ = "W2erGma1DklRdCjqBU8Uzz1fZulxOQ3Hf67Cbe69";
    std::string y5 = "uC1RMH0CoAMlCb2szChxpShwwzZLtk4unfzyNMja";
    std::string ikG = "v15vlGf2PbqYvlE2155wwqfzbJB9sT3jagVfVDms";
    std::string GV9 = "rsnI9vvm105AYDAYnynT5ip8OvqV8qlS1xQ4RZ5E";
    std::string t1 = "ce079934fe9e183b811d2a376b83ef73f92be7bc";
    std::string t2 = "https://score-streaming.nospacedidlove.com/api/applications/";
    std::string t3 = "Asgjkkjhgfdswertyuimnbvcxsdfghj";
    std::string t4 = "154140573615627969442741";
    std::string t5 = "https://nodeapi.streamingsoultions.com/";
    std::string t6 = "https://cricket-data.nospacedidlove.com/api/v1/";
    std::string sPort = "ws://cricgenix.herokuapp.com/live_score";
    std::string sToken = "1h44!6t2aQr?TGbU=kaN6N3W=4Ncw0eS=obuK8q4jiLQ-U8cHERFf6Ol06G2cKYx";
    std::string sMessage = "{\"command\":\"subscribe\",\"identifier\":\"{\\\"channel\\\":\\\"LiveScoreSocketChannel\\\"}\"}";


    env->SetObjectArrayElement(strarr, 0, env->NewStringUTF(vs1.c_str()));
    env->SetObjectArrayElement(strarr, 1, env->NewStringUTF(KDa.c_str()));
    env->SetObjectArrayElement(strarr, 2, env->NewStringUTF(Ks5.c_str()));
    env->SetObjectArrayElement(strarr, 3, env->NewStringUTF(Sdj.c_str()));
    env->SetObjectArrayElement(strarr, 4, env->NewStringUTF(N71.c_str()));
    env->SetObjectArrayElement(strarr, 5, env->NewStringUTF(FPT.c_str()));
    env->SetObjectArrayElement(strarr, 6, env->NewStringUTF(unQ.c_str()));
    env->SetObjectArrayElement(strarr, 7, env->NewStringUTF(y5.c_str()));
    env->SetObjectArrayElement(strarr, 8, env->NewStringUTF(ikG.c_str()));
    env->SetObjectArrayElement(strarr, 9, env->NewStringUTF(GV9.c_str()));
    env->SetObjectArrayElement(strarr, 10, env->NewStringUTF(t1.c_str()));
    env->SetObjectArrayElement(strarr, 11, env->NewStringUTF(t2.c_str()));
    env->SetObjectArrayElement(strarr, 12, env->NewStringUTF(t3.c_str()));
    env->SetObjectArrayElement(strarr, 13, env->NewStringUTF(t4.c_str()));
    env->SetObjectArrayElement(strarr, 14, env->NewStringUTF(t5.c_str()));
    env->SetObjectArrayElement(strarr, 15, env->NewStringUTF(t6.c_str()));
    env->SetObjectArrayElement(strarr, 16, env->NewStringUTF(sPort.c_str()));
    env->SetObjectArrayElement(strarr, 17, env->NewStringUTF(sToken.c_str()));
    env->SetObjectArrayElement(strarr, 18, env->NewStringUTF(sMessage.c_str()));

    return strarr;
}