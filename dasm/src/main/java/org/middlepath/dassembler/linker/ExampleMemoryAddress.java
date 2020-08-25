package org.middlepath.dassembler.linker;

/**
 * An object that represents a unique memory address. It serves as a classifier object that can be a key in 
 * a map of Words.
 * 
 * @author DAB
 *
 */
public class ExampleMemoryAddress implements Comparable<ExampleMemoryAddress> {

	private final int address;
	
	public ExampleMemoryAddress(int address) {
		this.address = address;
	}
	
	public int getAddress() {
		return this.address;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ExampleMemoryAddress))
			return false;
		ExampleMemoryAddress a = (ExampleMemoryAddress)obj;
		return a.getAddress() == getAddress();
	}
	
	@Override
	public int hashCode() {
		return getAddress();
	}
	
	@Override
	public String toString() {
		return "Memory Address: " + getAddress();
	}

	@Override
	public int compareTo(ExampleMemoryAddress o) {
		return getAddress() - o.getAddress();
	}
	
	
}
