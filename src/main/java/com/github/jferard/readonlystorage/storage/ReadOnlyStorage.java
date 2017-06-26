package com.github.jferard.readonlystorage.storage;

import java.util.Iterator;

/**
 * A ReadOnlyStorage gives a restitution of the values grouped by keys
 */
public interface ReadOnlyStorage<K, V> {
	/** @return an iterator on values */
	Iterator<V> valuesIterator();
}
