import java.awt.Dimension;
import java.awt.geom.Point2D;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;

public class MyFRLayout<V, E> extends FRLayout<V, E> implements Layout<V, E> {

	
	public MyFRLayout(Graph<V, E> g) {
		super(g);
		
	}

	@Override
	public Graph<V, E> getGraph() {
		return super.getGraph();
	}

	@Override
	public Dimension getSize() {
		return super.getSize();
	}

	@Override
	public void initialize() {
		super.initialize();

	}

	@Override
	public boolean isLocked(V arg0) {
		return super.isLocked(arg0);
	}

	@Override
	public void lock(V arg0, boolean arg1) {
		super.lock(arg0, arg1);

	}

	@Override
	public void reset() {
		super.reset();
	}

	@Override
	public void setGraph(Graph<V, E> arg0) {
		super.setGraph(arg0);
	}

	@Override
	public void setInitializer(Transformer<V, Point2D> arg0) {
		super.setInitializer(arg0);
	}

	@Override
	public void setLocation(V arg0, Point2D arg1) {
		super.setLocation(arg0, arg1);

	}

	public void step()
	{
		super.step();
	}
	
	@Override
	public void setSize(Dimension arg0) {
		super.setSize(arg0);

	}

	@Override
	public Point2D transform(V arg0) {
		return super.transform(arg0);
	}

}
