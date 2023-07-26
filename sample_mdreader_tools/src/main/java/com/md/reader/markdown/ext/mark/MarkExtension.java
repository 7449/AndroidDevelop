package com.md.reader.markdown.ext.mark;

import com.md.reader.markdown.ext.mark.internal.MarkDelimiterProcessor;
import com.md.reader.markdown.ext.mark.internal.MarkNodeRenderer;
import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataHolder;


public class MarkExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    private MarkExtension() {
    }

    public static Extension create() {
        return new MarkExtension();
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customDelimiterProcessor(new MarkDelimiterProcessor());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if ("HTML".equals(rendererType)) {
            rendererBuilder.nodeRendererFactory(options -> new MarkNodeRenderer());
        }
    }
}
