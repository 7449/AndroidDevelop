package com.md.reader.markdown.ext.kbd;

import com.md.reader.markdown.ext.kbd.internal.KeystrokeDelimiterProcessor;
import com.md.reader.markdown.ext.kbd.internal.KeystrokeNodeRenderer;
import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataHolder;


public class KeystrokeExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    private KeystrokeExtension() {
    }

    public static Extension create() {
        return new KeystrokeExtension();
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customDelimiterProcessor(new KeystrokeDelimiterProcessor());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if ("HTML".equals(rendererType)) {
            rendererBuilder.nodeRendererFactory(new KeystrokeNodeRenderer.Factory());
        }
    }
}
