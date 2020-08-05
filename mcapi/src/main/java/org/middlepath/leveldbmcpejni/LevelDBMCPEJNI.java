package org.middlepath.leveldbmcpejni;

import java.io.FileOutputStream;

import org.middlepath.leveldbmcpe.generic.ByteRetriever;

import java.io.File;

public class LevelDBMCPEJNI implements ByteRetriever {

	public native void print();
	public native long open(String path);
	public native byte[] get(long databasePointer, int x, int z, int y, int dim);
	public native long count(long databasePointer);
	public native void close(long databasePointer);
	
	static {
		System.loadLibrary("leveldb-mcpe-native");
	}
	
	private long databasePointer = -1;
	private String dbPath = "";
	
	public LevelDBMCPEJNI(String path) throws Exception {
		this.dbPath = path;
		this.databasePointer = open(this.dbPath);
	}
	
	@Override
	public void finalize() throws Throwable {
		super.finalize();
		this.close(this.databasePointer);
	}
	
	@Override
	public byte[] get(int x, int z, int yDiv, int dim) {
		return this.get(this.databasePointer, x, z, yDiv, dim);
	}
	
	public static void main(String[] args) throws Exception {
		LevelDBMCPEJNI j = new LevelDBMCPEJNI("");
		
		byte[] record = j.get(2, 2, 0, 0);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File("output.bin"));
			fos.write(record);
			fos.flush();
		} finally {
			if (fos != null)
				fos.close();
		}
	}
}