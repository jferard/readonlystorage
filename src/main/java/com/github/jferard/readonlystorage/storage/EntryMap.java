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
 * A sorted map of key -> many values
 */
public class EntryMap<K, V> implements Entry<K, V> {
    private final TreeMap<K, List<V>> map;
    private int size;

    public EntryMap(final int size, K key, List<V> values, K newKey, V newValue) {
        this.size = size;
        this.map = new TreeMap<K, List<V>>();
        this.map.put(key, values);

        List<V> newValues = new ArrayList<V>(size);
        newValues.add(newValue);
        this.map.put(newKey, newValues);
    }

    public Entry<K, V> add(K key, V value) {
        Collection<V> previousList = this.map.get(key);
        if (previousList == null) {
            List<V> newList = new ArrayList<V>(size);
            newList.add(value);
            this.map.put(key, newList);
        } else {
            previousList.add(value);
        }
        return this;
    }

    @Override
    public Iterator<V> getListIterator() {
        return null;
    }

    @Override
    public Iterator<List<V>> getMapIterator() {
        return this.map.values().iterator();
    }

    @Override
    public Map<K, List<V>> getMaps() {
        return this.map;
    }

    @Override
    public String toString() {
        return "EntryMap[" + this.map.keySet().toString() + "]";
    }
}
