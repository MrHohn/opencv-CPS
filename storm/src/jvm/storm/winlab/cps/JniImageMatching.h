/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class storm_winlab_cps_JniImageMatching */

#ifndef _Included_storm_winlab_cps_JniImageMatching
#define _Included_storm_winlab_cps_JniImageMatching
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     storm_winlab_cps_JniImageMatching
 * Method:    initiate_imageMatching
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_storm_winlab_cps_JniImageMatching_initiate_1imageMatching
  (JNIEnv *, jclass, jstring, jstring, jstring);

/*
 * Class:     storm_winlab_cps_JniImageMatching
 * Method:    matchingIndex
 * Signature: ([BI)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_storm_winlab_cps_JniImageMatching_matchingIndex
  (JNIEnv *, jclass, jbyteArray, jint);

#ifdef __cplusplus
}
#endif
#endif
