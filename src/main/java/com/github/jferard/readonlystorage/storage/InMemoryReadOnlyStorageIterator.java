package com.github.jferard.readonlystorage.storage;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Julien on 03/06/2017.
 */
public class InMemoryReadOnlyStorageIterator<K, V> implements Iterator<V> {
	private int index;
	private Iterator<V> listIterator;
	private Iterator<? extends Iterable<V>> mapIterator;
	private V next;
	private Entry<K, V>[] table;

	public InMemoryReadOnlyStorageIterator(Entry<K, V>[] table) {
		this.table = table;
		this.index = 0;
	}

	@Override
	public boolean hasNext() {
		if (this.next != null)
			return true;

		while (true) {
			if (this.listIterator != null && this.listIterator.hasNext()) {
				this.next = this.listIterator.next();
				break;
			} else if (this.mapIterator != null && this.mapIterator.hasNext()) {
				this.listIterator = this.mapIterator.next().iterator();
			} else if (this.index < this.table.length - 1) {
				if (!updateIterators())
					break;
			} else {
				break;
			}
		}
		return this.next != null;
	}

	private boolean updateIterators() {
		this.index++;
		Entry<K, V> entry = this.findNextEntry();
		if (entry == null)
			return false;
		else {
			this.mapIterator = entry.getMapIterator();
			this.listIterator = entry.getListIterator();
		}
		return true;
	}

	private Entry<K, V> findNextEntry() {
		while (this.index < this.table.length) {
			Entry<K, V> entry = this.table[this.index];
			if (entry != null)
				return entry;
		}
		return null;
	}


	@Override
	public V next() {
		if (this.next == null) {
			if (!this.hasNext())
				throw new NoSuchElementException();
		}

		V ret = this.next;
		this.next = null;
		return ret;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
