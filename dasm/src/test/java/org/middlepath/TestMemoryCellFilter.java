package org.middlepath;

import org.junit.Test;
import org.middlepath.dassembler.linker.MemoryCellFilter;
import org.middlepath.leveldbmcpejni.LevelDBMCPEJNI;
import org.middlepath.mcapi.block.SubChunkBlock;
import org.middlepath.mcapi.chunk.factory.CompleteChunkFactory;
import org.middlepath.mcapi.generic.Coordinate;
import org.middlepath.mcapi.group.BlockGrouping;
import org.middlepath.mcapi.source.ElementSource;
import org.middlepath.mcapi.visitor.FilteringElementTransformingVisitor;
import org.middlepath.mcapi.visitor.GroupingVisitor;
import org.middlepath.mcapi.visitor.NoAction;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

public class TestMemoryCellFilter {

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
	public void testMemoryCell() throws Exception {
		Coordinate c1 = new Coordinate(75, 48, 0);
		Coordinate c2 = new Coordinate(90, 63, 15);
		CompleteChunkFactory factory = new CompleteChunkFactory(j);
		ElementSource bs = new ElementSource(factory);
		final BlockGrouping<SubChunkBlock> bg = new BlockGrouping<SubChunkBlock>(bs, "cell_loc1", c1, c2);
		
		MemoryCellFilter filter = new MemoryCellFilter(bg);
		FilteringElementTransformingVisitor<SubChunkBlock> filteringVisitor = 
				new FilteringElementTransformingVisitor<>(new NoAction<SubChunkBlock>(), filter);
		GroupingVisitor<SubChunkBlock> groupVisitor = new GroupingVisitor<>(filteringVisitor);
		
		groupVisitor.visit(bg);
		
		assertNotNull(filteringVisitor.getFilterResultCache());
	}
}
