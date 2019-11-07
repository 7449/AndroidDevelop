package com.md.reader.markdown.ext.kbd.internal;

import com.md.reader.markdown.ext.kbd.Keystroke;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.HashSet;
import java.util.Set;


public class KeystrokeNodeRenderer implements NodeRenderer {

    public KeystrokeNodeRenderer() {
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(Keystroke.class, (node, context, html) -> KeystrokeNodeRenderer.this.render(node, html)));

        return set;
    }

    private void render(Keystroke node, HtmlWriter html) {
        html.withAttr().tag("kbd");
        html.append(node.getText().trim());
        html.tag("/kbd");
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer create(final DataHolder options) {
            return new KeystrokeNodeRenderer();
        }
    }
}
