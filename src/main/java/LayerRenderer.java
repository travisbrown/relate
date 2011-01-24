import java.util.ConcurrentModificationException;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.renderers.BasicRenderer;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  *
 * @param <V> DOCUMENT ME!
 * @param <E> DOCUMENT ME!
 */
public class LayerRenderer<V, E> extends BasicRenderer<V, E> {
  /**
   * DOCUMENT ME!
   *
   * @param renderContext DOCUMENT ME!
   * @param layout DOCUMENT ME!
   */
  @Override
  public void render(RenderContext<V, E> renderContext, Layout<V, E> layout) {
    // paint all the edges
    try {
      for (E e : layout.getGraph().getEdges()) {
        renderEdge(renderContext, layout, e);
        renderEdgeLabel(renderContext, layout, e);
      }
    } catch (ConcurrentModificationException cme) {
      renderContext.getScreenDevice().repaint();
    }

    // paint all the vertices
    try {
      for (V v : layout.getGraph().getVertices()) {
        renderVertex(renderContext, layout, v);
      }
    } catch (ConcurrentModificationException cme) {
      renderContext.getScreenDevice().repaint();
    }

    // paint all the vertices labels
    try {
      for (V v : layout.getGraph().getVertices()) {
        renderVertexLabel(renderContext, layout, v);
      }
    } catch (ConcurrentModificationException cme) {
      renderContext.getScreenDevice().repaint();
    }
  }
}
