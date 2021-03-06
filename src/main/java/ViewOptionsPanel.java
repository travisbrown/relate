import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 
DOCUMENT ME!
 *
 * @author jonathanjekeli
 */
public class ViewOptionsPanel extends javax.swing.JPanel {
  GUI g;
  FamilyCheckBoxActionListener checkBoxActionListener;
  KCoreCheckBoxActionListener kcoreActionListener;

/** Creates new form ViewOptionsPanel */
  public ViewOptionsPanel(GUI g) {
    this.g = g;
    checkBoxActionListener = new FamilyCheckBoxActionListener(g);
    kcoreActionListener = new KCoreCheckBoxActionListener(g);
    initComponents();
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do
   * NOT modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    edgePanel = new javax.swing.JPanel();
    showDirectedChkBox = new javax.swing.JCheckBox();
    showBidirectionalChkBox = new javax.swing.JCheckBox();
    familiesPanel = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    familyScroller = new javax.swing.JScrollPane();
    familyScrollPanel = new javax.swing.Box(BoxLayout.Y_AXIS);
    allFamiliesChkBox = new javax.swing.JCheckBox();
    kcorePanel = new JPanel();
    kcoreBox = new Box(BoxLayout.Y_AXIS);
    kcoreScroller = new javax.swing.JScrollPane();
    allKCoreChkBox = new JCheckBox();
    noneFamilyChkBox = new JCheckBox();
    noneKCoreChkBox = new JCheckBox();
    findFamilies = new JTextField("Enter family name");

    edgePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Edges"));
    kcorePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("K-Cores"));

