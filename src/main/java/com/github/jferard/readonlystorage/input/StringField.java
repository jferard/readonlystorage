package com.github.jferard.readonlystorage.input;

import com.github.jferard.readonlystorage.index.IndexBuilder;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by jferard on 05/06/17.
 */
public class StringField implements Field {
    private final String name;
    private int size;
    private IndexBuilder indexer;

    // BLAST algorithm to find
    public StringField(String name, final int size, final IndexBuilder indexer) {
        this.name = name;
        this.size = size;
        this.indexer = indexer;
    }

    @Override
    public int write(String valueAsString, OutputStream os) throws IOException {
        return FieldUtil.writeString(valueAsString, this.size, os);
    }

    @Override
    public void updateIndex(String valueAsString, long pos) {
        this.indexer.add(valueAsString, pos);
    }
}
