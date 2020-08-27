package org.middlepath.dassembler.linker;

import java.util.Collection;
import java.util.Iterator;

public class ExampleMemoryWordTuple implements Comparable<ExampleMemoryWordTuple> {
	
	//first index is address, second data
	private ExampleMemoryWord[] words = new ExampleMemoryWord[2];
	
	public ExampleMemoryWordTuple() {
	}
	
	public ExampleMemoryWordTuple(ExampleMemoryWord word) {
		if (word.isAddressWord()) {
			words[0] = word;
		} else {
			words[1] = word;
		}
	}
	
	public ExampleMemoryWordTuple(ExampleMemoryWord addressWord, ExampleMemoryWord dataWord) {
		this.words[0] = addressWord;
		this.words[1] = dataWord;
	}
	
	public ExampleMemoryWordTuple(Collection<ExampleMemoryWord> wordArgs) {
		Iterator<ExampleMemoryWord> it = wordArgs.iterator();
		while (it.hasNext()) {
			ExampleMemoryWord w = it.next();
			if (w.isAddressWord()) {
				words[0] = w;
			} else {
				words[1] = w;
			}
		}
	}

	public ExampleMemoryWord getAddressWord() {
		return this.words[0];
	}

	public void setAddressWord(ExampleMemoryWord addressWord) {
		this.words[0] = addressWord;
	}

	public ExampleMemoryWord getDataWord() {
		return this.words[1];
	}

	public void setDataWord(ExampleMemoryWord dataWord) {
		this.words[1] = dataWord;
	}
	
	public ExampleMemoryAddress getAddress() {
		if (this.words[0] == null)
			return null;
		return new ExampleMemoryAddress(this.words[0].getValue().intValue());
	}
	
	/**
	 * The Address and Data Words for this build happen to be on the same Z coordinate row.  That is how we 
	 * can correlate them
	 * 
	 * @return
	 */
	public Integer getZRow() {
		if (words[0] == null && words[1] == null)
			return null;
		
		ExampleMemoryWord someWord = (getAddressWord() == null) ? getDataWord() : getAddressWord();
		return someWord.getZRow();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ExampleMemoryWordTuple))
			return false;
		ExampleMemoryWordTuple a = (ExampleMemoryWordTuple)obj;
		return ((words[0] == null) ? (a.getAddressWord() == words[0]) : (words[0].equals(a.getAddressWord())) &&
				(words[1] == null) ? (a.getDataWord() == words[1]) : (words[1].equals(a.getDataWord())));
	}
	
	@Override
	public int hashCode() {
		return ((words[0] == null) ? 0 : getAddress().hashCode());
	}

	@Override
	public int compareTo(ExampleMemoryWordTuple o) {
		int a = (words[0] == null) ? Integer.MIN_VALUE : getAddress().getAddress();
		int b = (o.getAddressWord() == null) ? Integer.MIN_VALUE : o.getAddress().getAddress();
		return a - b;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("Memory Tuple: [");
		sb.append(getAddress());
		sb.append(", ");
		sb.append(getDataWord());
		sb.append("]");
		return sb.toString();
	}

}