    showDirectedChkBox.setSelected(true);
    showDirectedChkBox.setText("Show Directed Edges");
    showDirectedChkBox.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
          showDirectedChkBoxActionPerformed(evt);
        }
      });

    showBidirectionalChkBox.setSelected(true);
    showBidirectionalChkBox.setText("Show Bidirectional Edges");
    showBidirectionalChkBox.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
          showBidirectionalChkBoxActionPerformed(evt);
        }
      });

    org.jdesktop.layout.GroupLayout edgePanelLayout =
      new org.jdesktop.layout.GroupLayout(edgePanel);
    edgePanel.setLayout(edgePanelLayout);
    edgePanelLayout.setHorizontalGroup(edgePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                                       .add(edgePanelLayout.createSequentialGroup()
                                                                            .addContainerGap()
                                                                            .add(edgePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                                                                                 .add(showDirectedChkBox)
                                                                                                 .add(showBidirectionalChkBox))
                                                                            .addContainerGap(43,
                                                                                              Short.MAX_VALUE)));
    edgePanelLayout.setVerticalGroup(edgePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                                     .add(edgePanelLayout.createSequentialGroup()
                                                                          .add(showDirectedChkBox)
                                                                          .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                                          .add(showBidirectionalChkBox)
                                                                          .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                                                            Short.MAX_VALUE)));

    familiesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Families"));

    jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 10));
    jLabel1.setText("Select name groups to display: ");

    allFamiliesChkBox.setSelected(true);
    allFamiliesChkBox.setText("All");
    allFamiliesChkBox.addActionListener(checkBoxActionListener);

    allKCoreChkBox.setSelected(true);
    allKCoreChkBox.setText("All");
    allKCoreChkBox.addActionListener(kcoreActionListener);

    noneKCoreChkBox.setText("None");
    noneKCoreChkBox.addActionListener(kcoreActionListener);
    noneFamilyChkBox.setText("None");
    noneFamilyChkBox.addActionListener(checkBoxActionListener);

    updateDisplay();

    kcoreScroller.setViewportView(kcoreBox);
    familyScroller.setViewportView(familyScrollPanel);

    findFamilies.setForeground(Color.LIGHT_GRAY);
    findFamilies.addFocusListener(new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
          if (findFamilies.getText().contains("Enter family name")) {
            findFamilies.setText("");
            findFamilies.setForeground(Color.BLACK);
          }
        }

        @Override
        public void focusLost(FocusEvent e) {
          if (findFamilies.getText().equals("")) {
            findFamilies.setText("Enter family name");
            findFamilies.setForeground(Color.LIGHT_GRAY);
          }
        }
      });

    findFamilies.getDocument().addDocumentListener(new DocumentListener() {
        public void changedUpdate(DocumentEvent e) {
          findFamiliesActionPerformed(e);
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
          findFamiliesActionPerformed(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
          findFamiliesActionPerformed(e);
        }
      });

    org.jdesktop.layout.GroupLayout kcorePanelLayout =
      new org.jdesktop.layout.GroupLayout(kcorePanel);
    kcorePanel.setLayout(kcorePanelLayout);
    kcorePanelLayout.setHorizontalGroup(kcorePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                                         .add(kcorePanelLayout.createSequentialGroup()
                                                                               .addContainerGap()
                                                                               .add(kcorePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                                                                                     .add(kcoreScroller,
                                                                                                           org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                                                                                           210,
                                                                                                           org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                                                               .add(52, 52, 52)));
    kcorePanelLayout.setVerticalGroup(kcorePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                                       .add(kcorePanelLayout.createSequentialGroup()
                                                                             .add(kcoreScroller,
                                                                                   org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                                                                   200,
                                                                                   org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                                             .addContainerGap(40,
                                                                                               Short.MAX_VALUE)));

    org.jdesktop.layout.GroupLayout familiesPanelLayout =
      new org.jdesktop.layout.GroupLayout(familiesPanel);
    familiesPanel.setLayout(familiesPanelLayout);
    familiesPanelLayout.setHorizontalGroup(familiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                                               .add(familiesPanelLayout.createSequentialGroup()
                                                                                        .addContainerGap()
                                                                                        .add(familiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                                                                                                 .add(familyScroller,
                                                                                                                       org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                                                                                                       210,
                                                                                                                       org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                                                                                 .add(findFamilies))
                                                                                        .add(52,
                                                                                              52, 52)));
    familiesPanelLayout.setVerticalGroup(familiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                                             .add(familiesPanelLayout.createSequentialGroup()
                                                                                      .add(findFamilies)
                                                                                      .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                                                      .add(familyScroller,
                                                                                            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                                                                            200,
                                                                                            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                                                      .addContainerGap(40,
                                                                                                        Short.MAX_VALUE)));

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                     .add(layout.createSequentialGroup().addContainerGap()
                                                 .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING,
                                                                                  false)
                                                             .add(org.jdesktop.layout.GroupLayout.LEADING,
                                                                   edgePanel,
                                                                   org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                                   org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                                   Short.MAX_VALUE)
                                                             .add(org.jdesktop.layout.GroupLayout.LEADING,
                                                                   familiesPanel,
                                                                   org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                                   org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                                   Short.MAX_VALUE)
                                                             .add(org.jdesktop.layout.GroupLayout.LEADING,
                                                                   kcorePanel,
                                                                   org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                                   260, Short.MAX_VALUE))
                                                 .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                                   Short.MAX_VALUE)));
    layout.setVerticalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                   .add(layout.createSequentialGroup().addContainerGap()
                                               .add(edgePanel,
                                                     org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                                     org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                     org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                               .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                               .add(familiesPanel,
                                                     org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                                     org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                     org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                               .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                               .add(kcorePanel,
                                                     org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                                     org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                     org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                               .addContainerGap(224, Short.MAX_VALUE)));
  } // </editor-fold>//GEN-END:initComponents

  private void findFamiliesActionPerformed(DocumentEvent e) {
    String findText = findFamilies.getText();

    if (!(findText.equals("") || findText.equals("Enter family name"))) {
      for (int i = 2; i < familyScrollPanel.getComponentCount(); i++) {
        Component c = familyScrollPanel.getComponent(i);

        if (c instanceof JCheckBox) {
          JCheckBox chk = (JCheckBox) c;

          //not contains, but "startsWith" and convert to lowercase
          if (chk.getText().toLowerCase().startsWith(findText.toLowerCase())) {
            chk.setVisible(true);
          } else {
            chk.setVisible(false);
          }
        }
      }
    } else {
      for (int i = 2; i < familyScrollPanel.getComponentCount(); i++) {
        Component c = familyScrollPanel.getComponent(i);

        if (c instanceof JCheckBox) {
          JCheckBox chk = (JCheckBox) c;
          chk.setVisible(true);
        }
      }
    }

    familyScrollPanel.revalidate();
    familyScrollPanel.repaint();
  }

  /**
   * DOCUMENT ME!
   */
  protected void updateDisplay() {
    addFamilyCheckBoxes();
    updateKCoreCheckBoxes();
  }

  private void updateKCoreCheckBoxes() {
    for (Component c : kcoreBox.getComponents()) {
      kcoreBox.remove(c);
    }

    kcoreBox.add(allKCoreChkBox);
    kcoreBox.add(noneKCoreChkBox);

    int max;

    try {
      max = g.embed.findMaxKCore();
    } catch (NullPointerException e) {
      max = 0;
    }

    for (int i = 1; i <= max; i++) {
      String kcore = String.valueOf(i);
      JCheckBox check = new JCheckBox(kcore, true);
      check.addActionListener(kcoreActionListener);
      kcoreBox.add(check);
    }

    kcoreBox.revalidate();
    kcoreBox.repaint();
  }

  private void addFamilyCheckBoxes() {
    for (Component c : familyScrollPanel.getComponents()) {
      familyScrollPanel.remove(c);
    }

    familyScrollPanel.add(allFamiliesChkBox);
    familyScrollPanel.add(noneFamilyChkBox);

    for (int i = 0; i < g.includedFamilies.size(); i++) {
      String name = g.includedFamilies.get(i);
      JCheckBox check = new JCheckBox(name, true);
      check.addActionListener(checkBoxActionListener);
      familyScrollPanel.add(check);
    }

    familyScrollPanel.revalidate();
    familyScrollPanel.repaint();
  }

  private void showBidirectionalChkBoxActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_showBidirectionalChkBoxActionPerformed

    if (showBidirectionalChkBox.isSelected()) {
      g.show_edge.showUndirected(true);
    } else {
      g.show_edge.showUndirected(false);
    }

    g.vv.repaint();
  } //GEN-LAST:event_showBidirectionalChkBoxActionPerformed

  private void showDirectedChkBoxActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_showDirectedChkBoxActionPerformed

    if (showDirectedChkBox.isSelected()) {
      g.show_edge.showDirected(true);
    } else {
      g.show_edge.showDirected(false);
    }

    g.vv.repaint();
  } //GEN-LAST:event_showDirectedChkBoxActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  /**
   * DOCUMENT ME!
   */
  protected javax.swing.JCheckBox allFamiliesChkBox;

  /**
   * DOCUMENT ME!
   */
  protected javax.swing.JCheckBox allKCoreChkBox;

  /**
   * DOCUMENT ME!
   */
  protected javax.swing.JCheckBox noneKCoreChkBox;

  /**
   * DOCUMENT ME!
   */
  protected javax.swing.JCheckBox noneFamilyChkBox;

  /**
   * DOCUMENT ME!
   */
  protected javax.swing.JPanel edgePanel;

  /**
   * DOCUMENT ME!
   */
  protected javax.swing.JPanel familiesPanel;

  /**
   * DOCUMENT ME!
   */
  protected Box familyScrollPanel;

  /**
   * DOCUMENT ME!
   */
  protected javax.swing.JScrollPane familyScroller;
  private javax.swing.JLabel jLabel1;

  /**
   * DOCUMENT ME!
   */
  protected javax.swing.JCheckBox showBidirectionalChkBox;

  /**
   * DOCUMENT ME!
   */
  protected javax.swing.JCheckBox showDirectedChkBox;

  /**
   * DOCUMENT ME!
   */
  protected javax.swing.JPanel kcorePanel;

  /**
   * DOCUMENT ME!
   */
  protected javax.swing.JScrollPane kcoreScroller;

  /**
   * DOCUMENT ME!
   */
  protected Box kcoreBox;

  /**
   * DOCUMENT ME!
   */
  protected JTextField findFamilies;

  // End of variables declaration//GEN-END:variables
}
