#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>
#include <elf.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <dlfcn.h>
#include <jni.h>
#include <errno.h>
#include <sys/mman.h>
#include <ctype.h>
#define LOG_TAG "injectso"

#define LOGE(fmt, args...) __android_log_print(ANDROID_LOG_FATAL, LOG_TAG, fmt, ##args)

#ifdef __cplusplus
extern "C" {
#endif
int hook_entry(char * a);
#ifdef __cplusplus
}
#endif

typedef JNIEnv* (*GETENV)();

void loadJavaFun() {
	void *handle = dlopen("/system/lib/libandroid_runtime.so", 0);
	if (handle == NULL) {

		LOGE("dlopen error: %s(errno: %d)\n", strerror(errno), errno);
		return;
	}
	LOGE("handle: %08X\n", handle);

	GETENV getEnv = (GETENV) dlsym(handle,
			"_ZN7android14AndroidRuntime9getJNIEnvEv");

	if (getEnv == NULL) {
		LOGE("dlsym error: %s(errno: %d)\n", strerror(errno), errno);
		return;
	}
	LOGE("getEnv function address: %08X\n", getEnv);

	JNIEnv *env = getEnv();

	if (env == NULL) {
		LOGE("getJNIEnv error: %s(errno: %d)\n", strerror(errno), errno);
		return ;
	}

	LOGE("env: %08X\n", env);

	jclass stringClass, classLoaderClass, dexClassLoaderClass, targetClass;
	jmethodID getSystemClassLoaderMethod, dexClassLoaderContructor,
			loadClassMethod, targetMethod;
	jobject systemClassLoaderObject, dexClassLoaderObject;
	jstring dexPathString, dexOptDirString, classNameString, tmpString;
	jobjectArray stringArray;

	stringClass = env->FindClass("java/lang/String");
	classLoaderClass = env->FindClass("java/lang/ClassLoader");
	dexClassLoaderClass = env->FindClass("dalvik/system/PathClassLoader");
	getSystemClassLoaderMethod = env->GetStaticMethodID(classLoaderClass,
			"getSystemClassLoader", "()Ljava/lang/ClassLoader;");
	systemClassLoaderObject = env->CallStaticObjectMethod(classLoaderClass,
			getSystemClassLoaderMethod);

	LOGE("systemClassLoaderObject: %08X\n", systemClassLoaderObject);

	dexClassLoaderContructor =
			env->GetMethodID(dexClassLoaderClass, "<init>",
					"(Ljava/lang/String;Ljava/lang/String;Ljava/lang/ClassLoader;)V");
	dexPathString = env->NewStringUTF(
			"/data/data/com.example.hooktest/JavaHook.apk");
	dexOptDirString = env->NewStringUTF(
			"/data/data/com.example.hooktest/");
	dexClassLoaderObject = env->NewObject(dexClassLoaderClass,
			dexClassLoaderContructor, dexPathString, dexOptDirString, systemClassLoaderObject);

	loadClassMethod = env->GetMethodID(dexClassLoaderClass, "loadClass",
			"(Ljava/lang/String;)Ljava/lang/Class;");

	classNameString = env->NewStringUTF("com/example/javahook/MainActivity");

	LOGE("dexClassLoaderObject: %08X\n", dexClassLoaderObject);
	LOGE("loadClassMethod: %08X\n", loadClassMethod);

	targetClass = (jclass) env->CallObjectMethod(dexClassLoaderObject,
			loadClassMethod, classNameString);

	LOGE("targetClass: %08X\n", targetClass);

	if (targetClass != NULL) {
		LOGE("find targetClass");
		targetMethod = env->GetStaticMethodID(targetClass, "HookTest", "()I");
		LOGE("targetMethod: %08X\n", targetMethod);
		if (targetMethod != NULL) {
			LOGE("find targetMethod");
			env->CallStaticIntMethod(targetClass, targetMethod);
		}
	}
}

int hook_entry(char * a) {

	LOGE("Hook success, pid = %d\n", getpid());
	LOGE("Hello %s\n", a);
	loadJavaFun();
	return 0;
}
