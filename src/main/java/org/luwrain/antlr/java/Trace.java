// SPDX-License-Identifier: BUSL-1.1
// Copyright 2012-2025 Michael Pozhidaev <msp@luwrain.org>

package org.luwrain.antlr.java;

import java.util.*;
import java.io.*;
import java.nio.file.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.atn.*;

import org.luwrain.util.*;

import static java.nio.charset.StandardCharsets.*;
import static java.util.stream.Collectors.*;
import static org.luwrain.antlr.java.DefaultParser.*;

public final class Trace
{
    static public void main(String[] args) throws IOException
    {
	final var lines = new ArrayList<>(LineIterator.toList(Paths.get(args[0]), UTF_8.toString()));
	final var text = lines.stream().collect(joining("\n"));
	parse(new StringReader(text), new JavaParserBaseListener(){
		@Override public void enterEveryRule(ParserRuleContext c)  
		{
		    final var className = c.getClass().getSimpleName();
		    final var line = c.getStart().getLine();
		    System.out.println(className.substring(0, className.length() - 7) + " " + line + "," + c.getStart().getCharPositionInLine() + ": " + lines.get(line - 1));
		}
		@Override public void exitEveryRule(ParserRuleContext c)  
		{
		    //		    handler.endBlock(c.getClass().getSimpleName(), c.getStop().getLine(), c.getStop().getCharPositionInLine(),
				     //				     c.getStart().getLine(), c.getStart().getCharPositionInLine());
		}

	    });
	System.out.println(args[0]);
    }
}
