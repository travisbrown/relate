import java.awt.Component;

import java.util.ArrayList;

import javax.swing.JCheckBox;

import org.apache.commons.collections15.Predicate;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Context;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class VertexDisplayPredicate implements Predicate<Context<Graph<Node, Edge>, Node>> {
  /**
   * DOCUMENT ME!
   */
  protected ArrayList<String> includedNames;

  /**
   * DOCUMENT ME!
   */
  protected GUI gui;

  /**
   * Creates a new VertexDisplayPredicate object.
   *
   * @param includedNames DOCUMENT ME!
   * @param parent DOCUMENT ME!
   */
  public VertexDisplayPredicate(ArrayList<String> includedNames, GUI parent) {
    this.includedNames = includedNames;
    gui = parent;
  }

  /**
   * DOCUMENT ME!
   *
   * @param includedNames DOCUMENT ME!
   */
  public void updateIncluded(ArrayList<String> includedNames) {
    this.includedNames = includedNames;
  }

  /**
   * DOCUMENT ME!
   *
   * @param context DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public boolean evaluate(Context<Graph<Node, Edge>, Node> context) {
    Node n = context.element;
    ArrayList<Integer> kcoresIncluded = new ArrayList<Integer>();

    for (int i = 2; i < gui.viewOptionsPanel.kcoreBox.getComponentCount(); i++) {
      Component c = gui.viewOptionsPanel.kcoreBox.getComponent(i);

      if (c instanceof JCheckBox) {
        JCheckBox chkBox = (JCheckBox) c;

        if (chkBox.isSelected()) {
          int temp = Integer.parseInt(chkBox.getText());
          kcoresIncluded.add(temp);
        }
      }
    }

    for (String name : includedNames) {
      if (n.getLabel().contains(name)) {
        if (kcoresIncluded.contains(n.getCount())) {
          return true;
        }
      }
    }

    return false;
  }
}
