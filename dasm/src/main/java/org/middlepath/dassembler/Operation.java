package org.middlepath.dassembler;

public class Operation {

	private final AbstractInstruction instruction;
	private final byte[] bytes = new byte[2];
	
	public Operation(AbstractInstruction instruction, byte operand) {
		this.instruction = instruction;
		this.bytes[0] = instruction.getValue();
		this.bytes[1] = operand;
	}
	
	public byte[] getBytes() {
		return this.bytes;
	}
	
	public AbstractInstruction getInstruction() {
		return this.instruction;
	}
	
	public byte getOperand() {
		return this.bytes[1];
	}
	
	@Override
	public boolean equals(Object arg) {
		if (arg == null || !(arg instanceof Operation)) {
			return false;
		}
		
		Operation a = (Operation)arg;
		return this.bytes[0] == arg.getBytes()[0] && this.bytes[1] == arg.getBytes()[1];
	}
	
	@Override
	public int hashCode() {
		return this.bytes[0] ^ this.bytes[1];
	}
}