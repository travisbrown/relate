import java.io.Serializable;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
class Node implements Comparable<Object>, Serializable {
  private String label;
  private int count;
  private double degreeCentrality;
  private boolean selected;
  private boolean neighbor;
  private double betweenCentrality;
  private String lastName;
  private double sortValue;
  private int countType;
  private volatile int hashCode = 0;
  private double value;

  /**
   * Creates a new Node object.
   *
   * @param label DOCUMENT ME!
   */
  Node(String label) {
    this.label = label;
    count = 0;
    degreeCentrality = 0;
    selected = false;
    neighbor = false;
    betweenCentrality = 0;
    setCountType(StepBloomfield.NUM_CONNECTIONS);

    findFamilyName();
  }

  private void findFamilyName() {
    int commaIndex = label.indexOf(",");

    if (commaIndex != -1) {
      lastName = label.substring(0, commaIndex).trim();
    } else if (!(label.equals(""))) {
      lastName = label.trim();
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @Override
  public int hashCode() {
    final int multiplier = 23;

    if (hashCode == 0) {
      int code = 133;

      code = (multiplier * code) + label.hashCode();

      hashCode = code;
    }

    return hashCode;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public boolean isNeighbor() {
    return neighbor;
  }

  /**
   * DOCUMENT ME!
   *
   * @param neighbor DOCUMENT ME!
   */
  public void setNeighbor(boolean neighbor) {
    this.neighbor = neighbor;
  }

  /**
   * DOCUMENT ME!
   *
   * @param other DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Node)) {
      return false;
    } else {
      Node n = (Node) other;

      if (this.label.equals(n.label)) {
        return true;
      }

      return false;
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String toString() {
    String html_string_prefix = "<html><font color = ";
    String html_string_close = "</font></html>";
    String htmlString =
      (html_string_prefix + "black>" + label + "<br><DIV ALIGN=CENTER><font color = black>");
    String countLabel = "";

    if (countType == StepBloomfield.DEGREE_CENTRALITY) {
      htmlString += (degreeCentrality + " ");
      countLabel = "Degree Centrality";
    } else if (countType == StepBloomfield.BETWEEN_CENTRALITY) {
      htmlString += (betweenCentrality + " ");
      countLabel = "Betweenness Centrality";
    } else if (countType == StepBloomfield.NUM_CONNECTIONS) {
      htmlString += (count + " ");
      countLabel = "Connections";
    }

    if (selected) {
      htmlString = htmlString + countLabel + "</font></DIV>" + html_string_close;

      return htmlString;
    }

    return "";
  }

  /**
   * DOCUMENT ME!
   *
   * @param selected DOCUMENT ME!
   */
  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public boolean isSelected() {
    return selected;
  }

  /**
   * DOCUMENT ME!
   */
  public void increment() {
    count++;
  }

  /**
   * DOCUMENT ME!
   *
   * @param numNodes DOCUMENT ME!
   */
  public void calcDegreeCentrality(int numNodes) {
    degreeCentrality = (double) count / (double) numNodes;
  }

  /**
   * DOCUMENT ME!
   *
   * @param o DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @Override
  public int compareTo(Object o) {
    value();

    if (o instanceof Node) {
      Node n = (Node) o;
      n.value();

      if (this.getValue() < n.getValue()) {
        return -1;
      } else {
        return 1;
      }
    }

    return 0;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public double getBetweenCentrality() {
    return betweenCentrality;
  }

  /**
   * DOCUMENT ME!
   *
   * @param betweenCentrality DOCUMENT ME!
   */
  public void setBetweenCentrality(double betweenCentrality) {
    this.betweenCentrality = betweenCentrality;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * DOCUMENT ME!
   *
   * @param lastName DOCUMENT ME!
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getLabel() {
    return label;
  }

  /**
   * DOCUMENT ME!
   *
   * @param label DOCUMENT ME!
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getCount() {
    return count;
  }

  /**
   * DOCUMENT ME!
   *
   * @param count DOCUMENT ME!
   */
  public void setCount(int count) {
    this.count = count;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public double getDegreeCentrality() {
    return degreeCentrality;
  }

  /**
   * DOCUMENT ME!
   *
   * @param degreeCentrality DOCUMENT ME!
   */
  public void setDegreeCentrality(double degreeCentrality) {
    this.degreeCentrality = degreeCentrality;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public double getSortValue() {
    return sortValue;
  }

  /**
   * DOCUMENT ME!
   *
   * @param sortValue DOCUMENT ME!
   */
  public void setSortValue(double sortValue) {
    this.sortValue = sortValue;
  }

  /**
   * DOCUMENT ME!
   *
   * @param countType DOCUMENT ME!
   */
  public void setCountType(int countType) {
    this.countType = countType;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getCountType() {
    return countType;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public double getValue() {
    return value;
  }

  /**
   * DOCUMENT ME!
   *
   * @param value DOCUMENT ME!
   */
  public void setValue(double value) {
    this.value = value;
  }

  private void value() {
    switch (countType) {
    case StepBloomfield.NUM_CONNECTIONS:
      value = count;

      break;

    case StepBloomfield.DEGREE_CENTRALITY:
      value = degreeCentrality;

      break;

    case StepBloomfield.BETWEEN_CENTRALITY:
      value = betweenCentrality;

      break;

    default:
      value = count;

      break;
    }
  }
}
