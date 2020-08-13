package org.middlepath.mcapi;

import org.junit.Test;
import org.middlepath.leveldbmcpejni.LevelDBMCPEJNI;

import static org.junit.Assert.*;

public class TestWorld {

	@Test
	public void testWorld() throws Exception {
		assertTrue(true);
		
		LevelDBMCPEJNI j = new LevelDBMCPEJNI("/app/share/thdVXmy-AAA=/db");
		assertNotNull(j);
		
		//Check to make sure I can actually get a record back.
		byte[] bytes = j.get(0, 0, 0, 0);
		assertNotNull(bytes);
		System.out.println(bytes.length);
	}
}
