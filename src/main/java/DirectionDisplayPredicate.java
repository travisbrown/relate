import org.apache.commons.collections15.Predicate;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Context;

public class DirectionDisplayPredicate 
    	implements Predicate<Context<Graph<Node,Edge>,Edge>>
    	//extends AbstractGraphPredicate<V,E>
    {
        protected boolean show_d;
        protected boolean show_u;
        
        public DirectionDisplayPredicate(boolean show_d, boolean show_u)
        {
            this.show_d = show_d;
            this.show_u = show_u;
        }
        
        public void showDirected(boolean b)
        {
            show_d = b;
        }
        
        public void showUndirected(boolean b)
        {
            show_u = b;
        }
        
        public boolean evaluate(Context<Graph<Node,Edge>,Edge> context)
        {
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
