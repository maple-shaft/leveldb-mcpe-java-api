package org.middlepath.dassembler;

public interface AbstractInstruction {
	
	public static final byte LDA_IMM = 0x01;
	public static final byte LDB_IMM = 0x02;
	public static final byte ADD_NOOP = 0x03;
	public static final byte LDA_REF = 0x04;
	public static final byte LDB_REF = 0x05;
	public static final byte STA_REF = 0x06;
	public static final byte STB_REF = 0x07;
	public static final byte JMP_IMM = 0x08;
	public static final byte JMP_REF = 0x09;
	public static final byte SUB_NOOP = 0x0A;
	public static final byte BEQ_IMM = 0x0B;
	public static final byte BEQ_REF = 0x0C;
	public static final byte OUT_REF = 0x0D;
	
	byte getValue();
	
	InstructionType getType();
}