package org.middlepath.mcapi;

import org.junit.Test;
import org.middlepath.leveldbmcpejni.LevelDBMCPEJNI;
import org.middlepath.leveldbmcpejni.RecordType;
import org.middlepath.mcapi.block.BlockType;
import org.middlepath.mcapi.block.SubChunkBlock;
import org.middlepath.mcapi.chunk.CompleteChunk;
import org.middlepath.mcapi.chunk.SubChunkTypeChunk;
import org.middlepath.mcapi.chunk.factory.CompleteChunkFactory;
import org.middlepath.mcapi.generic.Coordinate;
import org.middlepath.mcapi.group.BlockGrouping;
import org.middlepath.mcapi.group.Grouping;
import org.middlepath.mcapi.source.ElementSource;
import org.middlepath.mcapi.world.World;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

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
	public void testWorldSections() throws Exception {
		CompleteChunkFactory factory = new CompleteChunkFactory(j);
		World w = new World(factory);
		Grouping<CompleteChunk> section1 = w.createChildGroup("0x0 to 32x16", c2, c4);
		assertNotNull(section1);
		
		Grouping<CompleteChunk> section2 = w.createChildGroup("16x16 to 32x0", c1, c3);
		assertNotNull(section2);
		
		Grouping<CompleteChunk> childSection = section1.createChildGroup("0x0 to 16x16", c2, c1);
		assertNotNull(childSection);
		
		Grouping<CompleteChunk> shouldBeNull = section2.createChildGroup("null", c2, c1);
		assertNull(shouldBeNull);
		
		Grouping<CompleteChunk> section1Ref = w.getChildGroupByName("0x0 to 32x16");
		assertNotNull(section1Ref);
		assertEquals(section1, section1Ref);
		
		section1.forEach(System.out::println);
		System.out.println("Count of CompleteChunks for section1: " + StreamSupport.stream(section1.spliterator(), false).count());
		
		w.forEach(System.out::println);
		System.out.println("Count of CompleteChunks for the world: " +
				StreamSupport.stream(w.spliterator(), false).count());
	}
	
	@Test
	public void testGlobalBlockIterator() throws Exception {
		CompleteChunkFactory factory = new CompleteChunkFactory(j);
		World w = new World(factory);
		
		ElementSource bs = new ElementSource(factory);
		BlockGrouping<SubChunkBlock> group1 = new BlockGrouping<SubChunkBlock>(
				bs, "group1", c2, c5);
		assertNotNull(group1);
		assertEquals(c2, group1.getPointOne());
		assertEquals(c5, group1.getPointTwo());
		Iterator<SubChunkBlock> it = group1.iterator();
		assertNotNull(it);
		int count = 0;
		List<SubChunkBlock> blocks = new ArrayList<SubChunkBlock>();
		while (it.hasNext()) {
			count++;
			SubChunkBlock block = it.next();
			blocks.add(block);
			System.out.println(block);
		}
		System.out.println("Count of blocks: " + count);
	}
	
	@Test
	public void testValidateCell() throws Exception {
		Coordinate c1 = new Coordinate(75, 48, 0);
		Coordinate c2 = new Coordinate(90, 63, 15);
		CompleteChunkFactory factory = new CompleteChunkFactory(j);
		World w = new World(factory);
		ElementSource bs = new ElementSource(factory);
		BlockGrouping<SubChunkBlock> bg = new BlockGrouping<SubChunkBlock>(bs, "cell_loc1", c1, c2);
		SubChunkBlock cell1 = bg.getLocatable(new Coordinate(81, 56, 11));
		assertNotNull(cell1);
		assertEquals(BlockType.UNLIT_REDSTONE_TORCH, cell1.getBlockType());
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
