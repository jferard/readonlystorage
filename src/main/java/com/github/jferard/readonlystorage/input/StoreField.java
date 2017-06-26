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

package com.github.jferard.readonlystorage.input;

import com.github.jferard.readonlystorage.index.IndexBuilder;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by jferard on 05/06/17.
 */
public class StoreField implements Field {
    private String name;
    private final int size;
    private final IndexBuilder indexer;

    public StoreField(String name, final int size, final IndexBuilder indexer) {
        this.name = name;
        this.size = size;
        this.indexer = indexer;
    }

    @Override
    public int write(String id, OutputStream os) throws IOException {
        return FieldUtil.writeString(id, this.size, os);
    }

    @Override
    public void updateIndex(String id, long pos) {
        this.indexer.add(id, pos);
    }
}
