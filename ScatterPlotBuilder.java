import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;  
import javax.swing.SwingUtilities;  
import javax.swing.WindowConstants;  
import org.jfree.chart.ChartFactory;  
import org.jfree.chart.ChartPanel;  
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;  
import org.jfree.data.xy.XYDataset;  
import org.jfree.data.xy.XYSeries;  
import org.jfree.data.xy.XYSeriesCollection;



public class ScatterPlotBuilder extends JFrame {  
	private static final long serialVersionUID = 6294689542092367723L;  
	protected float[][] points, centroids;
	ArrayList<ArrayList<float[]>> groupings;
	  
	
	  public ScatterPlotBuilder(String title,ArrayList<ArrayList<float[]>> groupings, float[][] centroids ) {  
	    super(title);  
	    this.groupings = groupings;
	    this.centroids = centroids;
	  
	    // Create dataset  
	    XYDataset dataset = createDataset();
	    
	    //create chart
	    JFreeChart chart = ChartFactory.createScatterPlot(  
		        "K-means clusters",   
		        "X-Axis", "Y-Axis", dataset, PlotOrientation.VERTICAL, true, true, false);
	    
	    //Changes background color  
	    XYPlot plot = (XYPlot)chart.getPlot();  
	    plot.setBackgroundPaint(new Color(255,228,196));  
	      
	     
	    // Create Panel  
	    ChartPanel panel = new ChartPanel(chart);  
	    setContentPane(panel);  
		      
	    
	  }
	  
	  public XYDataset createDataset() {
		  XYSeriesCollection dataset = new XYSeriesCollection();
		  
		  //points
		  for (int i = 0; i < groupings.size(); i++) {//iterate for each centroid
			  String name = "grouping " + String.valueOf(i);
			  XYSeries series = new XYSeries(name);
			  for(int j = 0; j < groupings.get(i).size(); j++ ) {
				  series.add(groupings.get(i).get(j)[0], groupings.get(i).get(j)[1]);
				  
			  }
			  dataset.addSeries(series);
		  }
		  
		  //clusters
		  XYSeries series = new XYSeries("clusters");
		  for(int i = 0; i < centroids.length; i++) {
			  series.add(centroids[i][0], centroids[i][1]);
			  
		  }
		  dataset.addSeries(series);
		  
		  
		  return dataset;
		  
		  
		  
	  }
}