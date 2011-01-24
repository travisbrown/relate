import org.apache.commons.collections15.Predicate;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Context;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class DirectionDisplayPredicate implements Predicate<Context<Graph<Node, Edge>, Edge>>//extends AbstractGraphPredicate<V,E>
 {
  /**
   * DOCUMENT ME!
   */
  protected boolean show_d;

  /**
   * DOCUMENT ME!
   */
  protected boolean show_u;

  /**
   * Creates a new DirectionDisplayPredicate object.
   *
   * @param show_d DOCUMENT ME!
   * @param show_u DOCUMENT ME!
   */
  public DirectionDisplayPredicate(boolean show_d, boolean show_u) {
    this.show_d = show_d;
    this.show_u = show_u;
  }

  /**
   * DOCUMENT ME!
   *
   * @param b DOCUMENT ME!
   */
  public void showDirected(boolean b) {
    show_d = b;
  }

  /**
   * DOCUMENT ME!
   *
   * @param b DOCUMENT ME!
   */
  public void showUndirected(boolean b) {
    show_u = b;
  }

  /**
   * DOCUMENT ME!
   *
   * @param context DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public boolean evaluate(Context<Graph<Node, Edge>, Edge> context) {
    Edge e = context.element;

    if (!(e.isBidirectional()) && show_d) {
      return true;
    }

    if (e.isBidirectional() && show_u) {
      return true;
    }

    return false;
  }
}
