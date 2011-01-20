import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;


import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.importance.BetweennessCentrality;
import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.algorithms.layout.util.RandomLocationTransformer;
import edu.uci.ics.jung.algorithms.layout.util.Relaxer;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Context;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.MultiLayerTransformer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.layout.PersistentLayout;
import edu.uci.ics.jung.visualization.layout.PersistentLayoutImpl;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import edu.uci.ics.jung.visualization.transform.MutableTransformer;

public class GUI extends JFrame implements ActionListener {

	//CONSTANTS_____________________
	private final static int CIRCLE = 0;
	private final static int SPRING = 1;
	private final static int ISOMLAYOUT = 2;
	private final static int KKLAYOUT = 3;
	private final static int FRLAYOUT = 4;
	//______________________________
	
	StepBloomfield embed;
	JPanel mainPanel;
	JLabel showLabel, nodeNameLabel;
	VisualizationViewer<Node,Edge> vv;
	DefaultModalGraphMouse<Node, Edge> gm;
	PersistentLayoutImpl<Node, Edge> persistLayout;
	JMenuBar menuBar;
	JMenu fileMenu, optionsMenu, helpMenu, mouseOptionSubMenu, 
		displaySubMenu, magnitudeSubMenu, familyOptionsMenu;
	JCheckBoxMenuItem directedItem;
	JMenuItem saveItem, loadItem, resetItem, changeSourceItem, exportItem, helpItem, quitItem;
	JRadioButtonMenuItem familyOnly, nonFamilyOnly, allOnly;
	JRadioButtonMenuItem circleItem, frItem, kkItem, springItem, isomItem;
	JRadioButtonMenuItem numConnectionsItem, degreeCentrItem, betweenCentrItem;
	JRadioButtonMenuItem pickingItem, transformingItem;
	ButtonGroup magnitudeGroup, mouseGroup, displayGroup, familyGroup;
	StatusPanel statusPanel;

	SaveFileNamesDialog saveFiles;
	ViewOptionsPanel viewOptionsPanel;
	
	DirectionDisplayPredicate show_edge;
	VertexDisplayPredicate show_vertex;
	
	BetweennessCentrality<Node, Edge> ranker;
	
	PickingPlugin pickPlug = new PickingPlugin();
	ArrayList<String> includedFamilies = new ArrayList<String>();
	ArrayList<String> currentViewFamilies = new ArrayList<String>();
	
	int width = 900;
	int height = 650;
	private boolean directed = true;
	private static final int BUFFER = 44;
	private int currentLayout;
	
	
	private String filePath;
	
