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
