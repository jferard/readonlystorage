/*
 * ReadonlyStorage - A light NoSQL storage for read only data
 *     Copyright (C) 2017 J. Férard <https://github.com/jferard>
 *
 * This file is part of ReadonlyStorage.
 *
 * ReadonlyStorage is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * ReadonlyStorage is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

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
