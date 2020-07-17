package org.middlepath;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.junit.Test;
import org.middlepath.dassembler.DAssembler;

public class TestParse {

	public static final InputStream is;
	public static final InputStream is2;
	
	static {
		is = TestParse.class.getResourceAsStream("/org/middlepath/test/fibonacci.dasm");
		is2 = TestParse.class.getResourceAsStream("/org/middlepath/test/fibonacci1.dasm");
	}
	
	//@Test
	public void testParse() throws Exception {
		DAssembler assembler = new DAssembler(is);
		ByteArrayOutputStream bos = assembler.assemble();
		
		bos.toByteArray();
	}
	
	@Test
	public void testFib() throws Exception {
		DAssembler assembler = new DAssembler(is2);
		ByteArrayOutputStream bos = assembler.assemble();
		
		bos.toByteArray();
	}
}