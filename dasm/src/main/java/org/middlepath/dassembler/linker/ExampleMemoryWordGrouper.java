package org.middlepath.dassembler.linker;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExampleMemoryWordGrouper {

	public static Integer mapRowIndex(ExampleMemoryCell cell) {
		return cell.getRowIndex();
	}
	
	public static Boolean mapByAddress(ExampleMemoryCell cell) {
		return cell.isAddress();
	}
	
	public static ExampleMemoryWordTuple createTupleFromWordCells(Collection<ExampleMemoryCell> cells) {
		Map<Boolean, List<ExampleMemoryCell>> cellsByPart = 
				cells.stream().collect(Collectors.groupingBy(ExampleMemoryWordGrouper::mapByAddress));
		ExampleMemoryWordTuple tuple = new ExampleMemoryWordTuple(
				cellsByPart.values().stream()
					.map(v -> new ExampleMemoryWord(v)).collect(Collectors.toList()));
		return tuple;				
	}
	
	public static List<ExampleMemoryWordTuple> groupTuples(List<ExampleMemoryCell> cells) {
		return cells.stream().collect(Collectors.groupingBy(ExampleMemoryWordGrouper::mapRowIndex))
				.values().stream().map(ExampleMemoryWordGrouper::createTupleFromWordCells)
				.collect(Collectors.toList());
	}

}
