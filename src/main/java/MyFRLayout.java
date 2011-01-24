import java.awt.Dimension;
import java.awt.geom.Point2D;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  *
 * @param <V> DOCUMENT ME!
 * @param <E> DOCUMENT ME!
 */
public class MyFRLayout<V, E> extends FRLayout<V, E> implements Layout<V, E> {
  /**
   * Creates a new MyFRLayout object.
   *
   * @param g DOCUMENT ME!
   */
  public MyFRLayout(Graph<V, E> g) {
    super(g);
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @Override
  public Graph<V, E> getGraph() {
    return super.getGraph();
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
   */
  @Override
  public void initialize() {
    super.initialize();
  }

  /**
   * DOCUMENT ME!
   *
   * @param arg0 DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @Override
  public boolean isLocked(V arg0) {
    return super.isLocked(arg0);
  }

  /**
   * DOCUMENT ME!
   *
   * @param arg0 DOCUMENT ME!
   * @param arg1 DOCUMENT ME!
   */
  @Override
  public void lock(V arg0, boolean arg1) {
    super.lock(arg0, arg1);
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
   * @param arg0 DOCUMENT ME!
   */
  @Override
  public void setGraph(Graph<V, E> arg0) {
    super.setGraph(arg0);
  }

  /**
   * DOCUMENT ME!
   *
   * @param arg0 DOCUMENT ME!
   */
  @Override
  public void setInitializer(Transformer<V, Point2D> arg0) {
    super.setInitializer(arg0);
  }

  /**
   * DOCUMENT ME!
   *
   * @param arg0 DOCUMENT ME!
   * @param arg1 DOCUMENT ME!
   */
  @Override
  public void setLocation(V arg0, Point2D arg1) {
    super.setLocation(arg0, arg1);
  }

  /**
   * DOCUMENT ME!
   */
  public void step() {
    super.step();
  }

  /**
   * DOCUMENT ME!
   *
   * @param arg0 DOCUMENT ME!
   */
  @Override
  public void setSize(Dimension arg0) {
    super.setSize(arg0);
  }

  /**
   * DOCUMENT ME!
   *
   * @param arg0 DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @Override
  public Point2D transform(V arg0) {
    return super.transform(arg0);
  }
}
