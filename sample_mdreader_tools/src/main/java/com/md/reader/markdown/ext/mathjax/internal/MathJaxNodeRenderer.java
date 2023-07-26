package com.md.reader.markdown.ext.mathjax.internal;

import com.md.reader.markdown.ext.mathjax.MathJax;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.HashSet;
import java.util.Set;


public class MathJaxNodeRenderer implements NodeRenderer {

    public MathJaxNodeRenderer() {
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(MathJax.class, MathJaxNodeRenderer.this::render));

        return set;
    }

    private void render(MathJax node, NodeRendererContext context, HtmlWriter html) {
        html.withAttr().attr("class", "math").tag("span");
        if (node.isInline()) {
            html.append("\\(");
        } else {
            html.append("$$");
        }
        context.renderChildren(node);
        if (node.isInline()) {
            html.append("\\)");
        } else {
            html.append("$$");
        }
        html.tag("/span");
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer create(final DataHolder options) {
            return new MathJaxNodeRenderer();
        }
    }
}
