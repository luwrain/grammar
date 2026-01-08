// SPDX-License-Identifier: BUSL-1.1
// Copyright 2012-2025 Michael Pozhidaev <msp@luwrain.org>

package org.luwrain.antlr.java;

import java.io.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.atn.*;

public final class DefaultParser
{
    static public void parse(Reader r, JavaParserListener listener) throws IOException
    {
	final JavaLexer lexer = new JavaLexer(CharStreams.fromReader(r));
	final CommonTokenStream tokens = new CommonTokenStream(lexer);
	final JavaParser parser = new JavaParser(tokens);
	parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
	final ParseTree tree = parser.compilationUnit();
	final ParseTreeWalker walker = new ParseTreeWalker();
	walker.walk(listener, tree);
    }
}
