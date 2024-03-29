package com.md.reader.markdown.ext.twitter.internal;

import com.md.reader.markdown.ext.twitter.Twitter;
import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.NodeTracker;
import com.vladsch.flexmark.util.sequence.BasedSequence;


public class TwitterNodePostProcessor extends NodePostProcessor {
    public TwitterNodePostProcessor() {
    }

    @Override
    public void process(NodeTracker state, Node node) {
        if (node instanceof Link) {
            Node previous = node.getPrevious();

            if (previous instanceof Text) {
                final BasedSequence chars = previous.getChars();

                //Se o nó anterior termina com '#' e é seguido pelo Link
                if (chars.endsWith("#") && chars.isContinuedBy(node.getChars())) {
                    //Remove o caractere '#' do nó anterior.
                    previous.setChars(chars.subSequence(0, chars.length() - 1));
                    Twitter videoLink = new Twitter((Link) node);
                    videoLink.takeChildren(node);
                    node.unlink();
                    previous.insertAfter(videoLink);
                    state.nodeRemoved(node);
                    state.nodeAddedWithChildren(videoLink);
                }
            }
        }
    }

    public static class Factory extends NodePostProcessorFactory {
        public Factory() {
            super(false);

            addNodes(Link.class);
        }

        @Override
        public NodePostProcessor create(Document document) {
            return new TwitterNodePostProcessor();
        }
    }
}
