package com.github.jferard.readonlystorage.storage;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A flusher for table
 */
public interface TableFlusher<K, V> {
    int flush(Entry<K, V>[] table, OutputStream os) throws IOException;
}
