package com.github.jferard.readonlystorage.storage;

import java.io.*;

/**
 */
public class IntSerializer {
    public void serialize(int data, OutputStream os) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.write(data);
    }

    public int deserialize(InputStream is) throws IOException {
        ObjectInputStream ois = new ObjectInputStream(is);
        return ois.readInt();
    }
}
