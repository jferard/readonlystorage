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

import net.jpountz.lz4.LZ4BlockOutputStream;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 */
public class Serializer<K, V> {
    private static final int BLOCK_SIZE = 1024 * 1024;

    public static <L, W> Serializer create(OutputStream os, TypesSerializer<L, W> typesSerializer) throws IOException {
        return new Serializer(os, BLOCK_SIZE, LZ4Factory.nativeInstance().fastCompressor(), typesSerializer);
    }

    private final ObjectOutputStream os;
    private TypesSerializer<K, V> typesSerializer;

    Serializer(OutputStream os, final int blockSize, final LZ4Compressor compressor, final TypesSerializer<K, V> typesSerializer) throws IOException {
        this.typesSerializer = typesSerializer;
        OutputStream lz4Os = new LZ4BlockOutputStream(os, blockSize, compressor);
        this.os = new ObjectOutputStream(lz4Os);
    }

    public void serializeMap(Map<K, List<V>> map) throws IOException {
        this.serializeInt(map.size()); // number of keys
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            K key = entry.getKey();
            List<V> values = entry.getValue();
            this.typesSerializer.serializeKey(os, key); // key n
            this.serializeInt(values.size()); // number of values
            for (V value : values) {
                this.typesSerializer.serializeValue(os, value); // value p
            }
        }
    }

    public void serializeInt(int data) throws IOException {
        this.os.write(data);
    }
}
