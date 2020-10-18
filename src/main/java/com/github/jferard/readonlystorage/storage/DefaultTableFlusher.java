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

import java.io.IOException;
import java.util.List;

/**
 * A DefaultTableFlusher flushes the table to a file. The format is:
 * [cell index]
 *      [number of keys]                \
 *      [key 0][number of values]       |
 *          [value 0]                   \
 *          ...                          >  [map n]
 *          [value v-1]                 /
 *      ...                             |
 *      [key k-1]...                    |
 *          ...                         /
 *
 *  ...
 */
public class DefaultTableFlusher<K, V> implements TableFlusher<K, V> {
    private final Serializer<K, V> serializer;

    DefaultTableFlusher(Serializer<K, V> serializer) {
        this.serializer = serializer;
    }

    public int flush(Entry<K, V>[] table, int fileNum) throws IOException {
        int score = 0;
        for (int i = 0; i < table.length; i++) {
            Entry<K, V> e = table[i];
            if (e != null) {
                this.serializer.serializeInt(i); // cell index
                List<Pair<K, List<V>>> map = e.getMap();
                this.serializer.serializeMap(map, fileNum); // number of keys
                score++;
            }
        }
        return score;
    }

    public void close() throws IOException {
        this.serializer.close();
    }
}
