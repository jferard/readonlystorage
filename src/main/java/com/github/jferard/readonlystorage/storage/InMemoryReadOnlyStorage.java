package com.github.jferard.readonlystorage.storage;

import java.util.Iterator;

/**
 */
public class InMemoryReadOnlyStorage<K, V> implements ReadOnlyStorage<K, V> {
	private Entry<K, V>[] table;

	public InMemoryReadOnlyStorage(Entry<K, V>[] table) {
		this.table = table;
	}

	@Override
	public Iterator<V> valuesIterator() {
		return new InMemoryReadOnlyStorageIterator<K, V>(this.table);
	}
}
