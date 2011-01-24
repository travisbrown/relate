import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class KCoreCheckBoxActionListener implements ActionListener {
  GUI g;

  /**
   * Creates a new KCoreCheckBoxActionListener object.
   *
   * @param g DOCUMENT ME!
   */
  public KCoreCheckBoxActionListener(GUI g) {
    this.g = g;
  }

  private boolean checkAllSelected() {
    for (int i = 2; i < g.viewOptionsPanel.kcoreBox.getComponentCount(); i++) {
      Component c = g.viewOptionsPanel.kcoreBox.getComponent(i);

      if (c instanceof JCheckBox) {
        JCheckBox chkBox = (JCheckBox) c;

        if (!chkBox.isSelected()) {
          return false;
        }
      }
    }

    return true;
  }

  private boolean checkAnySelected() {
    for (int i = 2; i < g.viewOptionsPanel.kcoreBox.getComponentCount(); i++) {
      Component c = g.viewOptionsPanel.kcoreBox.getComponent(i);

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
    if (e.getSource() == g.viewOptionsPanel.allKCoreChkBox) {
      if (g.viewOptionsPanel.allKCoreChkBox.isSelected()) {
        for (int i = 2; i < g.viewOptionsPanel.kcoreBox.getComponentCount(); i++) {
          Component c = g.viewOptionsPanel.kcoreBox.getComponent(i);

          if (c instanceof JCheckBox) {
            JCheckBox chkBox = (JCheckBox) c;
            chkBox.setSelected(true);
          }
        }
      }
    } else if (e.getSource() == g.viewOptionsPanel.noneKCoreChkBox) {
      if (g.viewOptionsPanel.noneKCoreChkBox.isSelected()) {
        for (Component c : g.viewOptionsPanel.kcoreBox.getComponents()) {
          if (c instanceof JCheckBox) {
            JCheckBox chkBox = (JCheckBox) c;
            chkBox.setSelected(false);
          }
        }

        //recheck none check box
        g.viewOptionsPanel.noneKCoreChkBox.setSelected(true);
      }
    } else {
      boolean allChecked = checkAllSelected();

      if (allChecked) {
        g.viewOptionsPanel.allKCoreChkBox.setSelected(true);
      } else {
        g.viewOptionsPanel.allKCoreChkBox.setSelected(false);
      }

      boolean anyChecked = checkAnySelected();

      if (anyChecked) {
        g.viewOptionsPanel.noneKCoreChkBox.setSelected(false);
      } else {
        g.viewOptionsPanel.noneKCoreChkBox.setSelected(true);
      }
    }

    g.vv.repaint();
  }
}
