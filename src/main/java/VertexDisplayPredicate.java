
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JCheckBox;

import org.apache.commons.collections15.Predicate;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Context;

public class VertexDisplayPredicate 
    	implements Predicate<Context<Graph<Node,Edge>,Node>>
    {
        protected ArrayList<String> includedNames;
        protected GUI gui;
        
        public VertexDisplayPredicate(ArrayList<String> includedNames, GUI parent)
        {
        	this.includedNames = includedNames;
        	gui = parent;
        }
        
        public void updateIncluded(ArrayList<String> includedNames)
        {
            this.includedNames = includedNames;
        }
        
        
        public boolean evaluate(Context<Graph<Node,Edge>,Node> context)
        {
        	Node n = context.element;
        	ArrayList<Integer> kcoresIncluded = new ArrayList<Integer>();
        	for(int i = 2; i < gui.viewOptionsPanel.kcoreBox.getComponentCount(); i++)
        	{
        		Component c = gui.viewOptionsPanel.kcoreBox.getComponent(i);
        		if(c instanceof JCheckBox)
        		{
        			JCheckBox chkBox = (JCheckBox) c;
        			if(chkBox.isSelected())
        			{
        				int temp = Integer.parseInt(chkBox.getText());
        				kcoresIncluded.add(temp);
        			}
        		}
        	}
            for(String name : includedNames)
            {
            	if(n.getLabel().contains(name))
            	{
            		if(kcoresIncluded.contains(n.getCount()))
            		{
            			return true;
            		}
            	}
            }
            return false;
        }
    }

