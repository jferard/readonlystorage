package com.github.jferard.readonlystorage.input;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by jferard on 05/06/17.
 */
public class IntField implements Field {
    private final String name;

    public IntField(String name) {
        this.name = name;
    }

    @Override
    public int write(String valueAsString, OutputStream os) throws IOException {
        return FieldUtil.writeInt(Integer.valueOf(valueAsString), os);
    }

    @Override
    public void updateIndex(String valueAsString, long pos) {}
}
