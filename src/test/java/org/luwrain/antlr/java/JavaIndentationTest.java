// SPDX-License-Identifier: BUSL-1.1
// Copyright 2012-2025 Michael Pozhidaev <msp@luwrain.org>

package org.luwrain.antlr.java;

import java.util.*;
import java.io.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.luwrain.util.ResourceUtils.*;

public class JavaIndentationTest
{
    @Test@Disabled void simple() throws IOException
    {
	final var map = getIndents("Simple.java");
	assertEquals(0, (int)map.get(1));  // package test;
	assertEquals(0, (int)map.get(3));  // class Test
	assertEquals(0, (int)map.get(4));  // {
	assertEquals(1, (int)map.get(5));  // void doSomething(int x)
	assertEquals(1, (int)map.get(6));  // {
	assertEquals(2, (int)map.get(7));  // if (x > 0)
	assertEquals(2, (int)map.get(8));  // {
	assertEquals(3, (int)map.get(9));  // System.out.println("Hello");
	assertEquals(2, (int)map.get(10)); // }
	assertEquals(1, (int)map.get(11)); // }
	assertEquals(0, (int)map.get(12)); // }
    }

    private Map<Integer, Integer> getIndents(String fileName) throws IOException
    {
	final var text = readStringResourceAsList(getClass(), fileName, "UTF-8");
	final var listener = new SourceCodeIndentsListener();
	DefaultParser.parse(new StringReader(String.join("\n", text)), listener);
	return listener.getLineIndents();
    }
}
