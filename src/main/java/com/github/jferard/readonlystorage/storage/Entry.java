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
