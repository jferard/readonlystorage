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
import java.util.Iterator;
import java.util.List;

/**
 * Created by jferard on 28/06/17.
 */
public class StreamProgress<K extends Comparable<K>, V> implements Comparable<StreamProgress<K, V>>, Iterator<Pair<K, List<V>>> {
    private Deserializer<K, V> deserializer;
    private Iterator<Pair<K, List<V>>> keysIterator;
    private int curCellIndex;
    private Pair<K, List<V>> curKeyValues;

    StreamProgress(Deserializer<K, V> deserializer) throws IOException {
        this.deserializer = deserializer;
    }

    @Override
    public final boolean hasNext() {
        if (this.keysIterator != null && this.keysIterator.hasNext())
            return true;
        try {
            this.curCellIndex = this.deserializer.deserializeInt(); // get cell index
            List<Pair<K, List<V>>> llist = this.deserializer.deserializeMap();
            this.keysIterator = llist.iterator();
        } catch (IOException eofe) {
            return false;
        }
        return true;
    }

    @Override
    public Pair<K, List<V>> next() {
        this.hasNext();
        this.curKeyValues = this.keysIterator.next();
        return this.curKeyValues;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int compareTo(StreamProgress<K, V> other) {
        if (this.curCellIndex < other.curCellIndex)
            return -1;
        else if (this.curCellIndex > other.curCellIndex)
            return 1;
        else {
            if (this.curKeyValues == null)
                return 1;
            if (other.curKeyValues == null)
                return -1;
            return this.curKeyValues.getKey().compareTo(other.curKeyValues.getKey());
        }
    }
}