	public GUI() {
		super("Graph View");
		
		this.setSize(width, height);
        setLayout(new BorderLayout());
        
        //JMenu setup
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        optionsMenu = new JMenu("Options");
        helpMenu = new JMenu("Help");
        menuBar.add(fileMenu);
        menuBar.add(optionsMenu);
        menuBar.add(helpMenu);
        
        
        changeSourceItem = new JMenuItem("Change Source File");      
        saveItem = new JMenuItem("Save");
        loadItem = new JMenuItem("Load");
        exportItem = new JMenuItem("Export");
        quitItem = new JMenuItem("Quit");
        
        fileMenu.add(changeSourceItem);
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(exportItem);
        fileMenu.add(quitItem);
        
        //option menu set up
        directedItem = new JCheckBoxMenuItem("Directed Graph", true);
        familyOptionsMenu = new JMenu("Show");
        familyGroup = new ButtonGroup();
        familyOnly= new JRadioButtonMenuItem("Family Only", true);
        nonFamilyOnly= new JRadioButtonMenuItem("Non-Family Only");
        allOnly= new JRadioButtonMenuItem("All");
        familyGroup.add(familyOnly);
        familyGroup.add(nonFamilyOnly);
        familyGroup.add(allOnly);
        familyOptionsMenu.add(familyOnly);
        familyOptionsMenu.add(nonFamilyOnly);
        familyOptionsMenu.add(allOnly);
        
        displaySubMenu = new JMenu("Layout");
        circleItem = new JRadioButtonMenuItem("Circle", true);
        frItem = new JRadioButtonMenuItem("Force");
        kkItem = new JRadioButtonMenuItem("Kamada-Kawai");
        springItem = new JRadioButtonMenuItem("Spring");
        isomItem = new JRadioButtonMenuItem("ISOM");
        displayGroup = new ButtonGroup();
        displayGroup.add(circleItem);
        displayGroup.add(frItem);
        displayGroup.add(kkItem);
        displayGroup.add(springItem);
        displayGroup.add(isomItem);
        displaySubMenu.add(circleItem);
        displaySubMenu.add(frItem);
        displaySubMenu.add(kkItem);
        displaySubMenu.add(springItem);
        displaySubMenu.add(isomItem);
        
        magnitudeSubMenu = new JMenu("Magnitude Type");
        numConnectionsItem = new JRadioButtonMenuItem("# of connections", true);
        degreeCentrItem = new JRadioButtonMenuItem("Degree Centrality");
        betweenCentrItem = new JRadioButtonMenuItem("Betweenness Centrality");
        magnitudeGroup = new ButtonGroup();
        magnitudeGroup.add(numConnectionsItem);
        magnitudeGroup.add(degreeCentrItem);
        magnitudeGroup.add(betweenCentrItem);
        magnitudeSubMenu.add(numConnectionsItem);
        magnitudeSubMenu.add(degreeCentrItem);
        magnitudeSubMenu.add(betweenCentrItem);
        
        mouseOptionSubMenu = new JMenu("Mouse Mode");
        pickingItem = new JRadioButtonMenuItem("Picking", true);
        transformingItem = new JRadioButtonMenuItem("Transforming");
        mouseGroup = new ButtonGroup();
        mouseGroup.add(pickingItem);
        mouseGroup.add(transformingItem);
        mouseOptionSubMenu.add(pickingItem);
        mouseOptionSubMenu.add(transformingItem);
        
        resetItem = new JMenuItem("Reset");
        
        optionsMenu.add(directedItem);
        optionsMenu.add(familyOptionsMenu);
        optionsMenu.add(displaySubMenu);
        optionsMenu.add(magnitudeSubMenu);
        optionsMenu.add(mouseOptionSubMenu);
        optionsMenu.add(resetItem);
        
        //help menu setup
        helpItem = new JMenuItem("Help");
        helpMenu.add(helpItem);
        
        addActionsListeners();
        
        this.setJMenuBar(menuBar);
        
        mainPanel = new JPanel();
  	  	mainPanel.setSize(new Dimension(width, height));
  	  	
//  	  	scroll = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
//  	  	scroll.add(mainPanel);
//  	  	scroll.setSize(new Dimension(width, height));
  	  	
  	  	statusPanel = new StatusPanel();
  		add(mainPanel, BorderLayout.CENTER);
		viewOptionsPanel = new ViewOptionsPanel(this);
		add(statusPanel, BorderLayout.SOUTH);
		add(viewOptionsPanel, BorderLayout.EAST);
	}
	
	private void addActionsListeners() {
		
        directedItem.addActionListener(this);
        resetItem.addActionListener(this);
        saveItem.addActionListener(this);
        loadItem.addActionListener(this);
        changeSourceItem.addActionListener(this);
        familyOnly.addActionListener(this);
        nonFamilyOnly.addActionListener(this);
        allOnly.addActionListener(this);
        circleItem.addActionListener(this);
        frItem.addActionListener(this);
        kkItem.addActionListener(this);
        springItem.addActionListener(this);
        isomItem.addActionListener(this);
        numConnectionsItem.addActionListener(this);
        degreeCentrItem.addActionListener(this);
        betweenCentrItem.addActionListener(this);
        pickingItem.addActionListener(this);
        transformingItem.addActionListener(this);
        exportItem.addActionListener(this);
        helpItem.addActionListener(this);
        quitItem.addActionListener(this);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GUI g = new GUI();
		//get file to read data in from 
        g.embed = g.loadSourceFile();
        g.setupGraph();
        g.pack();
		g.setVisible(true);
	    
	   

	}

