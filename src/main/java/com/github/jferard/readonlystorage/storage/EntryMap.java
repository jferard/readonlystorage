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
