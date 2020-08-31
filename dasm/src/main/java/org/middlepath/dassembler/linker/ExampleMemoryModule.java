package org.middlepath.dassembler.linker;

import java.util.Collection;
import java.util.stream.Collectors;

import org.middlepath.mcapi.block.SubChunkBlock;
import org.middlepath.mcapi.redstoneutils.AbstractMemoryWordPairModule;

public class ExampleMemoryModule extends AbstractMemoryWordPairModule<
		SubChunkBlock,
		Byte,
		Integer,
		ExampleMemoryWord,
		ExampleMemoryWordTuple> {

	
	public static Integer mapRowIndex(ExampleMemoryCell cell) {
		return cell.getRowIndex();
	}
	
	public static Boolean mapByInstruction(ExampleMemoryCell cell) {
		return cell.isInstruction();
	}
	
	public ExampleMemoryModule(Collection<ExampleMemoryCell> cells) {
		super();
		cells.stream().collect(Collectors.groupingBy(c -> c.getRowIndex()))
				.values().stream().map(ExampleMemoryWordTuple::new)
				.forEach(this::put);
	}
}
