package com.github.jferard.readonlystorage.input;

import com.github.jferard.readonlystorage.index.IndexBuilder;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by jferard on 05/06/17.
 */
public class StoreField implements Field {
    private String name;
    private final int size;
    private final IndexBuilder indexer;

    public StoreField(String name, final int size, final IndexBuilder indexer) {
        this.name = name;
        this.size = size;
        this.indexer = indexer;
    }

    @Override
    public int write(String id, OutputStream os) throws IOException {
        return FieldUtil.writeString(id, this.size, os);
    }

    @Override
    public void updateIndex(String id, long pos) {
        this.indexer.add(id, pos);
    }
}
