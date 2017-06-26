package com.github.jferard.readonlystorage.storage;

import java.util.*;

/**
 * A UniqueEntry : one key and many values.
 */
public class UniqueEntry<K, V> implements Entry<K,V> {
	private int expectedSize;
	private final K key;
	private final List<V> values;

	/**
	 * Create the entry and add the current (key, value) couple
	 * @param expectedSize
	 * @param key
	 * @param value
	 */
	public UniqueEntry(int expectedSize, K key, V value) {
		this.expectedSize = expectedSize;
		this.key = key;
		this.values = new ArrayList<V>(expectedSize);
		this.values.add(value);
	}

	@Override
	public Entry<K, V> add(K key, V value) {
		if (this.key.equals(key)) {
			this.values.add(value);
			return this;
		} else {
			return new EntryMap<K, V>(this.expectedSize, this.key, this.values, key, value);
		}
	}

	@Override
	public Iterator<V> getListIterator() {
		return this.values.iterator();
	}

	@Override
	public Iterator<? extends Iterable<V>> getMapIterator() {
		return null;
	}

	@Override
	public Map<K, List<V>> getMaps() {
		Map<K, List<V>> map = new HashMap<K, List<V>>(1);
		map.put(this.key, this.values);
		return map;
	}

	@Override
	public String toString() {
		return "UniqueEntry["+this.key+"]";
	}
}
