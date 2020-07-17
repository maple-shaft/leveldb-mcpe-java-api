package org.middlepath.dassembler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class DAssembler {

	private final ParseTreeWalker walker = new ParseTreeWalker();
	
	private final DasmWorkerListener listener;
	
	public DAssembler(InputStream source) throws IOException {
		listener = new DasmWorkerListener(source);
	}
	
	public ByteArrayOutputStream assemble() {
		walker.walk(listener, listener.getAppContext());
		
		// Will not work with parallel streams
		return listener.getParsedOperations().stream()
				.map(o -> o.getBytes())
				.collect(ByteArrayOutputStream::new, ((a,b) -> a.write(b, 0, 2)), ((x,y) -> x.write(0)));
	}
}