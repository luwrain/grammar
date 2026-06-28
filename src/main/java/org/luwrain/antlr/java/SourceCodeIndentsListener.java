// SPDX-License-Identifier: BUSL-1.1
// Copyright 2012-2025 Michael Pozhidaev <msp@luwrain.org>

package org.luwrain.antlr.java;

import java.util.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

/**
 * A parse tree listener that computes expected indentation for each line
 * of a Java source file based on its block structure.
 *
 * <p>
 * After walking the parse tree with this listener, call
 * {@link #getLineIndents()} to obtain a mapping from line number (1-based)
 * to the expected indentation level. The indentation level is expressed
 * in units of standard indentation (typically 4 spaces or 1 tab).
 * </p>
 *
 * <p>
 * The listener tracks the depth of nested block constructs such as class
 * bodies, method bodies, blocks, switch blocks, array initializers, etc.
 * Each opening brace increases the indent level by one, and each closing
 * brace decreases it back. Lines that do not start any parser rule
 * (empty lines, comment-only lines) are not present in the resulting map.
 * </p>
 */
public final class SourceCodeIndentsListener extends JavaParserBaseListener
{
    /** Current indentation depth in block levels. */
    private int currentIndent = 0;

    /**
     * Mapping from line number to the minimum expected indentation level.
     * Using minimum ensures that if multiple contexts start on the same
     * line, the outermost (smallest) indent wins.
     */
    private final Map<Integer, Integer> lineIndents = new HashMap<>();

    /**
     * Returns the computed line-to-indent mapping.
     *
     * @return An unmodifiable map where keys are 1-based line numbers
     * and values are expected indentation levels
     */
    public Map<Integer, Integer> getLineIndents()
    {
        return Collections.unmodifiableMap(lineIndents);
    }

    /**
     * Records the given indent level for a specific line.
     * If the line already has a recorded indent, keeps the smaller value.
     *
     * @param line The 1-based line number
     * @param indent The indentation level to record
     */
    private void recordLineIndent(int line, int indent)
    {
        if (line <= 0)
            return;
        lineIndents.merge(line, indent, Math::min);
    }

    /**
     * Records indent for the start line of a context at the current indent level.
     *
     * @param ctx The parser rule context whose start line should be recorded
     */
    private void recordStart(ParserRuleContext ctx)
    {
        if (ctx != null && ctx.getStart() != null)
            recordLineIndent(ctx.getStart().getLine(), currentIndent);
    }

    /**
     * Records indent for the stop line of a context at the current indent level.
     *
     * @param ctx The parser rule context whose stop line should be recorded
     */
    private void recordStop(ParserRuleContext ctx)
    {
        if (ctx != null && ctx.getStop() != null)
            recordLineIndent(ctx.getStop().getLine(), currentIndent);
    }

    /**
     * Enters a block construct: records the start line at the current
     * indent level, then increases indent for the block body.
     *
     * @param ctx The parser rule context for the block construct
     */
    private void enterBlock(ParserRuleContext ctx)
    {
        recordStart(ctx);
        currentIndent++;
    }

    /**
     * Exits a block construct: decreases indent back to the outer level,
     * then records the stop line at that outer indent level.
     *
     * @param ctx The parser rule context for the block construct
     */
    private void exitBlock(ParserRuleContext ctx)
    {
        currentIndent--;
        recordStop(ctx);
    }

    // ==================== enterEveryRule ====================

    /**
     * Records the start line of every parser rule at the current indent level.
     * This ensures that most lines carrying source code are captured.
     * Block constructs additionally adjust the indent via their dedicated
     * {@code enter*} and {@code exit*} methods.
     *
     * @param ctx The parser rule context being entered
     */
    @Override public void enterEveryRule(ParserRuleContext ctx)
    {
        recordStart(ctx);
    }

    // ==================== Type declaration bodies ====================

    @Override public void enterClassBody(JavaParser.ClassBodyContext ctx)
    {
        enterBlock(ctx);
    }

    @Override public void exitClassBody(JavaParser.ClassBodyContext ctx)
    {
        exitBlock(ctx);
    }

    @Override public void enterInterfaceBody(JavaParser.InterfaceBodyContext ctx)
    {
        enterBlock(ctx);
    }

    @Override public void exitInterfaceBody(JavaParser.InterfaceBodyContext ctx)
    {
        exitBlock(ctx);
    }

    @Override public void enterEnumBody(JavaParser.EnumBodyContext ctx)
    {
        enterBlock(ctx);
    }

    @Override public void exitEnumBody(JavaParser.EnumBodyContext ctx)
    {
        exitBlock(ctx);
    }

    @Override public void enterRecordBody(JavaParser.RecordBodyContext ctx)
    {
        enterBlock(ctx);
    }

    @Override public void exitRecordBody(JavaParser.RecordBodyContext ctx)
    {
        exitBlock(ctx);
    }

    @Override public void enterAnnotationInterfaceBody(JavaParser.AnnotationInterfaceBodyContext ctx)
    {
        enterBlock(ctx);
    }

    @Override public void exitAnnotationInterfaceBody(JavaParser.AnnotationInterfaceBodyContext ctx)
    {
        exitBlock(ctx);
    }

    // ==================== Regular block ====================

    @Override public void enterBlock(JavaParser.BlockContext ctx)
    {
        enterBlock(ctx);
    }

    @Override public void exitBlock(JavaParser.BlockContext ctx)
    {
        exitBlock(ctx);
    }

    // ==================== Switch block ====================

    @Override public void enterSwitchBlock(JavaParser.SwitchBlockContext ctx)
    {
        enterBlock(ctx);
    }

    @Override public void exitSwitchBlock(JavaParser.SwitchBlockContext ctx)
    {
        exitBlock(ctx);
    }

    // ==================== Constructor body ====================

    @Override public void enterConstructorBody(JavaParser.ConstructorBodyContext ctx)
    {
        enterBlock(ctx);
    }

    @Override public void exitConstructorBody(JavaParser.ConstructorBodyContext ctx)
    {
        exitBlock(ctx);
    }

    // ==================== Array initializer ====================

    @Override public void enterArrayInitializer(JavaParser.ArrayInitializerContext ctx)
    {
        enterBlock(ctx);
    }

    @Override public void exitArrayInitializer(JavaParser.ArrayInitializerContext ctx)
    {
        exitBlock(ctx);
    }

    // ==================== Element value array initializer ====================

    @Override public void enterElementValueArrayInitializer(JavaParser.ElementValueArrayInitializerContext ctx)
    {
        enterBlock(ctx);
    }

    @Override public void exitElementValueArrayInitializer(JavaParser.ElementValueArrayInitializerContext ctx)
    {
        exitBlock(ctx);
    }
}
