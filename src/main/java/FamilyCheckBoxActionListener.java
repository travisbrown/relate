import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import javax.swing.JCheckBox;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class FamilyCheckBoxActionListener implements ActionListener {
  GUI g;

  /**
   * Creates a new FamilyCheckBoxActionListener object.
   *
   * @param g DOCUMENT ME!
   */
  public FamilyCheckBoxActionListener(GUI g) {
    this.g = g;
  }

  private boolean checkAllSelected() {
    for (int i = 2; i < g.viewOptionsPanel.familyScrollPanel.getComponentCount(); i++) {
      Component c = g.viewOptionsPanel.familyScrollPanel.getComponent(i);

      if (c instanceof JCheckBox) {
        JCheckBox chkBox = (JCheckBox) c;

        if (!chkBox.isSelected()) {
          return false;
        }
      }
    }

    return true;
  }

  private void allFamiliesChkBoxActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_allFamiliesChkBoxActionPerformed
                                                                                  //boolean selected;

    if (g.viewOptionsPanel.allFamiliesChkBox.isSelected()) {
      for (int i = 2; i < g.viewOptionsPanel.familyScrollPanel.getComponentCount(); i++) {
        Component c = g.viewOptionsPanel.familyScrollPanel.getComponent(i);

        if (c instanceof JCheckBox) {
          JCheckBox chkBox = (JCheckBox) c;
          chkBox.setSelected(true);
        }
      }
    }

    //	        else
    //	        {
    //	            selected = false;
    //	        }
  } //GEN-LAST:event_allFamiliesChkBoxActionPerformed

  private boolean checkAnySelected() {
    for (int i = 2; i < g.viewOptionsPanel.familyScrollPanel.getComponentCount(); i++) {
      Component c = g.viewOptionsPanel.familyScrollPanel.getComponent(i);

      if (c instanceof JCheckBox) {
        JCheckBox chkBox = (JCheckBox) c;

        if (chkBox.isSelected()) {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * DOCUMENT ME!
   *
   * @param e DOCUMENT ME!
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == g.viewOptionsPanel.allFamiliesChkBox) {
      allFamiliesChkBoxActionPerformed(e);
    } else if (e.getSource() == g.viewOptionsPanel.noneFamilyChkBox) {
      if (g.viewOptionsPanel.noneFamilyChkBox.isSelected()) {
        for (Component c : g.viewOptionsPanel.familyScrollPanel.getComponents()) {
          if (c instanceof JCheckBox) {
            JCheckBox chkBox = (JCheckBox) c;
            chkBox.setSelected(false);
          }
        }

        //recheck nonebox
        g.viewOptionsPanel.noneFamilyChkBox.setSelected(true);
      }
    }
    else {
      boolean allChecked = checkAllSelected();

      if (allChecked) {
        g.viewOptionsPanel.allFamiliesChkBox.setSelected(true);
      } else {
        g.viewOptionsPanel.allFamiliesChkBox.setSelected(false);
      }

      //check if any selected
      boolean anyChecked = checkAnySelected();

      if (anyChecked) {
        g.viewOptionsPanel.noneFamilyChkBox.setSelected(false);
      } else {
        g.viewOptionsPanel.noneFamilyChkBox.setSelected(false);
      }
    }

    //if all families checked, take shortcut and included all families in view
    if (g.viewOptionsPanel.allFamiliesChkBox.isSelected()) {
      g.currentViewFamilies = (ArrayList<String>) g.includedFamilies.clone();
    } else {
      for (int i = 1; i < g.viewOptionsPanel.familyScrollPanel.getComponentCount(); i++) {
        Component c = g.viewOptionsPanel.familyScrollPanel.getComponent(i);

        if (c instanceof JCheckBox) {
          JCheckBox chkBox = (JCheckBox) c;
          String lastName = chkBox.getText();

          if (chkBox.isSelected()) {
            if (!(g.currentViewFamilies.contains(lastName))) {
              g.currentViewFamilies.add(lastName);
            }
          } else {
            if (g.currentViewFamilies.contains(lastName)) {
              g.currentViewFamilies.remove(lastName);
            }
          }
        }
      }
    }

    g.show_vertex.updateIncluded(g.currentViewFamilies);

    g.vv.repaint();
  }
}
