/*
 * ReadonlyStorage - A light NoSQL storage for read only data
 *     Copyright (C) 2017 J. Férard <https://github.com/jferard>
 *
 * This file is part of ReadonlyStorage.
 *
 * ReadonlyStorage is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * ReadonlyStorage is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.jferard.readonlystorage.storage;

import java.util.*;

/**
 * A UniqueEntry : one key and many values.
 */
public class UniqueEntry<K extends Comparable<K>, V> implements Entry<K,V> {
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
	public List<Pair<K, List<V>>> getMap() {
		return Arrays.asList(new Pair<K, List<V>>(this.key, this.values));
	}

	@Override
	public String toString() {
		return "UniqueEntry["+this.key+"]";
	}
}
