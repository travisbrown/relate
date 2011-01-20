import java.awt.Color;

public class ColorChooser {
	
	public static Color pickColor(int countType, Node n)
	{
		if(countType == StepBloomfield.BETWEEN_CENTRALITY)
		{
			return betweenCentrColor(n.getBetweenCentrality());
		}
		else if(countType == StepBloomfield.DEGREE_CENTRALITY)
		{
			return degreeCentrColor(n.getDegreeCentrality());
		}
		else if(countType == StepBloomfield.NUM_CONNECTIONS)
		{
			return numConnectionsColor(n.getCount());
		}
		return null;
	}

	private static Color numConnectionsColor(int size) {
		if(size == 1)
		{
			return Color.CYAN;
		}
		else if(size == 2)
		{
			return Color.ORANGE;
		}
		else if(size == 3)
		{
			return Color.MAGENTA;
		}
		else if(size >=4 && size <=6)
		{
			return Color.GRAY;
		}
		else if(size >=7 && size <=9)
		{
			return Color.GREEN;
		}
		else if(size >=10 && size <=12)
		{
			float[] color = Color.RGBtoHSB(134, 201, 252, null);
			return Color.getHSBColor(color[0], color[1], color[2]);
		}
		else if(size >=13 && size <=15)
		{
			return Color.PINK;
		}
		else
		{
			return Color.WHITE;
		}
	}

	private static Color degreeCentrColor(double size) {
		if(size > 0.0 && size <= 0.15)
		{
			return Color.CYAN;
		}
		else if(size > 0.15 && size <= 0.3)
		{
			return Color.ORANGE;
		}
		else if(size > 0.3 && size <= 0.45)
		{
			return Color.MAGENTA;
		}
		else if(size > 0.45 && size <= 0.6)
		{
			return Color.GRAY;
		}
		else if(size > 0.6 && size <= 0.7)
		{
			return Color.GREEN;
		}
		else if(size > 0.7 && size <= 0.8)
		{
			float[] color = Color.RGBtoHSB(134, 201, 252, null);
			return Color.getHSBColor(color[0], color[1], color[2]);
		}
		else if(size > 0.8 && size <= 0.9)
		{
			return Color.PINK;
		}
		else
		{
			return Color.WHITE;
		}
	}

	private static Color betweenCentrColor(double size) {
		if(size == 0)
		{
			return Color.CYAN;
		}
		else if(size > 0 && size <= 5)
		{
			return Color.ORANGE;
		}
		else if(size > 5 && size <= 10)
		{
			return Color.MAGENTA;
		}
		else if(size > 10 && size <= 15)
		{
			return Color.GRAY;
		}
		else if(size > 15 && size <= 20)
		{
			return Color.GREEN;
		}
		else if(size > 20 && size <= 25)
		{
			float[] color = Color.RGBtoHSB(134, 201, 252, null);
			return Color.getHSBColor(color[0], color[1], color[2]);
		}
		else if(size > 25 && size <= 30)
		{
			return Color.PINK;
		}
		else
		{
			return Color.WHITE;
		}
	}
	
}
