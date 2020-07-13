package org.middlepath.leveldbmcpejni;

import java.io.FileOutputStream;
import java.io.File;

public class LevelDBMCPEJNI {

	public native void print();
	public native long open(String path);
	public native byte[] get(long databasePointer, int x, int y, int z, int dim);
	public native long count(long databasePointer);
	public native void close(long databasePointer);
	
	static {
		System.loadLibrary("leveldb-mcpe-native");
	}
	
	public static void main(String[] args) throws Exception {
		LevelDBMCPEJNI j = new LevelDBMCPEJNI();
		
		j.print();
		long databasePtr = -1L;
		try {
			databasePtr = j.open("path");
			byte[] record = j.get(databasePtr, 2, 2, 0);
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(new File("output.bin"));
				fos.write(record);
				fos.flush();
			} finally {
				if (fos != null)
					fos.close();
			}
		} finally {
			j.close(databasePtr);
		}
	}
}