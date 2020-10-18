# ReadonlyStorage
A light NoSQL storage for read only data

Copyright (C) 2017 J. Férard <https://github.com/jferard>

## Work in progress !
This is just a prototype for testing. Do not rely on it.

## Motivation I
I need a way to handle some sets of rows that are grouped by a key. But in the source (a CSV file), these sets might be split into several chunks.

A map-reduce procedure, that will be performed once or twice, must browse each set of rows as one piece.

## Design
The first part of the job is to take a row after another. Each row has key. This is used to generate an index (basically: the hash code modulo an array length). If there is a collision, the keys are ordered.

When the number of rows exceeds a threshold (a tradeoff between memory and speed), the array is flushed in a file, and the job continues with an empty table and a new destination file.

The second part of the job is a map reduce procedure. By design, each file contains an array, and each cell of the array contains one or more entries with a pair composed of a key and a list of rows.

All the files are opened in parallel. The streams are put in a priority queue, first ordered by the current index in the table, then by the key. To get the next key/row pair, we just need to take the minimum stream of the priority queue and to take the next element of this stream.

## Complexity
Insert the rows into the files: *O(n)*

Read the rows in the required order: *O(n.lg k)*, where the *k* files are merged (see k-way merge algorithm with a min heap).

The overall complexity is *O(n.lg k)*.

## Motivation II
The data results of a denormalization. That is, for a theoretical functional dependency, we often have a "weak dependency", let's say:

* functional dependency *X -> Y*: in the relation *R*, there is only one *Y* for each *X* ;
* weak dependency *X ~> Y*: in the relation *R*, there is only **a few** *Y* for each *X*.

It would be interesting to renormalize the data, in order to spare some disk space, and thus I/O time.

## Renormalization
To take advantage of the situation "weak dependency", let's note that the weak dependency may be strenghten by adding an index to *X*: *X i -> X Y* where *i >= 0* is small.

Thus, a kind of renormalization is possible: for each theoretical functional dependency *X -> Y* and each pair *(x, y<sub>i</sub>)*, we store a t-uple *(x, i, x, y<sub>i</sub>)* in a table.

If we find the attributes *x* and *y* in a row *(x, y, z, t, ...)*, it becomes possible to create a secondary table *(x, y)* and to make from *x* a foreign key in the row.

## Algorithm
When we find couple *(x, y)* we look for a *(x, i, x, y)*. If the t-uple exists, then *(x, i)* is the foreign key we need. If the t-uple doesn't exist, we build a *(x, n, x, y)* t-uple and *(x, n)* is the foreign key we need.

    REDUCE([V1, ..., Vn], [W1, ..., Wk]):
    FOR EACH V in [V1, ..., Vn]:
        INSERT REDUCE_ONE(V, [W1, ..., Wk]) IN T

    REDUCE_ONE(V, [W1, ..., Wk]):
        V as (x1, ..., xm)
        FOR EACH Wj in [W1, ..., Wk]:
            Wj as (Xa1, ..., Xau -> Xb1, ..., Xbv)
            IF V DOES NOT CONTAIN Xa1, ..., Xau:
                CONTINUE

            IF (xa1, ..., xau, xb1, ..., xbv) IN TWj:
                i <- GET INDEX OF (xa1, ..., xau, xb1, ..., xbv) IN TWj
            ELSE:
                i <- 0
                INSERT REDUCE_ONE ((xb1, ..., xbv), [W1, ..., Wk]) IN TWj
            REPLACE (xb1, ..., xbv) BY i IN V
        RETURN V

Since values are the result of a denormalization, the weak dependencies form a forest (should be proven): if W is a weak dependency X -> Y, then all weak dependencies with Z -> T and Z is a   subset of Y is a child of W.

If W1, ..., Wk are the roots of the trees of the forest:

    REDUCE([V1, ..., Vn], [W1, ..., Wk]):
    FOR EACH V in [V1, ..., Vn]:
        INSERT REDUCE_ONE'(V, [W1, ..., Wk]) IN T

    REDUCE_ONE'(V, [W1, ..., Wk]):
        V as (x1, ..., xm)
        FOR EACH Wj in [W1, ..., Wk]:
            V = REDUCE_ONE''(V, Wj)
        RETURN V

    REDUCE_ONE''(V, W):
        FOR EACH Y CHILD OF W:
            V = REDUCE_ONE''(V, Y)

        W as (Xa1, ..., Xau -> Xb1, ..., Xbv)
        IF (xa1, ..., xau, xb1, ..., xbv) IN TW:
            i <- GET INDEX OF (xa1, ..., xau, xb1, ..., xbv) IN TW
        ELSE:
            i <- 0
            INSERT V IN TW
        REPLACE (xb1, ..., xbv) BY i IN V
        RETURN V



## Storage
Secondary tables should be stored in memory, but that's not guaranted because they may be huge.
