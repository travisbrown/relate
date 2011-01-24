import java.io.*;

import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class StepBloomfield {
  public static final int NUM_CONNECTIONS = 0;
  public static final int DEGREE_CENTRALITY = 1;
  public static final int BETWEEN_CENTRALITY = 2;
  ArrayList<Node> nodes = new ArrayList<Node>();
  ArrayList<Node> familyNodes = new ArrayList<Node>();
  ArrayList<Node> nonFamilyNodes = new ArrayList<Node>();
  ArrayList<Node> allNodes = new ArrayList<Node>();
  HashMap<String, Node> nodeTable = new HashMap<String, Node>();
  boolean all;
  boolean family = true;
  boolean toggle = false;
  ArrayList<Edge> edgesInitial = new ArrayList<Edge>();
  ArrayList<Edge> edges = new ArrayList<Edge>();
  ArrayList<Node> familyEdges = new ArrayList<Node>();
  ArrayList<Node> nonFamilyEdges = new ArrayList<Node>();
  ArrayList<Node> allEdges = new ArrayList<Node>();
  boolean directed = true;
  int countType = 0;
  Graph<Node, Edge> g;

  /**
   * Creates a new StepBloomfield object.
   *
   * @param stream DOCUMENT ME!
   */
  public StepBloomfield(InputStream stream) {
    g = new DirectedSparseGraph<Node, Edge>();
    loadData(stream);
  }

  /**
   * Creates a new StepBloomfield object.
   *
   * @param filePath DOCUMENT ME!
   */
  public StepBloomfield(String filePath) {
    g = new DirectedSparseGraph<Node, Edge>();
    loadData(filePath);
  }

  /**
   * DOCUMENT ME!
   *
   * @param all DOCUMENT ME!
   * @param family DOCUMENT ME!
   */
  public void setVariables(boolean all, boolean family) {
    this.all = all;
    this.family = family;
  }

  private void addNodesToGraph() {
    for (Node n : nodes) {
      g.addVertex(n);
    }

    for (Edge e : edges) {
      if (directed) {
        g.addEdge(e, e.from, e.to, EdgeType.DIRECTED);
      } else {
        g.addEdge(e, e.from, e.to, EdgeType.UNDIRECTED);
      }
    }
  }

  /**
   * prompts user to select folder and then reads contacts.txt
   *
   * @param filePath DOCUMENT ME!
   */
  public void loadData(String filePath) {
    try {
      this.loadData(new FileInputStream(filePath));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @param stream DOCUMENT ME!
   */
  public void loadData(InputStream stream) {
    //String connection = null;
    try {
      DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
      Document doc = docBuilder.parse(stream);

      doc.getDocumentElement().normalize();

      NodeList nodeList = doc.getDocumentElement().getChildNodes();

      String fromNodeName = null;
      String toNodeName = null;
      String letterNum = null;

      for (int i = 0; i < nodeList.getLength(); i++) {
        org.w3c.dom.Node n = nodeList.item(i);
        String nodeName = n.getNodeName();

        if (nodeName.contains("addressee")) {
          fromNodeName = n.getTextContent();
        } else if (nodeName.contains("edge")) {
          letterNum = n.getAttributes().getNamedItem("letter").getNodeValue().trim();
        } else if (nodeName.contains("ref")) {
          toNodeName = n.getTextContent();
        }

        if ((fromNodeName != null) && (toNodeName != null) && (letterNum != null)) {
          if (!(fromNodeName.equals("") || toNodeName.equals(""))) {
            Edge e =
              new Edge(new Node(fromNodeName.trim()), new Node(toNodeName.trim()), letterNum);
            edgesInitial.add(e);
          }

          fromNodeName = null;
          toNodeName = null;
          letterNum = null;
        }
      }

      for (Edge e1 : edgesInitial) {
        boolean found = false;

        for (Edge e2 : edges) {
          if (e1.equals(e2)) {
            found = true;
            e2.increment();

            break;
          }
        }

        if (!found) {
          //filter out blank node edges
          if (!(e1.from.getLabel().trim().equals("") || e1.to.getLabel().trim().equals(""))) {
            addEdge(e1);
          }
        }
      }

      for (int i = 0; i < nodes.size(); i++) {
        nodes.get(i).calcDegreeCentrality(nodes.size());
      }

      addNodesToGraph();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (ParserConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SAXException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @param e DOCUMENT ME!
   */
  void addEdge(Edge e) {
    // Filter out unnecessary words
    String fromLabel = e.from.getLabel();
    String toLabel = e.to.getLabel();

    if (fromLabel.equals(toLabel)) {
      return;
    }

    if (all == true) {
    } else {
      if (family == true) {
        if (!ignoreWord(fromLabel) || !ignoreWord(toLabel))
          return; // Family
      } else {
        if (ignoreWord(fromLabel) || ignoreWord(toLabel))
          return; // Non-family
      }
    }

    //add nodes/edges to specific arraylists for later use
    //		  if (!ignoreWord(fromLabel) || !ignoreWord(toLabel))
    //		  {
    //			  nonFamilyNodes.add(from);
    //			  nonFamilyEdges.add(e);
    //		  }
    //		  else if (ignoreWord(fromLabel) || ignoreWord(toLabel))
    //		  {
    //			  familyNodes.add(from);
    //			  familyEdges.add(e);
    //		  }
    //		  allNodes.add(from);
    //		  allEdges.add(e);
    Node from = findNode(e.from);
    Node to = findNode(e.to);

    from.increment();
    to.increment();

    Edge edge = new Edge(from, to, e.letterNum);
    edge.increment();

    edges.add(edge);
  }

  String[] ignore =
    { "bloomfield", "cousin", "blomfield", "gloverelizabeth", "fricker", "hill", "southey" }; // Use this to exclude people

  /**
   * DOCUMENT ME!
   *
   * @param what DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  boolean ignoreWord(String what) {
    for (int i = 0; i < ignore.length; i++) {
      //    if (what.equals(ignore[i])) {
      if (what.toLowerCase().contains(ignore[i])) {
        return true;
      }
    }

    return false;
  }

  /**
   * DOCUMENT ME!
   *
   * @param node DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  Node findNode(Node node) {
    Node n = (Node) nodeTable.get(node.getLabel());

    if (n == null) {
      return addNode(node);
    }

    return n;
  }

  /**
   * DOCUMENT ME!
   *
   * @param node DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  Node addNode(Node node) {
    Node n = new Node(node.getLabel());
    nodeTable.put(n.getLabel(), n);
    nodes.add(n);

    return n;
  }

  /**
   * DOCUMENT ME!
   */
  public void clear() {
    //halt draw operation
    //running = false;
    edgesInitial.clear();
    edges.clear();
    nodes.clear();
    nodeTable.clear();

    //		if(all)
    //		{
    //			nodes = allNodes;
    //			edges = allEdges;
    //		}
    //		else if(family)
    //		{
    //			nodes = familyNodes;
    //			edges = familyEdges;
    //		}
    //		else
    //		{
    //			nodes = nonFamilyNodes;
    //			edges = nonFamilyEdges;
    //		}
    //		
    //		addNodesToGraph();
  }

  /**
   * DOCUMENT ME!
   *
   * @param type DOCUMENT ME!
   */
  public void setCountType(int type) {
    countType = type;

    for (Node n : nodes) {
      n.setCountType(countType);
    }

    for (Node n : g.getVertices()) {
      n.setCountType(countType);
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public Graph<Node, Edge> getGraph() {
    return g;
  }

  /**
   * DOCUMENT ME!
   *
   * @param directed DOCUMENT ME!
   */
  public void setGraphType(boolean directed) {
    this.directed = directed;

    if (directed) {
      g = new DirectedSparseGraph<Node, Edge>();
    } else {
      g = new UndirectedSparseGraph<Node, Edge>();
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int findMaxKCore() {
    int max = nodes.get(0).getCount();

    for (Node n : nodes) {
      int temp = n.getCount();

      if (temp > max) {
        max = temp;
      }
    }

    return max;
  }
}