	private void setupGraph()
	{
		if(embed == null)
        {
        	System.exit(0);
        }
        Dimension layoutSize = new Dimension(mainPanel.getWidth() - BUFFER,
        		mainPanel.getHeight() - BUFFER);
        persistLayout = new PersistentLayoutImpl<Node, Edge>
        	(new MyCircleLayout<Node, Edge>(embed.getGraph()));
        persistLayout.setSize(layoutSize);
        currentLayout = CIRCLE;
        persistLayout.setInitializer
        	(new RandomLocationTransformer<Node>(layoutSize,0));
        vv = 
        	new VisualizationViewer<Node,Edge>(persistLayout); 
        vv.setPreferredSize(new Dimension
        			(mainPanel.getWidth() - BUFFER, mainPanel.getHeight() - BUFFER));
        vv.setRenderer(new LayerRenderer<Node,Edge>());
        setupGraphTransformers();
    
        gm = new DefaultModalGraphMouse<Node, Edge>();
        pickPlug.setParent(this);
        gm.setMode(ModalGraphMouse.Mode.PICKING);
        vv.setGraphMouse(gm);
        
        mainPanel.add(vv);
	}
	
	private void resetAllOptions()
	{
		currentLayout = CIRCLE;
		circleItem.setSelected(true);
		familyOnly.setSelected(true);
		numConnectionsItem.setSelected(true);
		pickingItem.setSelected(true);
	}
	
