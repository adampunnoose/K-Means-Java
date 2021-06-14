# K-Means-Java
A K-means clustering algorithm coded in the Java language.

# Algorithm 
The K-Means clustering algorithm is an unsupervised algorithm that is popular way to characterize data in statistical analysis. In order to run, it needs data points and initial clusters. It then gives the user clusters of data points that are close to or similar to each other. The best way to use this algorithm is to first look at the data, and choose centroids that are placed close to what are intuitively observed to be clusters. 

This is an iterative algorithm. for each iteration, points are assigned to the closest centroid. After all points are assigned, the centroids are recalculated by taking the average position of all points assigned to it. The current iteration is then finished, and assignments are reset. This process continues until centroids remain the same for 2 iterations. 

# Program Description 
The data is places in the Template names “points Template.csv”, this file contains the format that the code needs in order to read the data.  The data that is input into the template includes centroids, data points, the number of data points and the number of centroids. An example is also provided.

The program itself is split into 2 class files, “Kmean.javas”, which takes the data from the Template provided and calculates the clusters, and  “ScatterPlotBuilder.java”, which builds a Scatterplot to help visualize the data when clustered. The programs require the “java.awt”, “java.util”, “org.jfree.chart” and “org.jree.data” packages, the later 2 are provided in the “jcommon-1.0.12.jar” and “jfreechart-1.0.9.jar” packages located in the repository. A Driver file is also included to aide in the operation of this program, but its use when running the program is up to the user. 



