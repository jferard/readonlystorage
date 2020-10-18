package com.github.jferard.readonlystorage.input;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;

/**
 * Created by jferard on 05/06/17.
 */
public class DataImporterTest {
    public void test() throws IOException {
        CSVParser parser = CSVFormat.RFC4180.parse(new StringReader("1,abc\n2,cdef"));
//        DataImporter importer = new DataImporter(Arrays.asList(new StoreField("f1", 1, null), new StringField("f2", 1, null)));
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        importer.importData(parser, os);
//        Assert.assertArrayEquals(new byte[] { 0,0,0,1,0,0,0,3,'a','b','c',0,0,0,2,0,0,0,4,'c','d','e','f' }, os.toByteArray());
    }


}