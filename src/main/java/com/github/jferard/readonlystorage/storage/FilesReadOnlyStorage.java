package com.github.jferard.readonlystorage.storage;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 */
public class FilesReadOnlyStorage<K, V> implements ReadOnlyStorage<K, V> {
	private List<File> files;

	public FilesReadOnlyStorage(List<File> files) {
		this.files = files;
	}

	@Override
	public Iterator<V> valuesIterator() {
		return new FilesReadOnlyStorageIterator<K, V>(this.files);
	}
}
