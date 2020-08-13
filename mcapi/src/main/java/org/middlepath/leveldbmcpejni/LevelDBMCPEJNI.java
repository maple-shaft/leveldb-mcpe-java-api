package org.middlepath.leveldbmcpejni;

import java.io.FileOutputStream;

import org.middlepath.mcapi.generic.ByteRetriever;

import java.io.File;

public class LevelDBMCPEJNI implements ByteRetriever {

	public native void print();
	public native long open(String path);
	public native byte[] get(long databasePointer, int x, int z, int y, int dim);
	public native long count(long databasePointer);
	public native void close(long databasePointer);
	
	static {
		System.loadLibrary("leveldb");
		//System.load("C:/Users/Dustin/workspace/leveldb-mcpe/out-shared/libleveldb.so.1.20");
		System.loadLibrary("leveldb-mcpe-native");
		//System.load("C:/Users/Dustin/workspace/leveldb-mcpe/out-shared/libleveldb.so.1.20");
	}
	
	private long databasePointer = -1;
	private String dbPath = "";
	
	public LevelDBMCPEJNI(String path) throws Exception {
		this.dbPath = path;
		System.out.println("!+!+!+!+!+! Database path is " + path);
		this.databasePointer = open(this.dbPath);
		System.out.println("!+!+!+!+!+!+! Database pointer is " + this.databasePointer);
	}
	
	@Override
	public void finalize() throws Throwable {
		super.finalize();
		this.close(this.databasePointer);
	}
	
	@Override
	public byte[] get(int x, int z, int yDiv, int dim) {
		System.out.println("!+!+!+!+!+!+!+! Sanity check, db pointer " + this.databasePointer);
		System.out.println(x + " " + z + " " + yDiv + " " + dim);
		byte[] ret = this.get(this.databasePointer, x, z, yDiv, dim);
		System.out.println("Return value: " + ret[0] + " " + ret[1]);
		return ret;
	}
	
	public static void main(String[] args) throws Exception {
		LevelDBMCPEJNI j = new LevelDBMCPEJNI("C:\\Users\\Dustin\\workspace\\thdVXmy-AAA=\\db");
		
		byte[] record = j.get(0, 0, 0, 0);
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