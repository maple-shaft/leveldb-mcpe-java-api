package org.middlepath.mcapi;

import org.junit.Test;
import org.middlepath.leveldbmcpejni.LevelDBMCPEJNI;
import org.middlepath.mcapi.block.BlockType;
import org.middlepath.mcapi.block.SubChunkBlock;
import org.middlepath.mcapi.chunk.factory.CompleteChunkFactory;
import org.middlepath.mcapi.generic.Coordinate;
import org.middlepath.mcapi.group.BlockGrouping;
import org.middlepath.mcapi.source.ElementSource;
import org.middlepath.mcapi.visitor.FilterAction;
import org.middlepath.mcapi.visitor.FilteringElementTransformingVisitor;
import org.middlepath.mcapi.visitor.GroupingVisitor;
import org.middlepath.mcapi.visitor.NoAction;
import org.middlepath.mcapi.visitor.VisitAction;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

public class FilterTest {

	LevelDBMCPEJNI j = null;
	
	public static boolean actionFlag = false;
	
	private VisitAction<SubChunkBlock> testAction = new VisitAction<SubChunkBlock>() {
		@Override
		public void perform(SubChunkBlock t) {
			actionFlag = true;
		}
	};
	
	public FilterTest() throws Exception {
		
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
	public void testFilter() throws Exception {
		Coordinate c1 = new Coordinate(75, 48, 0);
		Coordinate c2 = new Coordinate(90, 63, 15);
		CompleteChunkFactory factory = new CompleteChunkFactory(j);
		ElementSource bs = new ElementSource(factory);
		BlockGrouping<SubChunkBlock> bg = new BlockGrouping<SubChunkBlock>(bs, "cell_loc1", c1, c2);
		SubChunkBlock cell1 = bg.getLocatable(new Coordinate(81,56,11));
		assertNotNull(cell1);
		assertEquals(BlockType.UNLIT_REDSTONE_TORCH, cell1.getBlockType());
		
		FilterAction<SubChunkBlock> filter = new FilterAction<SubChunkBlock>() {
			@Override
			public boolean filter(SubChunkBlock t) {
				return t.getBlockType().equals(BlockType.UNLIT_REDSTONE_TORCH);
			}
		};
		
		actionFlag = false;
		FilteringElementTransformingVisitor<SubChunkBlock> filteringVisitor =
				new FilteringElementTransformingVisitor<>(testAction, filter);
		
		filteringVisitor.visit(cell1);
		assertFalse(actionFlag);
	}
	
	@Test
	public void collectingBlocksOnAPattern() throws Exception {
		Coordinate c1 = new Coordinate(75, 48, 0);
		Coordinate c2 = new Coordinate(90, 63, 15);
		CompleteChunkFactory factory = new CompleteChunkFactory(j);
		ElementSource bs = new ElementSource(factory);
		final BlockGrouping<SubChunkBlock> bg = new BlockGrouping<SubChunkBlock>(bs, "cell_loc1", c1, c2);
		
		//Only get unlit torches to the right of a lit torch
		FilterAction<SubChunkBlock> filter = new FilterAction<>() {
			@Override
			public boolean filter(SubChunkBlock t) {
				if (!BlockType.UNLIT_REDSTONE_TORCH.equals(t.getBlockType()))
					return true;
				try {
					Coordinate neighborCoord = (Coordinate)t.getCoordinate().clone();
					neighborCoord.setGlobalX(neighborCoord.getGlobalX() -1);
					SubChunkBlock neighbor = bg.getLocatable(neighborCoord);
					return (neighbor == null ||
							!(BlockType.REDSTONE_TORCH.equals(neighbor.getBlockType())));
				} catch (Exception e) {
					e.printStackTrace();
					return true;
				}
			}
		};
		
		FilteringElementTransformingVisitor<SubChunkBlock> filteringVisitor = 
				new FilteringElementTransformingVisitor<>(new NoAction<SubChunkBlock>(), filter);
		GroupingVisitor<SubChunkBlock> groupVisitor = new GroupingVisitor<>(filteringVisitor);
		
		groupVisitor.visit(bg);
		
		assertNotNull(filteringVisitor.getFilterResultCache());
		assertFalse(filteringVisitor.getFilterResultCache().isEmpty());
		filteringVisitor.getFilterResultCache().stream().forEach(System.out::println);
	}
}
