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

import net.jpountz.lz4.LZ4BlockInputStream;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class Deserializer<K, V> {
    public static <L, W> Deserializer create(InputStream is, TypesDeserializer<L, W> typesDeserializer) throws IOException {
        return new Deserializer(is, LZ4Factory.nativeInstance().fastDecompressor(), typesDeserializer);
    }

    private final TypesDeserializer<K, V> typesDeserializer;
    private final ObjectInputStream is;

    Deserializer(InputStream is, final LZ4FastDecompressor decompressor, final TypesDeserializer<K, V> typesDeserializer) throws IOException {
        this.typesDeserializer = typesDeserializer;
        InputStream lz4Is = is; // new LZ4BlockInputStream(is, decompressor);
        this.is = new ObjectInputStream(lz4Is);
    }


    public List<Pair<K, List<V>>> deserializeMap() throws IOException {
        int mapSize = this.deserializeInt(); // number of keys
        List<Pair<K, List<V>>> llist = new ArrayList<Pair<K, List<V>>>(mapSize);
        for (int i = 0; i < mapSize; i++) {
            K key = this.typesDeserializer.deserializeKey(this.is);
            int valuesCount = this.deserializeInt(); // number of values
            List<V> values = new ArrayList<V>(valuesCount);
            for (int j = 0; j < valuesCount; j++) {
                values.add(this.typesDeserializer.deserializeValue(this.is)); // value p
            }
            llist.add(new Pair(key, values));
        }
        return llist;
    }

    public int deserializeInt() throws IOException {
        return this.is.readInt();
    }

    public void close() throws IOException {
        this.is.close();
    }

}
