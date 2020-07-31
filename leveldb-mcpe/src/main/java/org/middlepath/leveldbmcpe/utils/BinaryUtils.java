package org.middlepath.leveldbmcpe.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.io.ByteArrayOutputStream;

import org.middlepath.leveldbmcpe.generic.BedrockSerializable;

public class BinaryUtils {

	public static byte[] getRawBytes(InputStream is) throws IOException {
		ByteArrayOutputStream buffer = null;
		try {
			buffer = new ByteArrayOutputStream();
			int nRead;
			byte[] data = new byte[1024];
			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}
			buffer.flush();
			return buffer.toByteArray();
		} finally {
			if (buffer != null)
				buffer.close();
		}
	}
	
	public static void concat(ByteArrayOutputStream acccumulator, Iterator<? extends BedrockSerializable> it) {
		try {
			while (it.hasNext()) {
				accumulator.write(it.next().write());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static byte[] convertShortToBytesLittleEndian(short s) {
		byte[] ret = new byte[2];
		ret[1] = (byte)((s >>> 8) & 0xFF);
		ret[0] = (byte)(s & 0xFF);
		return ret;
	}
	
	public static byte[] convertIntToBytesBigEndian(int i) {
		byte[] ret = new byte[4];
		ret[0] = (byte)((i >>> 24) & 0xFF);
		ret[1] = (byte)((i >>> 16) & 0xFF);
		ret[2] = (byte)((i >>> 8) & 0xFF);
		ret[3] = (byte)(i & 0xFF);
		return ret;
	}
	
	public static byte[] convertIntToBytesLittleEndian(int i) {
		byte[] ret = new byte[4];
		ret[3] = (byte)((i >>> 24) & 0xFF);
		ret[2] = (byte)((i >>> 16) & 0xFF);
		ret[1] = (byte)((i >>> 8) & 0xFF);
		ret[0] = (byte)(i & 0xFF);
		return ret;
	}
	
	public static byte[] convertIntToBytesLittleEndian(Integer i) {
		return convertIntToBytesLittleEndian(i.intValue());
	}
	
	public static byte[] convertIntToBytesLittleEndian(Long l) {
		int con = (int)(l & 0xFFFFFFFFL);
		return convertIntToBytesLittleEndian(con);
	}
	
	/**
	 * Takes 4 bytes and adds them to a 32 bit integer in little endian
	 */
	public static int bytesToInt(byte b1, byte b2, byte b3, byte b4) {
		int ret = b4;
		ret |= (b3) << 8;
		ret |= (b2) << 16;
		ret |= (b1) << 24;
		return ret;
	}
	
	//TODO: This is wrong, refactor
	public static short bytesToShort(byte b1, byte b2) {
		return (short)(b2 | (b1 << 8));
	}
	
	public static short bytesToShortBigEndian(byte b1, byte b2) {
		return (short)(b1 | (b2 << 8));
	}
	
	public static long bytesToIntBigEndian(byte b1, byte b2, byte b3, byte b4) {
		return ((b1 & 0xFF) |
				((b2 & 0xFF) << 8) |
				((b3 & 0xFF) << 16) |
				((b4 & 0xFF) << 24)) & 0xFFFFFFFFL;
	}
	
	public static String printBitString(Integer num) {
		StringBuffer sb = new StringBuffer("Decimal: ");
		sb.append(num);
		sb.append(" ");
		Deque<Integer> stack = new ArrayDeque<Integer>();
		printStackInt(num, stack);
		stack.forEach(sb::append);
		return sb.toString();
	}
	
	private static void printStackInt(int num, Deque<Integer> stack) {
		final long mask = 1;
		for (int i = 1; i <= 32; i++) {
			stack.push((int)(num & mask));
			num >>>= 1;
		}
	}
}