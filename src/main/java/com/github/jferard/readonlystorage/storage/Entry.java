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

/**
 * An entry in the (hash) table.
 * As in a standard hash map, several keys may have the same hash (mod the size of the table),
 * and thus use the same cell of the array.
 * Therefore, the entry may be a SimpleEntry (one key) or an EntryMap (several keys).
 * The special feature is that the EntryMap is ordered by keys, allowing to scan several parallel
 * tables in the same order.
 */
public interface Entry<K, V> {

    /**
     * Try to add a	(key, value) couple to the current entry.
     * If it works, return this, else return a EntryMap.
     *
     * @param key   a key
     * @param value a value
     * @return this or a new EntryMap if there is a new key
     */
    Entry<K, V> add(K key, V value);

    /**
     * @return an iterator on values
     */
    Iterator<V> getListIterator();

    List<Pair<K, List<V>>> getMap();
}
