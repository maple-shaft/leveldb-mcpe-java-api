package org.middlepath.dassembler.linker;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.middlepath.mcapi.block.SubChunkBlock;
import org.middlepath.mcapi.chunk.factory.CompleteChunkFactory;
import org.middlepath.mcapi.generic.ByteRetriever;
import org.middlepath.mcapi.generic.Coordinate;
import org.middlepath.mcapi.group.BlockGrouping;
import org.middlepath.mcapi.source.ElementSource;
import org.middlepath.mcapi.visitor.FilteringElementTransformingVisitor;
import org.middlepath.mcapi.visitor.GroupingVisitor;
import org.middlepath.mcapi.visitor.NoAction;

public class DAssemblerLinker {

	public final ExampleMemoryModule module;
	
	public DAssemblerLinker(ExampleMemoryModule module) {
		this.module = module;
	}
	
	public DAssemblerLinker(ByteRetriever bs, Coordinate c1, Coordinate c2) {
		CompleteChunkFactory factory = new CompleteChunkFactory(bs);
		ElementSource es = new ElementSource(factory);
		final BlockGrouping<SubChunkBlock> bg = new BlockGrouping<SubChunkBlock>(es, "cell_loc1", c1, c2);
		
		MemoryCellFilter filter = new MemoryCellFilter(bg);
		FilteringElementTransformingVisitor<SubChunkBlock> filteringVisitor = 
				new FilteringElementTransformingVisitor<>(new NoAction<SubChunkBlock>(), filter);
		GroupingVisitor<SubChunkBlock> groupVisitor = new GroupingVisitor<>(filteringVisitor);
		
		groupVisitor.visit(bg);
		List<ExampleMemoryCell> cells = filteringVisitor.getFilterResultCache().stream()
				.map(s -> ExampleMemoryCell.createMemoryCell(s, bg))
				.collect(Collectors.toList());
		this.module = new ExampleMemoryModule(cells);
	}
	
	public void writeBytesToMemoryModule(ByteArrayOutputStream bos) {
		if (bos == null) {
			return;
		}
		
		byte[] programBytes = bos.toByteArray();
		Iterator<ExampleMemoryWordTuple> it = this.module.iterator();
		for (int i = 0; i < programBytes.length; i += 2) {
			ExampleMemoryWordTuple tuple = it.next();
			tuple.getInstructionWord().setValue(programBytes[i]);
			tuple.getDataWord().setValue(programBytes[i+1]);
		}
	}
}
