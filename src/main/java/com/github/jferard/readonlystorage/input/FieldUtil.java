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

import com.google.common.base.Charsets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Created by jferard on 05/06/17.
 */
public class FieldUtil {
    public static int writeInt(int value, OutputStream os) throws IOException {
        int b1 = value >>> 24;
        int r = b1 << 24;
        int b2 = (value - r) >>> 16;
        r += b2 << 16;
        int b3 = (value - r) >>> 8;
        r += b3 << 8;
        int b4 = value - r;
        os.write(b1);
        os.write(b2);
        os.write(b3);
        os.write(b4);
        return 4;
    }

    public static int writeString(String value, int size, OutputStream os) throws IOException {
        byte[] bytes = value.getBytes(Charsets.UTF_8);
        FieldUtil.writeInt(bytes.length, os);
        os.write(bytes);
        return 4 + size;
    }

    public static int writeLong(long value, OutputStream os) throws IOException {
        long big = value >>> 32;
        long small = value - (big << 32);
        FieldUtil.writeInt((int) big, os);
        FieldUtil.writeInt((int) small, os);
        return 8;
    }
}
