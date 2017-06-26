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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jferard on 05/06/17.
 */
public class DataImporterBuilder {
    private final List<Field> fields;

    DataImporterBuilder() {
        this.fields = new ArrayList<Field>();
    }

    public DataImporterBuilder addString(final String name, final int size) {
        IndexBuilder is = null;
        this.fields.add(new StringField(name, size, is));
        return this;
    }

    public DataImporterBuilder addInt(final String name) {
        this.fields.add(new IntField(name));
        return this;
    }

    public DataImporter build() {
        return new DataImporter(this.fields);
    }
}
