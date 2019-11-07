package com.md.reader.markdown.ext.mathjax;

import com.md.reader.markdown.ext.mathjax.internal.MathJaxDelimiterProcessor;
import com.md.reader.markdown.ext.mathjax.internal.MathJaxNodeRenderer;
import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataHolder;


public class MathJaxExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    private MathJaxExtension() {
    }

    public static Extension create() {
        return new MathJaxExtension();
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customDelimiterProcessor(new MathJaxDelimiterProcessor());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if ("HTML".equals(rendererType)) {
            rendererBuilder.nodeRendererFactory(options -> new MathJaxNodeRenderer());
        }
    }
}
