package com.github.jferard.readonlystorage.index;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jferard on 06/06/17.
 */
public class ByteIndexer {
    private Map<String, Byte> byteByString;

    ByteIndexer(Map<String, Byte> byteByString) {
        this.byteByString = byteByString;
    }

    public ByteIndexer create() {
        return new ByteIndexer(new HashMap<String, Byte>());
    }

    public void index(String value, int pos) {

    }

    /**
     * @return the byte to match
     */
    public byte getByte(String s) {
        return this.byteByString.get(s);
    }
}
