package com.github.jferard.readonlystorage.storage;

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
		Serializer<String> iss = new StringSerializer();
		Serializer<Integer> isi = new IntegerSerializer();
		EntryFactory<String, Integer> ef = null;
		ReadOnlyStorageBuilder<String, Integer> rosb = ReadOnlyStorageBuilder.create(new File("."),
				1000, 1000, ef, iss, isi);
		Random r = new Random();
		r.setSeed(Calendar.getInstance().getTimeInMillis());
		for (int i=0; i<100000; i++) {
			rosb.put(String.valueOf(r.nextInt(10000)), r.nextInt());
		}
		rosb.get();
	}
}

class IntegerSerializer implements Serializer<Integer> {
	@Override
	public void serialize(Integer data, OutputStream is) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new PipedOutputStream());
		oos.write(data);
	}

	@Override
	public Integer deserialize(InputStream os) throws IOException {
		return null;
	}
}

class StringSerializer implements Serializer<String> {
	@Override
	public void serialize(String data, OutputStream is) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new PipedOutputStream());
		oos.write(data.getBytes());
	}

	@Override
	public String deserialize(InputStream os) throws IOException {
		return null;
	}
}