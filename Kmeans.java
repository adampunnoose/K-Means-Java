import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import java.util.*;

public class Kmeans {

	private String line;
	private int numPoints, numCentroids, lineNum;
	protected float[][] points, centroids;
	ArrayList<ArrayList<float[]>> groupings;
	boolean valid = true;
	
	Kmeans(String path){
		
		//file IO
		
		line = "";
		lineNum = 1;
		
		try { //try to create, if it doesnt work, print the error message
			BufferedReader br = new BufferedReader(new FileReader(path));
			line = br.readLine();
			
			//read the data on the first line of the CSV file
			line = br.readLine();
			String[] values = line.split(",");
			numPoints = Integer.valueOf(values[0]);
			numCentroids = Integer.valueOf(values[1]);
			centroids = new float[numCentroids][2];
			points = new float[numPoints][2];
			
			//first point and centroid
			centroids[0][0] = Integer.valueOf(values[2]);
			centroids[0][1] = Integer.valueOf(values[3]);
			points[0][0] = Integer.valueOf(values[4]);
			points[0][1] = Integer.valueOf(values[5]);
			
			
			
			//infinitly go through the file, start from line 2 (line 3 when looking at excel)
			while((line = br.readLine()) != null) {
				lineNum += 1;
				values = line.split(","); //array of all values in a line, split up by the commas
				
				//read centroids
				if(lineNum >= 2 && lineNum < numCentroids + 1) {
					centroids[lineNum - 1][0] = Integer.valueOf(values[2]);
					centroids[lineNum - 1][1] = Integer.valueOf(values[3]);
					
					
				}
				
				//read Points
				if(lineNum >= 2 && lineNum < numPoints + 1) {
					points[lineNum - 1][0] = Integer.valueOf(values[4]);
					points[lineNum - 1][1] = Integer.valueOf(values[5]);
					
				}
				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//algorithm 
		ArrayList<ArrayList<float[]>> groupings = new ArrayList<>();
		groupings = algorithm(numPoints, numCentroids, points, centroids);
		printGroupings(groupings);
		
		
		
		
	}
	
	
	
	public float distP(float[] p1, float[] p2) {
		float distance = (float) Math.pow(   Math.pow(p1[0] - p2[0], 2) + Math.pow(p1[1]- p2[1], 2)    ,   0.5   );
		return distance;
	}
	
	public static float[][] twoDimensionalArrayCloneFloat(float[][] a) {
	    float[][] b = new float[a.length][];
	    for (int i = 0; i < a.length; i++) {
	      b[i] = a[i].clone();
	    }
	    return b;
	}
	
	public float[] meanCols(ArrayList<float[]> points){
		float xAvg= 0;
		float yAvg = 0;
		for (int i = 0; i < points.size(); i++) {
			xAvg += points.get(i)[0];
			yAvg += points.get(i)[1];
		}
		xAvg = xAvg / points.size();
		yAvg = yAvg / points.size();
		
		float[] output = {xAvg, yAvg};
		
		
		return output;
		
		
	}
	
	public void printGroupings(ArrayList<ArrayList<float[]>> groupings) {
		System.out.println("groupings: ");
		for(int i = 0; i < groupings.size(); i++) {
			System.out.println("\nCentroid: (" + centroids[i][0] + "," + centroids[i][1] + ")");
			System.out.println("Points: ");
			for(int j = 0; j < groupings.get(i).size(); j++) {
				System.out.println("[" + groupings.get(i).get(j)[0]
						+","+ groupings.get(i).get(j)[1] + "]");
			}
			
			
		}
		
	}
	
	public ArrayList<ArrayList<float[]>> algorithm (int numPoints, int numCentroids, float[][] points, float[][] centroids){
		ArrayList<ArrayList<float[]>> groupings = new ArrayList<>(numCentroids);
		float minDist, dist;
		int minCentroid;
		
		boolean done = false;
		//sort centroids
		Arrays.sort(centroids, Comparator.comparingDouble(o -> o[0] + o[1]));
		//run until final groupings are found
		while (done == false) {
			
			//clear groupings
			
			groupings = new ArrayList<ArrayList<float[]>>(numCentroids);
			for(int i = 0; i < numCentroids; i++) {
				groupings.add(new ArrayList<float[]>());
			}
			
			
			//create a temp centroid list
			float[][] cenTemp = twoDimensionalArrayCloneFloat(centroids);
			
			//main algorithm 
			for(int i = 0; i < numPoints; i++) { //measure distances between all points and all centroids
				minDist = distP(points[i], centroids[0]);
				minCentroid = 0;
				
				for (int j = 0; j < numCentroids; j++) {
					dist = distP(points[i], centroids[j]);
					if(dist < minDist) {
						minDist = dist;
						minCentroid = j;
					}
				}
				groupings.get(minCentroid);
				groupings.get(minCentroid).add(points[i]);
				
			}
			
			
			//recalculate centroids
			for(int i = 0; i < numCentroids; i++) {
				float[] avg;
				if(groupings.get(i).isEmpty()) {
					System.out.println("One Centroid does not atrack any points, "
							+ "please change the centroids used or try another clustering algorithm");
					valid = false;
					return groupings;
					
				}
				else {
					avg = meanCols(groupings.get(i));
					centroids[i] = avg;
					
				}
				
			}
			
			Arrays.sort(centroids, Comparator.comparingDouble(o -> o[0] + o[1]));
			if(Arrays.deepEquals(centroids, cenTemp)) {
				done = true;
			}

			
		}
		this.groupings = groupings;
		
		
		
		
		
		
		return groupings;
	}
	
	public void Graph() {
		if (valid == true) {
			SwingUtilities.invokeLater(() -> {  
				ScatterPlotBuilder example = new ScatterPlotBuilder("Scatter Plot",groupings, centroids );  
		        example.setSize(800, 400);  
		        example.setLocationRelativeTo(null);  
		        example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
		        example.setVisible(true);  
		      });  
			
		}
		else {
			System.out.println("data is not valid, please try another clustering algorithm, " 
					+ "or edit the data points");
		}
		

	}
	
	

}
