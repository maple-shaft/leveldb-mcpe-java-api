package org.middlepath.leveldbmcpejni;

public interface ByteRetriever {

	byte[] get(int x, int z, int yDiv, int dim);
	
}