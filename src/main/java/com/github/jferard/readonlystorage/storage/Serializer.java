package com.github.jferard.readonlystorage.storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 */
public interface Serializer<S> {
	void serialize(S data, OutputStream os) throws IOException;

	S deserialize(InputStream os) throws IOException;
}
