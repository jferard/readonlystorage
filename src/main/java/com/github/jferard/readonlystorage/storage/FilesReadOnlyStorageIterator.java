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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by jferard on 03/06/2017.
 */
public class FilesReadOnlyStorageIterator<K extends Comparable<K>, V> implements Iterator<Pair<K, List<V>>> {
	private final PriorityQueue<StreamProgress<K,V>> progresses;

	public FilesReadOnlyStorageIterator(List<File> files, TypesDeserializer<K, V> typesDeserializer) throws IOException {
		this.progresses = new PriorityQueue<StreamProgress<K,V>>(files.size());
		for (File file : files) {
			FileInputStream is = new FileInputStream(file);
			Deserializer<K, V> deserializer = Deserializer.create(is, typesDeserializer);
			StreamProgress<K,V> progress = new StreamProgress<K, V>(deserializer);
			progresses.add(progress);
		}
		System.out.println(this.progresses);
	}

	@Override
	public boolean hasNext() {
		StreamProgress<K, V> progress = this.progresses.peek();
		if (progress == null)
			return false;
		while (!progress.hasNext()) {
			this.progresses.poll();
			progress = this.progresses.peek();
			if (progress == null)
				return false;
		}
		return progress.hasNext();
	}

	@Override
	public Pair<K, List<V>> next() {
		StreamProgress<K, V> progress = this.progresses.poll();
		while (!progress.hasNext())
			progress = this.progresses.poll();
		Pair<K, List<V>> ret = progress.next();
		this.progresses.offer(progress);
		return ret;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
