package com.github.jferard.readonlystorage.input;

import com.google.common.base.Charsets;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by jferard on 05/06/17.
 */
public class FieldUtil {
    public static int writeInt(int value, OutputStream os) throws IOException {
        os.write(value >>> 24);
        os.write(value >>> 16);
        os.write(value >>> 8);
        os.write(value);
        return 4;
    }

    public static int writeString(String value, int size, OutputStream os) throws IOException {
        byte[] bytes = value.getBytes(Charsets.UTF_8);
        FieldUtil.writeInt(bytes.length, os);
        os.write(bytes);
        return 4 + size;
    }
}
