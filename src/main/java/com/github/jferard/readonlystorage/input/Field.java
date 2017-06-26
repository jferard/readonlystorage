package com.github.jferard.readonlystorage.input;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by jferard on 05/06/17.
 */
public interface Field {
    int write(String valueAsString, OutputStream os) throws IOException;

    void updateIndex(String valueAsString, long pos);
}
