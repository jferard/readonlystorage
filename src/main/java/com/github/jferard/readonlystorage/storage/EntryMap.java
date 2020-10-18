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
 * A sorted map of key -> [values].
 * The special feature is that the EntryMap is ordered by keys, allowing to scan several parallel
 * tables in the same order.
 */
public class EntryMap<K extends Comparable<K>, V> implements Entry<K, V> {
    private final List<Pair<K, List<V>>> map;
    private int size;

    /**
     * Create a new EntryMap
     * @param size the size
     * @param key the initial key
     * @param values the initial values
     * @param newKey the new key
     * @param newValue the new value
     */
    public EntryMap(final int size, K key, List<V> values, K newKey, V newValue) {
        this.size = size;
        this.map = new LinkedList<Pair<K, List<V>>>();
        this.map.add(new Pair(key, values));

        List<V> newValues = new ArrayList<V>(size);
        newValues.add(newValue);
        // add the pair at its place
        if (newKey.compareTo(key) <= 0)
            this.map.add(0, new Pair(newKey, newValues));
        else
            this.map.add(new Pair(newKey, newValues));
    }

    @Override
    public Entry<K, V> add(K key, V value) {
        int i=0;
        // add (key, [value]) to its place, depending on key ordering.
        for (Pair<K, List<V>> entry : this.map) {
            int keyCmp = key.compareTo(entry.getKey());
            if (keyCmp == 0) { // I've got it!
                entry.getValue().add(value);
                break;
            } else if (keyCmp < 0) { // please continue...
                i++;
            } else { // this key is unknown
                List<V> values = new ArrayList<V>(size);
                values.add(value);
                this.map.add(i, new Pair(key, values));
                break;
            }
        }
        return this;
    }

    @Override
    public Iterator<V> getListIterator() {
        throw new RuntimeException("TODO");
    }

    @Override
    public List<Pair<K, List<V>>> getMap() {
        return this.map;
    }

    @Override
    public String toString() {
        return "EntryMap[" + this.map.toString() + "]";
    }
}
