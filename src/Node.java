import java.io.Serializable;

class Node implements Comparable<Object>, Serializable{
	
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
		  if(commaIndex != -1)
		  {
			  lastName = label.substring(0, commaIndex).trim();
		  }
		  else if(!(label.equals("")))
		  {
			  lastName = label.trim();
		  }
		  
		}

	  
	@Override
	  public int hashCode () {

			final int multiplier = 23;

			if (hashCode == 0) {

				int code = 133;

				code = multiplier*code + label.hashCode();

				hashCode = code;

			}

			return hashCode;

		}

	  
	  public boolean isNeighbor() {
		return neighbor;
	}

	public void setNeighbor(boolean neighbor) {
		this.neighbor = neighbor;
	}

	@Override
	public boolean equals(Object other)
	  {
		if(!(other instanceof Node))
		{
			return false;
		}
		else
		{
			Node n = (Node) other;
			if(this.label.equals(n.label))
			  {
				  return true;
			  }
			  return false;
		}
	  }
	  
	  public String toString()
	  {
		  String html_string_prefix = "<html><font color = ";
			String html_string_close = "</font></html>";
			String htmlString = (html_string_prefix + "black>" + label +
					"<br><DIV ALIGN=CENTER><font color = black>");
		  String countLabel = "";
		  if(countType == StepBloomfield.DEGREE_CENTRALITY)
		  {
			  htmlString += degreeCentrality + " ";
			  countLabel = "Degree Centrality";
		  }
		  else if(countType == StepBloomfield.BETWEEN_CENTRALITY)
		  {
			  htmlString += betweenCentrality + " ";
			  countLabel = "Betweenness Centrality";
		  }
		  else if(countType == StepBloomfield.NUM_CONNECTIONS)
		  {
			  htmlString += count + " ";
			  countLabel = "Connections";
		  }
		  if(selected)
		  {
			  htmlString = htmlString + countLabel 
			  	+ "</font></DIV>" + html_string_close;
			  return htmlString;
		  }
		  return "";
	  }
	  
	  public void setSelected(boolean selected)
	  {
		  this.selected = selected;
	  }
	  
	  public boolean isSelected()
	  {
		  return selected;
	  }
	  
	  public void increment() {
		  count++;
	  }
	  
	  public void calcDegreeCentrality(int numNodes)
	  {
		  degreeCentrality = (double) count / (double) numNodes;
	  }

	@Override
	public int compareTo(Object o) {
		value();
		if(o instanceof Node)
		{
			Node n = (Node) o;
			n.value();
			if(this.getValue() < n.getValue())
			{
				return -1;
			}
			else
			{
				return 1;
			}
		}
		return 0;
	}
	  
	  public double getBetweenCentrality() {
		return betweenCentrality;
	}

	public void setBetweenCentrality(double betweenCentrality) {
		this.betweenCentrality = betweenCentrality;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getDegreeCentrality() {
		return degreeCentrality;
	}

	public void setDegreeCentrality(double degreeCentrality) {
		this.degreeCentrality = degreeCentrality;
	}

	public double getSortValue() {
		return sortValue;
	}

	public void setSortValue(double sortValue) {
		this.sortValue = sortValue;
	}

	public void setCountType(int countType) {
		this.countType = countType;
	}

	public int getCountType() {
		return countType;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	private void value()
	{
		switch(countType) {
			case StepBloomfield.NUM_CONNECTIONS: value = count; break;
			case StepBloomfield.DEGREE_CENTRALITY: value = degreeCentrality; break;
			case StepBloomfield.BETWEEN_CENTRALITY: value = betweenCentrality; break;
			default: value = count; break;
		}
	}

}