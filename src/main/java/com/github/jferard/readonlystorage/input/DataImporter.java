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

import com.github.jferard.readonlystorage.storage.ReadOnlyStorageBuilder;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by jferard on 05/06/17.
 */
public class DataImporter<K extends Comparable<K>> {
    private ReadOnlyStorageBuilder<K, CSVRecord> builder;
    private Fields<K, CSVRecord> fields;

    public DataImporter(ReadOnlyStorageBuilder<K, CSVRecord> builder, Fields<K, CSVRecord> fields) {
        this.builder = builder;
        this.fields = fields;
    }

    public void importData(CSVParser parser, OutputStream os) throws IOException {
        long pos = 0;
        for (CSVRecord record : parser) {
            K key = fields.getKey(record);
            this.builder.put(key, record);
            for (int i=0; i<record.size(); i++) {
                String valueAsString = record.get(i);
                // Field field = fields.get(i);
                // pos = storageBuilder.put(keyAsString, CSVRecord)
                // if field has index:
                //    os_field.write(value, pos) // see Field interface
                //
            }
        }
    }

}
