#ifndef LEVELDB_MCPE_NATIVE_DB_H
#define LEVELDB_MCPE_NATIVE_DB_H

#include <stdio.h>
#include <leveldb/db.h>
#include <leveldb/options.h>
#include <leveldb/decompress_allocator.h>
#include <leveldb/zlib_compressor.h>
#include <leveldb/filter_policy.h>
#include <leveldb/cache.h>
#include <leveldb/status.h>
#include <leveldb/env.h>
#include "mapkey.h"

namespace leveldbmcpenative {

	class NullLogger : public leveldb::Logger {
		public:
			void Logv(const char *, va_list) override;
	};
	
#define CHUNK_LI_CLEAN 0b0
#define CHUNK_LI_DIRTY 0b1
#define VERSION_POCKET 4
#define VERSION_BEDROCK 7

	typedef unsigned char byte;
	
	class DB
	{
		private:
			char *path;
			leveldb::DB *database;
			leveldb::ReadOptions readOptions;
			leveldb::Status status;
			//version 4 for pocket, 7 for bedrock
			char version;
			uint8_t subver;
		public:
			DB(const char *p);
			~DB();
			int Open();
			std::string Get(mapkey_t key);
			void Close();
			long CountRecords();
			void PrintDB();
	};
}

#endif
			
