package org.middlepath.mcapi;

import org.junit.Test;
import org.middlepath.leveldbmcpejni.LevelDBMCPEJNI;
import org.middlepath.leveldbmcpejni.RecordType;
import org.middlepath.mcapi.chunk.CompleteChunk;
import org.middlepath.mcapi.chunk.SubChunkTypeChunk;
import org.middlepath.mcapi.chunk.factory.CompleteChunkFactory;
import org.middlepath.mcapi.generic.Coordinate;
import org.middlepath.mcapi.world.World;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

public class TestWorld {

	Coordinate c1 = new Coordinate(16,16,0,0,0,0);
	Coordinate c1_1 = new Coordinate(16,16,16,0,0,0);
	Coordinate c2 = new Coordinate(0,0,0,0,0,0);
	Coordinate c3 = new Coordinate(32,0,0,0,0,0);
	Coordinate c3_1 = new Coordinate(32,0,16,0,0,0);
	Coordinate c4 = new Coordinate(32,16,0,0,0,0);
	Coordinate c5 = new Coordinate(32,32,32,0,0,0);
	
	LevelDBMCPEJNI j = null;
	
	public TestWorld() throws Exception {
		//j = new LevelDBMCPEJNI("/home/dustin/Desktop/workspace/thdVXmy-AAA=/db");
	}
	
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
	public void testWorld() throws Exception {
		
		CompleteChunkFactory factory = new CompleteChunkFactory(j);
		World w = new World(factory);
		assertNotNull(w);
		CompleteChunk chunk1 = w.getLocatable(c1);
		CompleteChunk chunk2 = w.getLocatable(c2);
		CompleteChunk chunk3 = w.getLocatable(c3);
		CompleteChunk chunk4 = w.getLocatable(c4);
		CompleteChunk chunk5 = w.getLocatable(c5);
		
		assertNotNull(chunk1);
		assertNotNull(chunk2);
		assertNotNull(chunk3);
		assertNotNull(chunk4);
		assertNotNull(chunk5);
		
		SubChunkTypeChunk sub1 = chunk1.getSubChunk(0);
		SubChunkTypeChunk sub2 = chunk1.getSubChunk(1);
		assertNotNull(sub1);
		assertNotNull(sub2);
		assertNotEquals(sub1, sub2);
		
		assertNull(chunk1.getSubChunk(2));
	}
	
	@Test
	public void testSanity() throws Exception {
		assertTrue(true);
		assertNotNull(j);
		
		//Check to make sure I can actually get a record back.
		byte[] bytes = j.get(RecordType.SUBCHUNK, 0, 0, 0, 0);
		assertNotNull(bytes);
		System.out.println(bytes.length);
	}
	
	
}
