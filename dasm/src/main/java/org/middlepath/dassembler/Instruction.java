package org.middlepath.dassembler;

public enum Instruction implements AbstractInstruction {

	LDA_R(LDA_REF, InstructionType.REFERENCE),
	LDB_R(LDB_REF, InstructionType.REFERENCE),
	LDA_I(LDA_IMM, InstructionType.IMMEDIATE),
	LDB_I(LDB_IMM, InstructionType.IMMEDIATE),
	ADD(ADD_NOOP, InstructionType.NOOPERAND),
	SUB(SUB_NOOP, InstructionType.NOOPERAND),
	JMP_R(JMP_REF, InstructionType.REFERENCE),
	BEQ_R(BEQ_REF, InstructionType.REFERENCE),
	JMP_I(JMP_IMM, InstructionType.IMMEDIATE),
	BEQ_I(BEQ_IMM, InstructionType.IMMEDIATE),
	STA(AbstractInstruction.STA_REF, InstructionType.REFERENCE),
	STB(AbstractInstruction.STB_REF, InstructionType.REFERENCE),
	OUT(AbstractInstruction.OUT_REF, InstructionType.REFERENCE);
	
	private final byte value;
	private final InstructionType instructionType;
	
	private Instruction(byte value, InstructionType instructionType) {
		this.value = value;
		this.instructionType = instructionType;
	}
	
	@Override
	public byte getValue() {
		return this.value;
	}
	
	@Override
	public InstructionType getType() {
		return this.instructionType;
	}
	
}