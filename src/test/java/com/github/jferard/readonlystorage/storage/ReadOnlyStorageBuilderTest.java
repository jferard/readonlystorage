package com.github.jferard.readonlystorage.storage;

import net.jpountz.lz4.LZ4Compressor;
import org.junit.Test;

import java.io.*;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Julien on 04/06/2017.
 */
public class ReadOnlyStorageBuilderTest {
	public void test0() {
		for (int i=0; i<100; i++) {
			final Integer v = Integer.valueOf(i);
			System.out.println(""+ v +": "+v.hashCode()+", "+v.hashCode() % 100);
		}
	}

	@Test
	public void test() throws IOException {
		EntryFactory<String, Integer> ef = new EntryFactory<String, Integer>(10);
		FlusherFactory<String, Integer> ff = new MyFlushFactory();
		MyTypesSerializer mts = new MyTypesSerializer();
		ReadOnlyStorageBuilder<String, Integer> rosb = ReadOnlyStorageBuilder.create(new File("temp_files"),
				1000, 1000, ef, ff);
		Random r = new Random();
		r.setSeed(Calendar.getInstance().getTimeInMillis());
		for (int i=0; i<100000; i++) {
			rosb.put(String.valueOf(r.nextInt(10000)), r.nextInt());
		}
		rosb.get();
	}
}

class MyFlushFactory implements FlusherFactory<String, Integer> {
	@Override
	public TableFlusher<String, Integer> create(OutputStream os) throws IOException {
		TypesSerializer<String, Integer> ts = new MyTypesSerializer();
		Serializer<String, Integer> s = Serializer.create(os, ts);
		return new DefaultTableFlusher<String, Integer>(s);
	}
}

class MyTypesSerializer implements TypesSerializer<String, Integer> {
	@Override
	public void serializeKey(ObjectOutputStream os, String data) throws IOException {
		os.write(data.getBytes());
	}

	@Override
	public void serializeValue(ObjectOutputStream os, Integer data) throws IOException {
		os.write(data);
	}
}