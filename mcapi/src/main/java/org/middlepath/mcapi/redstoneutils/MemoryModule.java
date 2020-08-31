package org.middlepath.mcapi.redstoneutils;

import java.util.Comparator;

import org.middlepath.mcapi.generic.Locatable;

public interface MemoryModule <
		T extends Locatable,
		S extends Number,
		K extends Comparable<K>,
		R extends MemoryWord<T,S,K>> extends Comparator<R> {

}
