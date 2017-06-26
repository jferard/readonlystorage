package com.github.jferard.readonlystorage.storage;

import net.jpountz.lz4.LZ4BlockOutputStream;
import net.jpountz.lz4.LZ4Factory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A DefaultTableFlusher flushes the table to a file. The format is:
 * [cell index][number of keys]
 *      [key 0][number of values]
 *          [value 0]
 *          ...
 *          [value v-1]
 *      ...
 *      [key k-1]...
 *          ...
 *
 *  ...
 */
public class DefaultTableFlusher<K, V> implements TableFlusher<K, V> {
    private static final int BLOCK_SIZE = 1024 * 1024;

    private IntSerializer si;
    private final Serializer<K> sk;
    private final Serializer<V> sv;
    private final int blockSize;

    public static <L, W> DefaultTableFlusher<L, W> create(Serializer<L> sk, Serializer<W> sv) {
        return new DefaultTableFlusher(new IntSerializer(), sk, sv, BLOCK_SIZE);
    }

    DefaultTableFlusher(IntSerializer si, Serializer<K> sk, Serializer<V>
            sv, int blockSize) {
        this.si = si;
        this.sk = sk;
        this.sv = sv;
        this.blockSize = blockSize;
    }

    public int flush(Entry<K, V>[] table, OutputStream os) throws IOException {
        int score = 0;
        OutputStream os2 = new LZ4BlockOutputStream(os, blockSize, LZ4Factory.nativeInstance().fastCompressor());
        for (int i = 0; i < table.length; i++) {
            Entry<K, V> e = table[i];
            if (e != null) {
                this.si.serialize(i, os); // cell index
                Map<K, List<V>> maps = e.getMaps();
                this.si.serialize(maps.size(), os); // number of keys
                for (Map.Entry<K, List<V>> entry : maps.entrySet()) {
                    K key = entry.getKey();
                    Collection<V> values = entry.getValue();
                    this.sk.serialize(key, os); // key n
                    this.si.serialize(values.size(), os); // number of values
                    for (V value : values) {
                        this.sv.serialize(value, os); // value p
                    }
                }
                score++;
            }
        }
        return score;
    }
}
