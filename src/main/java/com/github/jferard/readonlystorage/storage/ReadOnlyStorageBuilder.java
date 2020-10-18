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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class ReadOnlyStorageBuilder<K extends Comparable<K>, V> {
    public static <L extends Comparable<L>, W> ReadOnlyStorageBuilder<L, W> create(File directory, int tableLength, int maxEntries,
                                                                                   EntryFactory<L, W> entryFactory, FlusherFactory<L, W> flusherFactory, TypesDeserializer<L, W> typesDeserializer) {
        Entry<L, W>[] table = (Entry<L, W>[]) new Entry[tableLength];
        return new ReadOnlyStorageBuilder(directory, table, maxEntries, entryFactory, flusherFactory, typesDeserializer);
    }

    private final List<File> files;
    private final File directory;
    private int count;
    private Entry<K, V>[] table;
    private EntryFactory<K, V> entryFactory;
    private int maxEntries;
    private FlusherFactory<K, V> flusherFactory;
    private TypesDeserializer<K, V> typesDeserializer;


    public ReadOnlyStorageBuilder(File directory, Entry[] table, int maxEntries,
                                  EntryFactory entryFactory, FlusherFactory<K, V> flusherFactory, TypesDeserializer<K, V> typesDeserializer) {
        this.directory = directory;
        this.table = table;
        this.entryFactory = entryFactory;
        this.maxEntries = maxEntries;
        this.flusherFactory = flusherFactory;
        this.typesDeserializer = typesDeserializer;
        this.count = 0;
        this.files = new ArrayList<File>();
    }

    /**
     * Add a new key/value pair.
     * @param key   the key to add
     * @param value the value
     */
    public void put(K key, V value) throws IOException {
        int index = this.getIndex(key);
        this.addEntry(key, value, index);
        this.count++;
        if (this.count == this.maxEntries) { // time to flush
            this.flush();
        }
    }

    /**
     * Add a key/value pair at an index
     * @param key the key
     * @param value the value
     * @param index the index (0..table.length-1)
     */
    private void addEntry(K key, V value, int index) {
        Entry<K, V> previousEntry = this.table[index];
        Entry<K, V> e;
        if (previousEntry == null) { // was empty
            e = this.entryFactory.create(key, value);
        } else {
            e = previousEntry.add(key, value);
        }
        this.table[index] = e;
    }

    /**
     * @param key the key
     * @return an index between 0 and table.length-1
     */
    private int getIndex(K key) {
        int h = key.hashCode();
        int hash = h ^ (h >>> 16); // h xor h/2^16
        return hash % table.length;
    }

    private void flush() throws IOException {
        File f = new File(this.directory, "ros" + this.files.size() + ".rbf");
        OutputStream os = new FileOutputStream(f);
        TableFlusher<K, V> flusher = this.flusherFactory.create(os);
        try {
            flusher.flush(this.table, this.files.size());
            this.files.add(f);
        } finally {
            flusher.close();
        }
        this.table = (Entry<K, V>[]) new Entry[this.table.length];
        this.count = 0;
    }

    /**
     * @return a readonly storage
     */
    public ReadOnlyStorage<K, V> get() throws IOException {
        if (this.files.isEmpty()) { // go for in memory
            return new InMemoryReadOnlyStorage<K, V>(this.table);
        } else {
            this.flush();
            return new FilesReadOnlyStorage<K, V>(this.files, this.typesDeserializer);
        }
    }
}