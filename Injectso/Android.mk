LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

          

LOCAL_LDLIBS += -L$(SYSROOT)/usr/lib -llog   

LOCAL_MODULE    := Injectso
LOCAL_SRC_FILES := Injectso.cpp

include $(BUILD_SHARED_LIBRARY)
