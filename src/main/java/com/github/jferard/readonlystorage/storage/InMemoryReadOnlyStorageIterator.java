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

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Julien on 03/06/2017.
 */
public class InMemoryReadOnlyStorageIterator<K, V> implements Iterator<Pair<K, List<V>>> {
	private int index;
	private Iterator<V> listIterator;
	private Iterator<? extends Iterable<V>> mapIterator;
	private V next;
	private Entry<K, V>[] table;
	private Iterator<Pair<K, List<V>>> keysIterator;
	private int curCellIndex;

	public InMemoryReadOnlyStorageIterator(Entry<K, V>[] table) {
		this.table = table;
		this.index = 0;
	}

	@Override
	public boolean hasNext() {
		if (this.keysIterator != null && this.keysIterator.hasNext())
			return true;

		this.curCellIndex++;
		if (this.curCellIndex >= this.table.length)
			return false;

		Entry<K, V> e = this.table[this.curCellIndex];
		while (e == null) {
			this.curCellIndex++;
			if (this.curCellIndex >= this.table.length)
				return false;

			e = this.table[this.curCellIndex];
		}
		this.keysIterator = e.getMap().iterator();
		return true;
	}

	@Override
	public Pair<K, List<V>> next() {
		this.hasNext();
		return this.keysIterator.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
