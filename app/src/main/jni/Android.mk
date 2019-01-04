LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := jni-utils-lib
LOCAL_SRC_FILES := jniutils.cpp
include $(BUILD_SHARED_LIBRARY)
