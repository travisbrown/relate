import java.io.Serializable;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class Edge implements Serializable {
  Node from;
  Node to;
  float len;
  int count;
  boolean selected;
  boolean neighbor;
  boolean bidirectional;
  double betweenCentr;
  String letterNum;
  private volatile int hashCode = 0;

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

      code = (multiplier * code) + from.getLabel().hashCode() + to.getLabel().hashCode();

      hashCode = code;
    }

    return hashCode;
  }

  /**
   * DOCUMENT ME!
   *
   * @param o DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Edge)) {
      return false;
    } else {
      Edge e = (Edge) o;

      if ((e.from.getLabel().equals(this.to.getLabel()))
           && (e.to.getLabel().equals(this.from.getLabel()))) {
        e.setBidirectional(true);
      }

      boolean equal =
        (((e.from.getLabel().equals(this.from.getLabel()))
          && (e.to.getLabel().equals(this.to.getLabel())))
         || ((e.from.getLabel().equals(this.to.getLabel()))
             && (e.to.getLabel().equals(this.from.getLabel()))));

      return equal;
    }
  }

  /**
   * Creates a new Edge object.
   *
   * @param from DOCUMENT ME!
   * @param to DOCUMENT ME!
   * @param letterNum DOCUMENT ME!
   */
  Edge(Node from, Node to, String letterNum) {
    this.from = from;
    this.to = to;
    selected = false;
    neighbor = false;
    bidirectional = false;
    this.letterNum = letterNum;
    betweenCentr = 0;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public double getBetweenCentr() {
    return betweenCentr;
  }

  /**
   * DOCUMENT ME!
   *
   * @param betweenCentr DOCUMENT ME!
   */
  public void setBetweenCentr(double betweenCentr) {
    this.betweenCentr = betweenCentr;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public boolean isBidirectional() {
    return bidirectional;
  }

  /**
   * DOCUMENT ME!
   *
   * @param bidirectional DOCUMENT ME!
   */
  public void setBidirectional(boolean bidirectional) {
    this.bidirectional = bidirectional;
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
   * @return DOCUMENT ME!
   */
  public String toString() {
    if (selected) {
      String html_string_prefix = "<html><font color = \"";

      String html_string_close = "</font></html>";

      return (html_string_prefix + "black" + "\">" + count + " connections" + html_string_close);
    }

    return "";
  }

  //  	public void relax() {
  //  		float vx = (float) (to.position.x - from.position.x);
  //  		float vy = (float) (to.position.y - from.position.y);
  //	    float d = PApplet.mag(vx, vy);
  //	    if (d > 0) {
  //	      float f = (len - d) / (d * 3);
  //	      float dx = f * vx;
  //	      float dy = f * vy;
  //	      to.dx += dx;
  //	      to.dy += dy;
  //	      from.dx -= dx;
  //	      from.dy -= dy;
  //	    }
  //  	}

  //  void draw() {
  //    parent.stroke(eColor);
  //    parent.strokeWeight((float) 1.00);
  //    
  //    if (eColor == step.edgeColor){
  //    	parent.strokeWeight((float) 0.15 * count);
  //        parent.line((float)from.position.x, (float)from.position.y, 
  //        		(float)to.position.x, (float)to.position.y);
  //    } else if (eColor == step.selectEdgeColor){
  //       parent.strokeWeight((float) 0.35 * count);
  //       parent.line((float)from.position.x, (float)from.position.y, 
  //    		   (float)to.position.x, (float)to.position.y);
  //    }
  //    
  //    if ((step.edgeSelection == this))
  //    {
  //    	float x = (float) ((from.position.x + to.position.x) / 2.0);
  //  		x += 15; //give slight offset so is not directly on line
  //  		float y = (float) ((from.position.y + to.position.y) / 2.0);
  //    	parent.text(count + " connections", x, y);
  //    }
  //  }
}
