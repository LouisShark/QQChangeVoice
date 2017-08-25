#include <jni.h>
#include "fmod.hpp"
#include <stdlib.h>
#include <unistd.h>
#include <android/log.h>
#include "cn_louis_shark_qqvoice_VoiceFixer.h"

#define MODE_NORMAL 0
#define MODE_LUOLI 1
#define MODE_DASHU 2
#define MODE_JINGSONG 3
#define MODE_GAOGUAI 4
#define MODE_KONGLING 5
#define TAG "LOUIS_LOG"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
# define NELEM(x) ((int) (sizeof(x) / sizeof((x)[0])))

//hpp  既包含函数的声明又包含函数的实现
using namespace FMOD;
extern "C"
JNIEXPORT void JNICALL Java_cn_louis_shark_qqvoice_VoiceFixer_fix
        (JNIEnv *env, jclass jclazz, jstring path, jint mode) {
    //jstring -> c char *
    const char *path_cstr = env->GetStringUTFChars(path, NULL);

    System *system;
    Sound *sound;
    Channel *channel;
    bool isplaying = true;
    DSP *dsp;
    float frequency;
    //初始化操作
    System_Create(&system);
    try {
        system->init(32, FMOD_INIT_NORMAL, NULL);
        //创建声音
        system->createSound(path_cstr, FMOD_DEFAULT, NULL, &sound);
        switch (mode) {
            case MODE_NORMAL:
                //播放在子线程
                system->playSound(sound, NULL, false, &channel);
                LOGI("原声");
                break;
            case MODE_LUOLI:
                // FMOD_RESULT F_API createDSPByType (FMOD_DSP_TYPE type, DSP **dsp);
                //dsp 数字信号处理
                system->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT, &dsp);
                dsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH, 3.0);
                system->playSound(sound, NULL, false, &channel);
                channel->addDSP(0, dsp);
                LOGI("萝莉");
                break;
            case MODE_DASHU:
                system->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT, &dsp);
                dsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH, 0.8);
                system->playSound(sound, NULL, false, &channel);
                channel->addDSP(0, dsp);
                LOGI("大叔");
                break;
            case MODE_JINGSONG:
                system->createDSPByType(FMOD_DSP_TYPE_TREMOLO, &dsp);
                dsp->setParameterFloat(FMOD_DSP_TREMOLO_SKEW,0.5);
                system->playSound(sound, NULL, false, &channel);
                channel->addDSP(0, dsp);
                LOGI("惊悚");
                break;
            case MODE_GAOGUAI:
                system->playSound(sound, NULL, false, &channel);
                channel->getFrequency(&frequency);
                frequency = frequency * 1.6;
                channel->setFrequency(frequency);
                LOGI("搞怪");
                break;
            case MODE_KONGLING:
                system->createDSPByType(FMOD_DSP_TYPE_ECHO, &dsp);
                dsp->setParameterFloat(FMOD_DSP_ECHO_DELAY, 300);
                dsp->setParameterFloat(FMOD_DSP_ECHO_FEEDBACK, 30);
                system->playSound(sound, NULL, false, &channel);
                channel->addDSP(0, dsp);
                LOGI("空灵");
                break;
        }
        system->update();

    } catch (...) {  //可以捕捉到任意异常
        goto end;
    }

    //等待声音是否播放完成
    while (isplaying) {
        channel->isPlaying(&isplaying);
        usleep(1000);
    }
    end:
    system->close();
    system->release();
    env->ReleaseStringUTFChars(path, path_cstr);
}

