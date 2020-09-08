package org.middlepath;

import org.junit.Test;
import org.middlepath.dassembler.DAssembler;
import org.middlepath.dassembler.linker.DAssemblerLinker;
import org.middlepath.dassembler.linker.ExampleMemoryCell;
import org.middlepath.dassembler.linker.ExampleMemoryModule;
import org.middlepath.dassembler.linker.ExampleMemoryWordTuple;
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

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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
		//Coordinate c1 = new Coordinate(75, 48, 0);
		//Coordinate c2 = new Coordinate(90, 63, 15);
		Coordinate c1 = new Coordinate(58, 54, 6);
		Coordinate c2 = new Coordinate(178, 90, 15);
		
		CompleteChunkFactory factory = new CompleteChunkFactory(j);
		ElementSource bs = new ElementSource(factory);
		final BlockGrouping<SubChunkBlock> bg = new BlockGrouping<SubChunkBlock>(bs, "cell_loc1", c1, c2);
		
		MemoryCellFilter filter = new MemoryCellFilter(bg);
		FilteringElementTransformingVisitor<SubChunkBlock> filteringVisitor = 
				new FilteringElementTransformingVisitor<>(new NoAction<SubChunkBlock>(), filter);
		GroupingVisitor<SubChunkBlock> groupVisitor = new GroupingVisitor<>(filteringVisitor);
		
		groupVisitor.visit(bg);
		
		assertNotNull(filteringVisitor.getFilterResultCache());
		
		List<ExampleMemoryCell> cells = filteringVisitor.getFilterResultCache().stream()
				.map(s -> ExampleMemoryCell.createMemoryCell(s, bg))
				.collect(Collectors.toList());
		assertNotNull(cells);
		
		ExampleMemoryModule module = new ExampleMemoryModule(cells);
		assertNotNull(module);

		Iterator<ExampleMemoryWordTuple> it = module.iterator();
		while(it.hasNext()) {
			ExampleMemoryWordTuple t = it.next();
			t.getInstructionWord().setValue((byte)1);
			t.getDataWord().setValue((byte)2);
			System.out.println(t.getInstructionWord().getValue());
			System.out.println(t.getDataWord().getValue());
		}
		System.out.println(module);
		//List<ExampleMemoryWordTuple> tuples = ExampleMemoryWordGrouper.groupTuples(cells);
		//assertNotNull(tuples);
	}
	
	@Test
	public void testMemoryLink() throws Exception {
		Coordinate c1 = new Coordinate(58, 54, 6);
		Coordinate c2 = new Coordinate(178, 90, 15);
		DAssemblerLinker linker = new DAssemblerLinker(j, c1, c2);
		
		DAssembler assembler = new DAssembler(TestParse.is2);
		ByteArrayOutputStream bos = assembler.assemble();
		
		linker.writeBytesToMemoryModule(bos);
		
		//Now test if the bytes are correct
		byte[] moduleBytesActual = linker.getMemoryContentsOfModule();
		byte[] expected = bos.toByteArray();
		System.out.println(moduleBytesActual.length);
		
		for (int i = 0; (i < expected.length && i < moduleBytesActual.length); i++) {
			System.out.println("Expected: " + expected[i] +
					", Actual: " + moduleBytesActual[i]);
			assertEquals(expected[i], moduleBytesActual[i]);
		}
		
		
	}
}
