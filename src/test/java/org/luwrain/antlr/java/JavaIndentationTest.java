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
    @Test void simple() throws IOException { checkFile("Simple.java"); }

    void checkFile(String fileName) throws IOException
    {
	final var text = readStringResourceAsList(getClass(), fileName, "UTF-8");
	//	assertNotNull(text);
    }

    
}
