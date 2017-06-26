package com.github.jferard.readonlystorage.storage;

/**
 */
public class EntryFactory<K, V> {
	private int size;

	public EntryFactory(int size) {
		this.size = size;
	}

	public Entry<K, V> create(K key, V value) {
		return new UniqueEntry<K, V>(this.size, key, value);
	}

	public int getSize() {
		return size;
	}
}
