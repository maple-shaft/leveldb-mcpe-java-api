package org.middlepath.mcapi.generic;

import org.middlepath.leveldbmcpejni.RecordType;

public interface ByteRetriever {

	byte[] get(RecordType type, int x, int z, int yDiv, int dim);
	
}