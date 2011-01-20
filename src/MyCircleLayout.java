import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.Graph;

public class MyCircleLayout<Node,Edge> extends CircleLayout<Node, Edge> {

	public MyCircleLayout(Graph<Node, Edge> g) {
		super(g);
		
	}
	
	@Override
	public Dimension getSize()
	{
		return super.getSize();
	}
	
	@Override
	public Graph<Node, Edge> getGraph()
	{
		return super.getGraph();
	}
	
	@Override
	public double getRadius()
	{
		return super.getRadius();
	}
	
	@Override
	public double getX(Node n)
	{
		return super.getX(n);
	}
	
	@Override
	public double getY(Node n)
	{
		return super.getY(n);
	}
	
	@Override
	public void initialize()
	{
		super.initialize();
	}

	@Override
	public void setSize(Dimension d)
	{
		super.setSize(d);
	}
	
	@Override
	public void setGraph(Graph<Node, Edge> g)
	{
		super.setGraph(g);
	}
	
	@Override
	public void setRadius(double r)
	{
		super.setRadius(r);
	}
	
	@Override
	public void setInitializer(Transformer<Node, Point2D> t)
	{
		super.setInitializer(t);
	}
	
	@Override
	public int hashCode()
	{
		return super.hashCode();
	}
	
	@Override
	public boolean isLocked(Node n)
	{
		return super.isLocked(n);
	}
	
	@Override
	public void lock(boolean arg0)
	{
		super.lock(arg0);
	}
	
	@Override
	public void reset()
	{
		super.reset();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setVertexOrder(List<Node> vertex_list) 
	{
		Node[] node_list = (Node[]) vertex_list.toArray();
		Arrays.sort(node_list);
		ArrayList<Node> firstHalf = new ArrayList<Node>();
		ArrayList<Node> secondHalf = new ArrayList<Node>();
		int i = 0;
		for(Node n : node_list)
		{
			if((i % 2) == 0)
			{
				firstHalf.add(n);
			}
			else
			{
				secondHalf.add(n);
			}
			i++;
		}
		ArrayList<Node> orderedList = new ArrayList<Node>();
		for(Node n : firstHalf)
		{
			orderedList.add(n);
		}
		for(Node n : secondHalf)
		{
			orderedList.add(n);
		}
		super.setVertexOrder(orderedList);
	}

}
