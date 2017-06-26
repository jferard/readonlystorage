package com.github.jferard.readonlystorage.storage;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * An entry in the hash table. May be a UniqueEntry (one key) or a sorted EntryMap (several keys).
 */
public interface Entry<K, V> {

	/**
	 * Try to add a	(key, value) couple to the current entry. If it works, return this, else return a EntryMap.
	 * @param key a key
	 * @param value a value
	 * @return this or a new EntryMap if there is a new key
	 */
	Entry<K, V> add(K key, V value);

	/**
	 * @return an iterator on values
	 */
	Iterator<V> getListIterator();

	/**
	 * @return an iterator on iterables. That means : collections of values by iterator.
	 */
	Iterator<? extends Iterable<V>> getMapIterator();

	Map<K, List<V>> getMaps();
}
