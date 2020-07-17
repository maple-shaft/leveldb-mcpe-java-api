package org.middlepath.dassembler;

public class InstructionFactory {

	private static InstructionFactory _instance;
	
	private InstructionFactory() {}
	
	public static InstructionFactory getInstance() {
		if (_instance == null)
			_instance = new InstructionFactory();
		return _instance;
	}
	
	public Instruction createInstructionFromByte(byte b) {
		switch (b) {
			case Instruction.BEQ_IMM:
				return Instruction.BEQ_I;
			case Instruction.JMP_IMM:
				return Instruction.JMP_I;
			case Instruction.LDA_IMM:
				return Instruction.LDA_I;
			case Instruction.LDB_IMM:
				return Instruction.LDB_I;
			case Instruction.ADD_NOOP:
				return Instruction.ADD;
			case Instruction.BEQ_REF:
				return Instruction.BEQ_R;
			case Instruction.JMP_REF:
				return Instruction.JMP_R;
			case Instruction.LDA_REF:
				return Instruction.LDA_R;
			case Instruction.LDB_REF:
				return Instruction.LDB_R;
			case Instruction.OUT_REF:
				return Instruction.OUT;
			case Instruction.STA_REF:
				return Instruction.STA;
			case Instruction.STB_REF:
				return Instruction.STB;
			case Instruction.SUB_NOOP:
				return Instruction.SUB;
			default:
				return null;
		}
	}
	
	public Instruction createInstructionFromLexerConstant(int lexerConstant, InstructionType type) {
		if (type == InstructionType.REFERENCE) {
			switch (lexerConstant) {
				case DasmLexer.LDA:
					return Instruction.LDA_R;
				case DasmLexer.LDB:
					return Instruction.LDB_R;
				case DasmLexer.JMP:
					return Instruction.JMP_R;
				case DasmLexer.BEQ:
					return Instruction.BEQ_R;
				case DasmLexer.STA:
					return Instruction.STA;
				case DasmLexer.STB:
					return Instruction.STB;
				case DasmLexer.OUT:
					return Instruction.OUT;
			}
		} else if (type == InstructionType.NOOPERAND) {
			switch (lexerConstant) {
				case DasmLexer.ADD:
					return Instruction.ADD;
				case DasmLexer.SUB:
					return Instruction.SUB;
			}
		} else {
			switch (lexerConstant) {
				case DasmLexer.BEQ:
					return Instruction.BEQ_I;
				case DasmLexer.JMP:
					return Instruction.JMP_I;
				case DasmLexer.LDA:
					return Instruction.LDA_I;
				case DasmLexer.LDB:
					return Instruction.LDB_I;
			}
		}
		return null;
	}
}