	private StepBloomfield loadSourceFile() {
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "XML Files (*.xml)", "xml");
	    chooser.setFileFilter(filter);
	    chooser.setDialogTitle("Please choose a file to input connections from: ");
	    int returnVal = chooser.showOpenDialog(this);
	    
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       filePath = chooser.getSelectedFile().getAbsolutePath();
	       return new StepBloomfield(filePath);
	    }
	    else if(returnVal == JFileChooser.CANCEL_OPTION) 
		{
	    	System.exit(0);
	    }
	    return null;
	}
	
	private void setupGraphTransformers() {
		
		final Font selectedFont = new Font("Selected Font", Font.BOLD, 13);
        Transformer<Node, Font> fontTransformer =
        		new Transformer<Node, Font>() { 
        	public Font transform(Node n) {
        		return selectedFont;
        	}
        };
        	
        Transformer<Node, Shape> nodeSizeTransformer =
    		new Transformer<Node, Shape>() { 
        	public Shape transform(Node n) {
        		
        		int size = 0;
        		if(numConnectionsItem.isSelected())
        		{
        			size = (int) (12 * Math.log(2+n.getCount()));
            
        		}
        		else if(degreeCentrItem.isSelected())
        		{
        			size = 12 * (int) Math.log(n.getDegreeCentrality()*40 + 2);
        		}
        		else if(betweenCentrItem.isSelected())
        		{
        			size = 12 * (int) (Math.log((n.getBetweenCentrality()+1) * 2) + 2);
        		}
        		
        		if(size < 2)
        		{
        			size = 2;
        		}
        		
        		return new Ellipse2D.Float((float)(-1 * size / 2.0), 
        				(float)(-1 * size / 2.0), 
        				size, size);
        	}
        };
        
        Transformer<Node, Paint> nodeFillTransformer = new Transformer<Node, Paint>() {
			public Paint transform(Node n) {
				if(n.isSelected())
				{
					return Color.RED;
				}
				else if(n.isNeighbor())
				{
					return Color.YELLOW;
				}
				else
				{
					return ColorChooser.pickColor(embed.countType, n);
				}	
			}	
        };
        
        Transformer<Edge, Paint> edgeFillTransformer = new Transformer<Edge, Paint>() {
			public Paint transform(Edge e) {
				if(e.isSelected() || e.isNeighbor())
				{
					return Color.BLACK;
				}
				return Color.LIGHT_GRAY;	
			}     	
        };
        
        Transformer<Edge, Stroke> edgeStrokeTransformer = new Transformer<Edge, Stroke>() {
			public Stroke transform(Edge e) {
				Stroke stroke = new BasicStroke(2.0f, BasicStroke.CAP_ROUND,  
                        BasicStroke.JOIN_MITER, 15.0f, 
                        new float[] {(float) 10.0,(float) 10.0}, (float) 5.0);              
			
				if((e.isBidirectional() && directed) || !directed)
				{
					return new BasicStroke();
				}
				return stroke;
			}   	
        };

        Transformer<Node, String> vertexToolTipTransformer = new Transformer<Node, String>() {
			public String transform(Node n) {	
				return n.getLabel();	
			}
        };
        
        Transformer<Edge, String> edgeToolTipTransformer = new Transformer<Edge, String>() {
			public String transform(Edge edge) {
				return edge.toString();				
			}       	
        };
        
        Transformer<Node, String> stringLabelTransformer = new Transformer<Node, String>() {
			public String transform(Node n) {			
				return n.toString();
			}     	
        };
        
        show_edge = new DirectionDisplayPredicate(true, true);
        
        currentViewFamilies = repopulateIncludedFamilies();
        show_vertex = new VertexDisplayPredicate(currentViewFamilies, this);
        
        computeBetweenness();
                
        vv.getRenderContext().setEdgeIncludePredicate(show_edge);
        vv.getRenderContext().setVertexIncludePredicate(show_vertex);
        vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
        vv.getRenderContext().setEdgeDrawPaintTransformer(edgeFillTransformer);
        vv.getRenderContext().setVertexFillPaintTransformer(nodeFillTransformer);
        vv.getRenderContext().setVertexShapeTransformer(nodeSizeTransformer);
        vv.getRenderContext().setVertexFontTransformer(fontTransformer);
        vv.getRenderContext().setVertexLabelTransformer(stringLabelTransformer); 
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<Edge>()); 
        vv.getRenderContext().setEdgeArrowPredicate(new Predicate<Context<Graph<Node, Edge>, Edge>>(){

            public boolean evaluate(Context<Graph<Node, Edge>,Edge> context){
            	if(context.graph.getEdgeType(context.element) == EdgeType.DIRECTED)
            	{
	            	if(context.element.isBidirectional())
	            	{
	            		return false;
	            	}
	            	return true;
            	}
            	else
            	{
            		return false;
            	}
            }

        });
        //vv.setToolTipText("<html><center>Use the mouse wheel to zoom<p>");
        vv.setVertexToolTipTransformer(vertexToolTipTransformer);
        vv.setEdgeToolTipTransformer(edgeToolTipTransformer);
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
        
        vv.getPickedVertexState().addItemListener(pickPlug);
        vv.getPickedEdgeState().addItemListener(pickPlug);
        
		}

	private void computeBetweenness() {
		ranker = 
        	new BetweennessCentrality<Node, Edge>(embed.getGraph());
		ranker.setRemoveRankScoresOnFinalize(false);
        ranker.evaluate();
        for(Node n : embed.getGraph().getVertices())
        {
        	n.setBetweenCentrality(ranker.getVertexRankScore(n));
        }
        for(Edge e : embed.getGraph().getEdges())
        {
        	e.setBetweenCentr(ranker.getEdgeRankScore(e));
        }
        for(Node n : embed.nodes)
        {
        	n.setBetweenCentrality(ranker.getVertexRankScore(n));
        }
        for(Edge e : embed.edges)
        {
        	e.setBetweenCentr(ranker.getEdgeRankScore(e));
        }
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == familyOnly)
		{
			embed.setVariables(false, true);
			resetDisplay(directed);

	        
		}
		else if(e.getSource() == nonFamilyOnly)
		{
			embed.setVariables(false, false);
			resetDisplay(directed);

	        
		}
		else if(e.getSource() == allOnly)
		{
			embed.setVariables(true, false);
			resetDisplay(directed);


		}
		else if(e.getSource() == circleItem)
		{
			currentLayout = CIRCLE;
			displayChange(currentLayout);
		}
		else if(e.getSource() == frItem)
		{
			currentLayout = FRLAYOUT;
			displayChange(currentLayout);
		}
		else if(e.getSource() == kkItem)
		{
			currentLayout = KKLAYOUT;
			displayChange(currentLayout);
		}
		else if(e.getSource() == springItem)
		{
			currentLayout = SPRING;
			displayChange(currentLayout);
		}
		else if(e.getSource() == isomItem)
		{
			currentLayout = ISOMLAYOUT;
			displayChange(currentLayout);
		}
		
		else if(e.getSource() == pickingItem)
		{
			gm.setMode(Mode.PICKING);
		}
		else if(e.getSource() == transformingItem)
		{
			gm.setMode(Mode.TRANSFORMING);
		}
		
		else if(e.getSource() == directedItem)
		{
			if(directedItem.isSelected())
			{
				directed = true;
			}
			else
			{
				directed = false;
			}
			resetDisplay(directed);
		}
		
		else if(e.getSource() == numConnectionsItem)
		{
			
			embed.setCountType(StepBloomfield.NUM_CONNECTIONS);
			if(currentLayout == CIRCLE)
			{
				persistLayout = new PersistentLayoutImpl<Node, Edge>
				(new MyCircleLayout<Node, Edge>(embed.getGraph()));
				persistLayout.initialize();
			}
			vv.setGraphLayout(persistLayout);
			vv.repaint();
		}
		else if(e.getSource() == degreeCentrItem)
		{
			embed.setCountType(StepBloomfield.DEGREE_CENTRALITY);
			if(currentLayout == CIRCLE)
				if(currentLayout == CIRCLE)
				{
					persistLayout = new PersistentLayoutImpl<Node, Edge>
					(new MyCircleLayout<Node, Edge>(embed.getGraph()));
					persistLayout.initialize();
				}
			vv.setGraphLayout(persistLayout);
			vv.repaint();
		}
		else if(e.getSource() == betweenCentrItem)
		{
			embed.setCountType(StepBloomfield.BETWEEN_CENTRALITY);
			if(currentLayout == CIRCLE)
			{
				persistLayout = new PersistentLayoutImpl<Node, Edge>
				(new MyCircleLayout<Node, Edge>(embed.getGraph()));
				persistLayout.initialize();
			}
			vv.setGraphLayout(persistLayout);
			vv.repaint();
		}
		else if(e.getSource() == saveItem)
		{
			saveGraph();
		}
		else if(e.getSource() == resetItem)
		{
			resetActions();
		}
		else if(e.getSource() == loadItem)
		{
			restoreGraph();
		}
		else if(e.getSource() == exportItem)
		{
			writeJPEGImage();
		}
		else if(e.getSource() == helpItem)
		{
			HelpDialog helpDialog = new HelpDialog(this, false);
			helpDialog.show();
			
		}
		else if(e.getSource() == changeSourceItem)
		{
			embed = loadSourceFile();
	        mainPanel.removeAll();
	        setupGraph();
	        resetAllOptions();
			currentViewFamilies = repopulateIncludedFamilies();
			show_vertex.updateIncluded(currentViewFamilies);
	        mainPanel.revalidate();
	        this.repaint();
		}
		else if(e.getSource() == quitItem)
		{
			String saveBeforeExit = "Would you like to save before exiting?";
			int returnVal = JOptionPane.showConfirmDialog(this, saveBeforeExit);
			if(returnVal == JOptionPane.YES_OPTION)
			{
				saveGraph();
			}
			else if(returnVal == JOptionPane.NO_OPTION)
			{
				System.exit(0);
			}
		}
	}
	
	/**
     * copy the visible part of the graph to a file as a jpeg image
     * @param file
     */
    private void writeJPEGImage() {
		JFileChooser fileSelect = new JFileChooser();
		fileSelect.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "JPG Files (*.jpg)", ".jpg");
		fileSelect.setFileFilter(filter);
		fileSelect.setDialogTitle("Export");
		
		int response = fileSelect.showSaveDialog(this);
		if(response == JFileChooser.APPROVE_OPTION)
		{
			String path = fileSelect.getSelectedFile().getAbsolutePath() + ".jpg";
			File file = new File(path);
	        int width = vv.getWidth();
	        int height = vv.getHeight();

	        BufferedImage bi = new BufferedImage(width, height,
	                BufferedImage.TYPE_INT_RGB);
	        Graphics2D graphics = bi.createGraphics();
	        vv.paint(graphics);
	        graphics.dispose();
	        
	        try {
	            ImageIO.write(bi, "jpg", file);
	        } catch (Exception e) {
	            e.printStackTrace();
			}
		}
    }
	
	private void restoreGraph()
	{
		JFileChooser openFiles = new JFileChooser();
		openFiles.setMultiSelectionEnabled(true);
		openFiles.setDialogTitle("Please select the 3 files necessary for graph opening");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "Text Files (*.txt), Output Files (*.out), Serialized Object File (*.ser)",
		        "txt", "out", "ser");
		openFiles.setFileFilter(filter);
		
		if(openFiles.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			if(openFiles.getSelectedFiles().length != 3)
			{
				JOptionPane.showMessageDialog(openFiles, 
						"Please select three files");
			}
			else
			{
				load(openFiles.getSelectedFiles());
			}
		}
	}
	
	private void load(File[] files)
	{
		FileInputStream fileStream;
		try {
			
			String objectFileName = null;
			String layoutFileName = null;
			String optionsFileName = null;
			
			for(File file: files)
			{
				if(file.getName().contains(".ser"))
				{
					objectFileName = file.getAbsolutePath();
				}
				else if(file.getName().contains(".out"))
				{
					layoutFileName = file.getAbsolutePath();
				}
				else if(file.getName().contains(".txt"))
				{
					optionsFileName = file.getAbsolutePath();
				}
			}
			
			if(objectFileName == null || layoutFileName == null 
					|| optionsFileName == null)
			{
				JOptionPane.showMessageDialog(this, "One of more invalid files selected.  Try again.");
			}
			else
			{
				fileStream = new FileInputStream(objectFileName);
		
				ObjectInputStream os = new ObjectInputStream(fileStream);
		
				Object gr = os.readObject();
				
				try
				{
					embed.g = (DirectedSparseGraph<Node, Edge>) gr;
					embed.directed = true;
				}
				catch(ClassCastException e)
				{
					embed.g = (UndirectedSparseGraph<Node, Edge>) gr;
					embed.directed = false;
				}
		
				embed.nodes.clear();
				for(Node n : embed.g.getVertices())
				{
					embed.nodes.add(n);
				}
				embed.edges.clear();
				for(Edge e : embed.g.getEdges())
				{
					embed.edges.add(e);
				}
				
				persistLayout.setGraph(embed.getGraph());
		
				persistLayout.restore(layoutFileName);
				
				//repopulate names for check box
				currentViewFamilies = repopulateIncludedFamilies();
				show_vertex.updateIncluded(currentViewFamilies);

				
				//find options to change checkboxes on menu to appropriate items
				changeOptions(optionsFileName);
				
				vv.repaint();
			}
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void changeOptions(String optionsFileName) {
		Scanner read;
		try {
			
			read = new Scanner(new File(optionsFileName));
			String readIn = read.nextLine();
			//check if directed
			if(readIn.trim().equals("directed"))
			{
				directedItem.setSelected(true);
				embed.directed = true;
			}
			else
			{
				directedItem.setSelected(false);
				embed.directed = false;
			}
			
			readIn = read.nextLine();
			//check family options
			if(readIn.trim().equals("all"))
			{
				allOnly.setSelected(true);
				embed.all = true;
				embed.family = false;
			}
			else if(readIn.trim().equals("family"))
			{
				familyOnly.setSelected(true);
				embed.all = false;
				embed.family = true;
			}
			else
			{
				nonFamilyOnly.setSelected(true);
				embed.all = false;
				embed.family = false;
			}
			
			readIn = read.nextLine();
			//check layout type
			if(readIn.trim().equals("fr"))
			{
				currentLayout = FRLAYOUT;
				frItem.setSelected(true);
			}
			else if(readIn.trim().equals("circle"))
			{
				currentLayout = CIRCLE;
				circleItem.setSelected(true);
			}
			else if(readIn.trim().equals("spring"))
			{
				currentLayout = SPRING;
				springItem.setSelected(true);
			}
			else if(readIn.trim().equals("kk"))
			{
				currentLayout = KKLAYOUT;
				kkItem.setSelected(true);
			}
			else
			{
				currentLayout = ISOMLAYOUT;
				isomItem.setSelected(true);
			}
			
			readIn = read.nextLine();
			//check magnitude type
			if(readIn.trim().equals("numConnections"))
			{
				numConnectionsItem.setSelected(true);
				embed.countType = StepBloomfield.NUM_CONNECTIONS;
			}
			else if(readIn.trim().equals("degreeCentrality"))
			{
				degreeCentrItem.setSelected(true);
				embed.countType = StepBloomfield.DEGREE_CENTRALITY;
			}
			else
			{
				betweenCentrItem.setSelected(true);
				embed.countType = StepBloomfield.BETWEEN_CENTRALITY;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	private void saveGraph()
	{
		
		//deselect all nodes before saving
		for(Edge edge : embed.getGraph().getEdges())
		{
			edge.setSelected(false);
		}
		for(Node node : embed.getGraph().getVertices())
		{
			node.setSelected(false);
			node.setNeighbor(false);
		}
		
		vv.repaint();
		
		
		JFileChooser directorySelect = new JFileChooser();
		directorySelect.setDialogTitle("Choose a directory to save files to");
		directorySelect.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    //
	    // disable the "All files" option.
	    //
		directorySelect.setAcceptAllFileFilterUsed(false);
	    //    
	    if (directorySelect.showDialog(this, "Choose") == JFileChooser.APPROVE_OPTION) { 
	    	String directoryPath = directorySelect.getSelectedFile().getAbsolutePath();
	    	saveFiles = new SaveFileNamesDialog(this, true);
			saveFiles.show();
			
			if(saveFiles.isClickedOK())
			{
		    	save(directoryPath);
			}
	    }
		
	}
	
	private void save(String directoryPath)
	{
		FileOutputStream fileStream;
		FileWriter optionsFile;
	    
	    try {
			fileStream = new FileOutputStream(directoryPath + "/" +
					saveFiles.getVeDataTextField().getText() + ".ser");
		
			ObjectOutputStream os = new ObjectOutputStream(fileStream);
	
			os.writeObject(embed.getGraph());
	
			os.close();
				
			persistLayout.persist(directoryPath + "/" +
					saveFiles.getLayoutDataTextField().getText() + ".out");
			
			//write options
			optionsFile = new FileWriter(directoryPath + "/" +
					saveFiles.getOptionsDataTextField().getText() + ".txt");
			PrintWriter optionsPrint = new PrintWriter(optionsFile);
			if(directedItem.isSelected())
			{
				optionsPrint.println("directed");
			}
			else
			{
				optionsPrint.println("undirected");
			}
			
			if(familyOnly.isSelected()){
				optionsPrint.println("family");
			}
			else if(nonFamilyOnly.isSelected()){
				optionsPrint.println("nonfamily");
			}
			else{
				optionsPrint.println("all");
			}
			
			if(currentLayout == CIRCLE){
				optionsPrint.println("circle");
			}
			else if(currentLayout == SPRING){
				optionsPrint.println("spring");
			}
			else if(currentLayout == ISOMLAYOUT){
				optionsPrint.println("isom");
			}
			else if(currentLayout == KKLAYOUT){
				optionsPrint.println("kk");
			}
			else{
				optionsPrint.println("fr");
			}
		
			if(numConnectionsItem.isSelected())
			{
				optionsPrint.println("numConnections");
			}
			else if(degreeCentrItem.isSelected())
			{
				optionsPrint.println("degreeCentrality");
			}
			else
			{
				optionsPrint.println("betweenCentrality");
			}
			
			optionsPrint.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void resetActions()
	{
		Layout<Node,Edge> layout = vv.getGraphLayout();
		layout.initialize();
		Relaxer relaxer = vv.getModel().getRelaxer();
		if(relaxer != null) {
//		if(layout instanceof IterativeContext) {
			relaxer.stop();
			relaxer.prerelax();
			relaxer.relax();
		}
		
		for(Node n : embed.getGraph().getVertices())
		{
			n.setSelected(false);
			n.setNeighbor(false);
		}
		
		for(Edge e : embed.getGraph().getEdges())
		{
			e.setSelected(false);
		}
		
		MultiLayerTransformer mlt = vv.getRenderContext().getMultiLayerTransformer(); 

        MutableTransformer lt = mlt.getTransformer(Layer.LAYOUT); 

        MutableTransformer vt = mlt.getTransformer(Layer.VIEW);

        lt.setToIdentity();

        vt.setToIdentity();
	}
	
	private void displayChange(int currentLayout) {
		
		switch(currentLayout)
		{
			case CIRCLE:
			{
				persistLayout = new PersistentLayoutImpl<Node, Edge>
					(new MyCircleLayout<Node, Edge>(embed.getGraph()));
				break;
			}
			case SPRING:
			{
				Transformer<Edge, Integer> springTransformer = new Transformer<Edge, Integer>() {
					public Integer transform(Edge e) {
						return e.count;
					} 	
		        };
		        persistLayout = new PersistentLayoutImpl<Node, Edge>
		        	(new edu.uci.ics.jung.algorithms.layout.SpringLayout
						<Node, Edge>(embed.getGraph(), springTransformer));
				break;
			}
			case ISOMLAYOUT:
			{
				persistLayout = new PersistentLayoutImpl<Node, Edge>
					(new ISOMLayout<Node, Edge>(embed.getGraph()));
				break;
			}
			case KKLAYOUT:
			{
				Transformer<Edge, Integer> distanceTransformer = new Transformer<Edge, Integer>() {
					public Integer transform(Edge e) {
						return e.count;
					} 	
		        };
		        DijkstraDistance<Node, Edge> dDistance = new DijkstraDistance<Node, Edge>
		        	(embed.getGraph(), distanceTransformer);
		        persistLayout = new PersistentLayoutImpl<Node, Edge>
		        	(new KKLayout<Node, Edge>(embed.getGraph(), dDistance));
				break;
			}
			case FRLAYOUT:
			{
				persistLayout = new PersistentLayoutImpl<Node, Edge>
					(new FRLayout<Node, Edge>(embed.getGraph()));
				break;
			}
		}
		
		vv.setGraphLayout(persistLayout);
		vv.repaint();
		
	}
	
	private ArrayList<String> repopulateIncludedFamilies()
	{
		includedFamilies.clear();
		for(Node n : embed.getGraph().getVertices())
		{
			String familyName = n.getLastName();
			
	        if(familyName != null &&
	        		!(includedFamilies.contains(familyName)))
	        {
	        	includedFamilies.add(familyName);
	        }
		}
		
		Collections.sort(includedFamilies);
		
		viewOptionsPanel.updateDisplay();
		
		ArrayList<String> names = new ArrayList<String>();
		for(String s : includedFamilies)
		{
			names.add(s);
		}
		
		return names;
		
	}
	
	private void resetDisplay(boolean directed)
	{
		embed.clear();
		embed.setGraphType(directed);
		embed.loadData(filePath);
		if(numConnectionsItem.isSelected())
		{
			embed.setCountType(StepBloomfield.NUM_CONNECTIONS);
		}
		else if(betweenCentrItem.isSelected())
		{
			embed.setCountType(StepBloomfield.BETWEEN_CENTRALITY);
		}
		else if(degreeCentrItem.isSelected())
		{
			embed.setCountType(StepBloomfield.DEGREE_CENTRALITY);
		}
		currentViewFamilies = repopulateIncludedFamilies();
		show_vertex.updateIncluded(currentViewFamilies);
		displayChange(currentLayout);
		computeBetweenness();
		mainPanel.repaint();
	}
	
}
