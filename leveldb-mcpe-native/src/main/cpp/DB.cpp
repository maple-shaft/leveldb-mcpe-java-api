#include <stdio.h>
#include "DB.h"

#ifndef LEVELDB_MCPE_NATIVE_DB
#define LEVELDB_MCPE_NATIVE_DB

void leveldbmcpenative::NullLogger::Logv(const char *, va_list) {}

leveldbmcpenative::DB::DB(const char *p)
{
	readOptions.decompress_allocator = new leveldb::DecompressAllocator();
	database = nullptr;
	this->path = new char[strlen(p) + 1];
	strcpy(this->path, p);
}

leveldbmcpenative::DB::~DB()
{
	if (path) { delete path; }
	if (database) { delete database; }
}

void leveldbmcpenative::DB::PrintDB()
{
	printf("Hello WORLD!!!\n");
	return;
}

int leveldbmcpenative::DB::Open()
{
	if (database) { return 0; }
	leveldb::Options options;
	options.filter_policy = leveldb::NewBloomFilterPolicy(10);
	options.block_cache = leveldb::NewLRUCache(40 * 1024 * 1024);
	options.write_buffer_size = 4 * 1024 * 1024;
	options.info_log = new leveldbmcpenative::NullLogger();
	options.compressors[0] = new leveldb::ZlibCompressorRaw(-1);
	options.compressors[1] = new leveldb::ZlibCompressor();
	options.create_if_missing = false;
	
	status = leveldb::DB::Open(options, path, &database);
	if (!status.ok()) return status.code();
	
	return 0;
}

long leveldbmcpenative::DB::CountRecords()
{
	if (database)
	{
		leveldb::Iterator *iterator = database->NewIterator(readOptions);
		long retVal = 0;
		iterator->SeekToFirst();
		while (iterator->Valid())
		{
			leveldb::Slice keySlice = iterator->key();
			leveldb::Slice valueSlice = iterator->value();
			const char *kch = keySlice.ToString().c_str();
			printf("Key Text: %s\n", kch);
			retVal++;
			iterator->Next();
		}
		return retVal;
	}
	else
	{
		return -1;
	}
}

std::string leveldbmcpenative::DB::GetBlockEntityChunk(mapkey_t k, int yDiv)
{
	if (database)
	{
		LDBKEY_BLOCKENTITY(k, yDiv);
		std::string str;
		char ver;
		leveldb::Slice sl = leveldb::Slice(key, k.dimension == 0 ? 10 : 14);
		status = database->Get(readOptions, sl, &str);
		return str;
	}
	return "-1";
}

std::string leveldbmcpenative::DB::GetSubChunk(mapkey_t k, int yDiv)
{
	if (database)
	{
		LDBKEY_SUBCHUNK(k, yDiv);
		std::string str;
		leveldb::Slice sl = leveldb::Slice(key, k.dimension == 0 ? 10 : 14);
		status = database->Get(readOptions, sl, &str);
		if (!status.ok())
		{
			return "-1";
		} else {
			return str;
		}
	}
	return "-1";
}

void leveldbmcpenative::DB::Close()
{
	if (database) { delete database; }
}

#endif
	