import java.awt.Dimension;
import java.awt.geom.Point2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.Graph;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  *
 * @param <Node> DOCUMENT ME!
 * @param <Edge> DOCUMENT ME!
 */
public class MyCircleLayout<Node, Edge> extends CircleLayout<Node, Edge> {
  /**
   * Creates a new MyCircleLayout object.
   *
   * @param g DOCUMENT ME!
   */
  public MyCircleLayout(Graph<Node, Edge> g) {
    super(g);
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @Override
  public Dimension getSize() {
    return super.getSize();
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @Override
  public Graph<Node, Edge> getGraph() {
    return super.getGraph();
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @Override
  public double getRadius() {
    return super.getRadius();
  }

  /**
   * DOCUMENT ME!
   *
   * @param n DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @Override
  public double getX(Node n) {
    return super.getX(n);
  }

  /**
   * DOCUMENT ME!
   *
   * @param n DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @Override
  public double getY(Node n) {
    return super.getY(n);
  }

  /**
   * DOCUMENT ME!
   */
  @Override
  public void initialize() {
    super.initialize();
  }

  /**
   * DOCUMENT ME!
   *
   * @param d DOCUMENT ME!
   */
  @Override
  public void setSize(Dimension d) {
    super.setSize(d);
  }

  /**
   * DOCUMENT ME!
   *
   * @param g DOCUMENT ME!
   */
  @Override
  public void setGraph(Graph<Node, Edge> g) {
    super.setGraph(g);
  }

  /**
   * DOCUMENT ME!
   *
   * @param r DOCUMENT ME!
   */
  @Override
  public void setRadius(double r) {
    super.setRadius(r);
  }

  /**
   * DOCUMENT ME!
   *
   * @param t DOCUMENT ME!
   */
  @Override
  public void setInitializer(Transformer<Node, Point2D> t) {
    super.setInitializer(t);
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /**
   * DOCUMENT ME!
   *
   * @param n DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @Override
  public boolean isLocked(Node n) {
    return super.isLocked(n);
  }

  /**
   * DOCUMENT ME!
   *
   * @param arg0 DOCUMENT ME!
   */
  @Override
  public void lock(boolean arg0) {
    super.lock(arg0);
  }

  /**
   * DOCUMENT ME!
   */
  @Override
  public void reset() {
    super.reset();
  }

  /**
   * DOCUMENT ME!
   *
   * @param vertex_list DOCUMENT ME!
   */
  @SuppressWarnings("unchecked")
  @Override
  public void setVertexOrder(List<Node> vertex_list) {
    Node[] node_list = (Node[]) vertex_list.toArray();
    Arrays.sort(node_list);

    ArrayList<Node> firstHalf = new ArrayList<Node>();
    ArrayList<Node> secondHalf = new ArrayList<Node>();
    int i = 0;

    for (Node n : node_list) {
      if ((i % 2) == 0) {
        firstHalf.add(n);
      } else {
        secondHalf.add(n);
      }

      i++;
    }

    ArrayList<Node> orderedList = new ArrayList<Node>();

    for (Node n : firstHalf) {
      orderedList.add(n);
    }

    for (Node n : secondHalf) {
      orderedList.add(n);
    }

    super.setVertexOrder(orderedList);
  }
}
