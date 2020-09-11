package org.middlepath.mcapi.generic;

import org.middlepath.leveldbmcpejni.RecordType;

/**
 * Instance of ByteRetriever have the implementation details for fetching and persisting chunk records.  This could be 
 * a server network connection or a native connection to a LevelDB database.
 * 
 * @author DAB
 *
 */
public interface ByteRetriever {

	byte[] get(RecordType type, int x, int z, int yDiv, int dim);
	
	int save(byte[] data, RecordType type, int x, int z, int yDiv, int dim);
	
}