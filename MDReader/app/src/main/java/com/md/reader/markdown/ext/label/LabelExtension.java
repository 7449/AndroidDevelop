package com.md.reader.markdown.ext.label;

import com.md.reader.markdown.ext.label.internal.LabelDelimiterProcessor;
import com.md.reader.markdown.ext.label.internal.LabelNodeRenderer;
import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataHolder;


public class LabelExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    private LabelExtension() {
    }

    public static Extension create() {
        return new LabelExtension();
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customDelimiterProcessor(new LabelDelimiterProcessor());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if ("HTML".equals(rendererType)) {
            rendererBuilder.nodeRendererFactory(new LabelNodeRenderer.Factory());
        }
    }
}
