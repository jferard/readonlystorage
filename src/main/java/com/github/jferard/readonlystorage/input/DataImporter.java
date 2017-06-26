package com.github.jferard.readonlystorage.input;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by jferard on 05/06/17.
 */
public class DataImporter {
    private List<Field> fields;

    public DataImporter(List<Field> fields) {
        this.fields = fields;
    }

    public void importData(CSVParser parser, OutputStream os) throws IOException {
        long pos = 0;
        for (CSVRecord record : parser) {
            for (int i=0; i<record.size(); i++) {
                String valueAsString = record.get(i);
                Field field = fields.get(i);
                pos += field.write(valueAsString, os);
                field.updateIndex(valueAsString, pos);
            }
            for (int i=0; i<record.size(); i++) {
                Field field = fields.get(i);
//                field.index().write(pos);
            }
        }
    }

}
