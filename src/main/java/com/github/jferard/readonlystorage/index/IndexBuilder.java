package com.github.jferard.readonlystorage.index;

import com.google.common.collect.Multimap;

import java.io.OutputStream;

/**
 */
public class IndexBuilder {
    private final Multimap<String, Long> multimap;

    public IndexBuilder(Multimap<String, Long> multimap){
        this.multimap = multimap;
    }

    public void add(String value, long pos) {
        this.multimap.put(value, pos);
    }

    public void save(OutputStream os) {
        // depending on the number of keys.
        for (;;) {


        }
    }
}

