package org.middlepath.dassembler;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import org.antlr.v4.gui.Trees;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.middlepath.dassembler.DasmParser.AppContext;
import org.middlepath.dassembler.DasmParser.Immediate_instructionContext;
import org.middlepath.dassembler.DasmParser.LineContext;
import org.middlepath.dassembler.DasmParser.Nooperand_instructionContext;
import org.middlepath.dassembler.DasmParser.AppContext;
import org.middlepath.dassembler.DasmParser.OperandContext;
import org.middlepath.dassembler.DasmParser.Reference_instructionContext;

public class DasmWorkerListener extends DasmBaseListener {
	
	protected final CommonTokenStream tokenStream;
	protected final DasmLexer lexer;
	protected final DasmParser parser;
	
	private LinkedList<Operation> parsedOperations = new LinkedList<Operation>();
	private AbstractInstruction _tempInstruction;
	private byte _tempOperand;
	
	public DasmWorkerListener(CharStream cs) {
		this.lexer = new DasmLexer(cs);
		this.tokenStream = new CommonTokenStream(lexer);
		this.parser = new DasmParser(tokenStream);
	}
	
	public DasmWorkerListener(InputStream is) throws IOException {
		this(CharStreams.fromStream(is));
	}
	
	@Override
	public void exitLine(LineContext ctx) {
		if (_tempInstruction == null)
			return;
		getParsedOperations().add(new Operation(_tempInstruction, _tempOperand));
		_tempInstruction = null;
		_tempOperand = 0;
	}
	
	@Override
	public void enterOperand(OperandContext ctx) {
		Token t = ctx.getStart();
		_tempOperand = Byte.decode(t.getText());
	}
	
	@Override
	public void enterNooperand_instruction(Nooperand_instructionContext ctx) {
		Token t = ctx.getStart();
		_tempInstruction = InstructionFactory.getInstance()
				.createInstructionFromLexerConstant(t.getType(), InstructionType.NOOPERAND);
	}
	
	@Override
	public void enterImmediate_instruction(Immediate_instructionContext ctx) {
		Token t = ctx.getStart();
		_tempInstruction = InstructionFactory.getInstance()
				.createInstructionFromLexerConstant(t.getType(), InstructionType.IMMEDIATE);
	}
	
	@Override
	public void enterReference_instruction(Reference_instructionContext ctx) {
		Token t = ctx.getStart();
		_tempInstruction = InstructionFactory.getInstance()
				.createInstructionFromLexerConstant(t.getType(), InstructionType.REFERENCE);
	}
	
	public AppContext getAppContext() {
		return this.parser.app();
	}
	
	public void inspect() {
		Trees.inspect(this.parser.app(), this.parser);
	}
	
	public LinkedList<Operation> getParsedOperations() {
		return parsedOperations;
	}
}