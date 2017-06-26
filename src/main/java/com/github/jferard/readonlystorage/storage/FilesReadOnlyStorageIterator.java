package com.github.jferard.readonlystorage.storage;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Julien on 03/06/2017.
 */
public class FilesReadOnlyStorageIterator<K, V> implements Iterator<V> {
	private int index;
	private List<File> files;

	public FilesReadOnlyStorageIterator(List<File> files) {
		this.files = files;
		// open streams
	}

	@Override
	public boolean hasNext() {
		// iterator on merge keys

		// priority queue
		// or add++
		return false;
	}

	@Override
	public V next() {
		return null;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
