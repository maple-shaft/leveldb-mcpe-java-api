#include <stdint.h>
#include <cstdint>
#include <jni.h>
#include <stdio.h>
#include <leveldb/db.h>
#include "DB.h"

#ifndef LEVELDB_MCPE_JNI
#define LEVELDB_MCPE_JNI

#ifdef __cplusplus
extern "C" {
#endif

jbyteArray as_byte_array(JNIEnv *env, unsigned char* buf, int len) {
	jbyteArray array = env->NewByteArray(len);
	env->SetByteArrayRegion(array, 0, len, reinterpret_cast<jbyte*>(buf));
	return array;
}

unsigned char* as_unsigned_char_array(JNIEnv *env, jbyteArray array) {
	int len = env->GetArrayLength(array);
	unsigned char* buf = new unsigned char[len];
	env->GetByteArrayRegion(array, 0, len, reinterpret_cast<jbyte*>(buf));
	return buf;
}

JNIEXPORT jlong JNICALL
Java_org_middlepath_leveldbmcpejni_LevelDBMCPEJNI_open(JNIEnv *env, jobject thiz, jstring dbpath)
{
		const char *path = env->GetStringUTFChars(dbpath, 0);
		leveldbmcpenative::DB *db = new leveldbmcpenative::DB(path);
		int ret = db->Open();
		printf("Database opened with return code %d", ret);
		env->ReleaseStringUTFChars(dbpath, path);
		jlong retV = reinterpret_cast<jlong>(db);
		return retV;
}

JNIEXPORT jlong JNICALL
Java_org_middlepath_leveldbmcpejni_LevelDBMCPEJNI_count(JNIEnv *env, jobject thiz, jlong ptr)
{
	leveldbmcpenative::DB *database = reinterpret_cast<leveldbmcpenative::DB*>(ptr);
	long recordCount = database->CountRecords();
	return reinterpret_cast<jlong>(recordCount);
}

JNIEXPORT jbyteArray JNICALL
Java_org_middlepath_leveldbmcpejni_LevelDBMCPEJNI_get(JNIEnv *env, jobject thiz, jlong ptr, jint x, jint z, jint y, jint dim)
{
	leveldbmcpenative::DB *database = reinterpret_cast<leveldbmcpenative::DB*>(ptr);
	mapkey_t key = LDBKEY_STRUCT(static_cast<int32_t>(x), static_cast<int32_t>(z), static_cast<int32_t>(dim));
	std::string ret = database->Get(key);
	unsigned char * retC = const_cast<unsigned char*>(reinterpret_cast<unsigned const char *>(ret.data()));
	return as_byte_array(env, retC, ret.length());
}

JNIEXPORT void JNICALL
Java_org_middlepath_leveldbmcpejni_LevelDBMCPEJNI_close(JNIEnv *env, jobject thiz, jlong ptr)
{
	leveldbmcpenative::DB *database = reinterpret_cast<leveldbmcpenative::DB*>(ptr);
	if (database)
	{
		delete database;
	}
	return;
}

JNIEXPORT void JNICALL
Java_org_middlepath_leveldbmcpejni_LevelDBMCPEJNI_print(JNIEnv *env, jobject obj)
{
	leveldbmcpenative::DB *db = new leveldbmcpenative::DB("Hellow WORLD!!\n");
	db->PrintDB();
	return;
}

#ifdef __cplusplus
}
#endif
#endif


	

