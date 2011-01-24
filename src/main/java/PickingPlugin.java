import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.util.Collection;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class PickingPlugin implements ItemListener {
  GUI gui;

  /**
   * DOCUMENT ME!
   *
   * @param g DOCUMENT ME!
   */
  public void setParent(GUI g) {
    gui = g;
  }

  /**
   * DOCUMENT ME!
   *
   * @param e DOCUMENT ME!
   */
  @Override
  public void itemStateChanged(ItemEvent e) {
    if (e.getSource() == gui.vv.getPickedEdgeState()) {
      if (e.getStateChange() == ItemEvent.DESELECTED) {
        Edge edge = (Edge) e.getItem();
        edge.setSelected(false);
        edge.from.setSelected(false);
        edge.to.setSelected(false);
      } else if (e.getStateChange() == ItemEvent.SELECTED) {
        gui.vv.getPickedVertexState().clear();

        Edge edge = (Edge) e.getItem();
        edge.setSelected(true);
        edge.from.setSelected(true);
        edge.to.setSelected(true);
        gui.statusPanel.setStatusBar(edge);
      }
    } else if (e.getSource() == gui.vv.getPickedVertexState()) {
      if (e.getStateChange() == ItemEvent.DESELECTED) {
        Node node = (Node) e.getItem();
        node.setSelected(false);

        Collection<Node> neighbors = gui.embed.getGraph().getNeighbors(node);

        if (neighbors != null) {
          for (Node n : neighbors) {
            n.setNeighbor(false);
          }
        }
      } else if (e.getStateChange() == ItemEvent.SELECTED) {
        gui.vv.getPickedEdgeState().clear();

        Node node = (Node) e.getItem();
        node.setSelected(true);

        Collection<Node> neighbors = gui.embed.getGraph().getNeighbors(node);

        if (neighbors != null) {
          for (Node n : neighbors) {
            n.setNeighbor(true);
          }
        }

        gui.statusPanel.setStatusBar(node);
      }
    }
  }
}
