package org.middlepath.mcapi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.middlepath.leveldbmcpejni.LevelDBMCPEJNI;
import org.middlepath.mcapi.chunk.CompleteChunk;
import org.middlepath.mcapi.chunk.SubChunkTypeChunk;
import org.middlepath.mcapi.chunk.factory.CompleteChunkFactory;
import org.middlepath.mcapi.generic.Coordinate;

import static org.junit.Assert.*;

public class CompleteChunkTest {

	LevelDBMCPEJNI j = null;
	
	@Before
	public void setup() throws Exception {
		j = new LevelDBMCPEJNI("/home/dustin/Desktop/workspace/thdVXmy-AAA=/db");
	}
	
	@After
	public void tearDown() throws Throwable {
		j.close();
		j = null;
		System.gc();
	}
	
	@Test
	public void testCompleteChunk() throws Exception {
		Coordinate coord = new Coordinate(16,16,0);
		CompleteChunkFactory factory = new CompleteChunkFactory(j);
		CompleteChunk c = factory.getLocatable(coord);
		assertNotNull(c);
		SubChunkTypeChunk subChunk = c.getSubChunk(0);
		assertNotNull(subChunk);
		subChunk.forEach(System.out::println);
	}
}
