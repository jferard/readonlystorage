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

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import static org.junit.Assert.*;

public class FieldUtilTest {
    @Test
    public void testInt() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream(4);
        int a = 0;
        for (int i=100; i<104; i++)
            a = a *256 + i;
        FieldUtil.writeInt(a, os);
        Assert.assertArrayEquals(new byte[] {100, 101, 102, 103}, os.toByteArray());
    }

    @Test
    public void testLong() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream(4);
        long a = 0;
        for (int i=100; i<108; i++)
            a = a * 256 + i;
        FieldUtil.writeLong(a, os);
        Assert.assertArrayEquals(new byte[] {100, 101, 102, 103, 104, 105, 106, 107}, os.toByteArray());
    }
}