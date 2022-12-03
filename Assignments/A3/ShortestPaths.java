/* ShortestPaths.java
   CSC 226 - Fall 2019
   
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
   java ShortestPaths
   
   To conveniently test the algorithm with a large input, create a text file
   containing one or more test graphs (in the format described below) and run
   the program with
   java ShortestPaths file.txt
   where file.txt is replaced by the name of the text file.
   
   The input consists of a series of graphs in the following format:
   
   <number of vertices>
   <adjacency matrix row 1>
   ...
   <adjacency matrix row n>
   
   Entry A[i][j] of the adjacency matrix gives the weight of the edge from 
   vertex i to vertex j (if A[i][j] is 0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that A[i][j]
   is always equal to A[j][i].
   
   An input file can contain an unlimited number of graphs; each will be processed separately.
   
   NOTE: For the purpose of marking, we consider the runtime (time complexity)
         of your implementation to be based only on the work done starting from
	 the ShortestPaths() method. That is, do not not be concerned with the fact that
	 the current main method reads in a file that encodes graphs via an
	 adjacency matrix (which takes time O(n^2) for a graph of n vertices).
   
   
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

//Do not change the name of the ShortestPaths class
public class ShortestPaths{

		
	private static class Edge implements Comparable<Edge>{
		
		private final int src,dest,weight;
		
		private Edge(int src,int dest,int weight){
			
			this.src = src;
			this.dest = dest;
			this.weight = weight;
			
		}
		
		public int compareTo(Edge that){
			if (this.weight < that.weight) return -1;
			else if (this.weight > that.weight) return +1;
			else return 0;
		}
		
		private int either(){
			return src;
		}
		
		private int other(int vertex){
			if (vertex == src) return dest;
			return src;
		}
		
		private static boolean sameEdge(Edge e1, Edge e2){
			if (e1.src == e2.src && e1.dest == e2.dest && e1.weight == e2.weight) return true;
			if (e1.src == e2.dest && e1.dest == e2.src && e1.weight == e2.weight) return true;
			
			return false;
		}
		
		private static boolean edgeInPQ(PriorityQueue<Edge> pq, Edge e1){
			
			for(Edge e: pq){
				if (Edge.sameEdge(e,e1)) return true;
			}
			return false;
		}
	}	
	
    //TODO: Your code here
    public static int n; // number of vertices
	private static int[] distTo;
	private static Edge[] edgeTo;
	private static IndexMinPQ<Integer> pq;
    /* ShortestPaths(adj) 
       Given an adjacency list for an undirected, weighted graph, calculates and stores the
       shortest paths to all the vertices from the source vertex.
       
       The number of vertices is adj.length
       For vertex i:
         adj[i].length is the number of edges
         adj[i][j] is an int[2] that stores the j'th edge for vertex i, where:
           the edge has endpoints i and adj[i][j][0]
           the edge weight is adj[i][j][1] and assumed to be a positive integer
       
       All weights will be positive.
    */
    static void ShortestPaths(int[][][] adj, int source){
		n = adj.length;
		edgeTo = new Edge[n];
		distTo = new int[n];
		pq = new IndexMinPQ<Integer>(n);
	
		for(int i=0;i<n;i++){
			distTo[i] = Integer.MAX_VALUE;
		}
		distTo[source] = 0;
		edgeTo[source] = null;
		pq.insert(source,0);
		
		while(!pq.isEmpty()){
			
			int i = pq.delMin();
			
			for(int j = 0;j<adj[i].length;j++){
				
				int w = adj[i][j][0];
				
				if(distTo[w] > distTo[i] + adj[i][j][1]){
					distTo[w] = distTo[i] + adj[i][j][1];
					Edge e = new Edge(i,w,adj[i][j][1]);
					edgeTo[w] = e;
					if(pq.contains(w)) pq.change(w,distTo[w]);
					else pq.insert(w, distTo[w]);
				}
			}
		
		}
		
    }
    
	
    static void PrintPaths(int source){
		
		for(int i=0;i<n;i++){
			
			if(i==source){
				System.out.println("The path from " + source + " to " + source + " is: " + source + " and the total distance is : 0");
				continue;
			}
			
			Stack<Edge> path = new Stack<Edge>();
			for(Edge e = edgeTo[i];e != null;e = edgeTo[e.other(i)]){
				path.push(e);
			}
			if(!path.empty()){
				Edge e1 = path.pop();
				System.out.print("The path from " + source + " to " + i +" is: " + e1.src + " --> " + e1.dest);
			}
			while(!path.empty()){
				Edge e_rest = path.pop();
				System.out.print(" --> " + e_rest.dest);
			}
			System.out.println(" and the total distance is : " + distTo[i]);
		}
	
    }
    
    
    /* main()
       Contains code to test the ShortestPaths function. You may modify the
       testing code if needed, but nothing in this function will be considered
       during marking, and the testing process used for marking will not
       execute any of the code below.
    */
    public static void main(String[] args) throws FileNotFoundException{
	Scanner s;
	if (args.length > 0){
	    //If a file argument was provided on the command line, read from the file
	    try{
		s = new Scanner(new File(args[0]));
	    } catch(java.io.FileNotFoundException e){
		System.out.printf("Unable to open %s\n",args[0]);
		return;
	    }
	    System.out.printf("Reading input values from %s.\n",args[0]);
	}
	else{
	    //Otherwise, read from standard input
	    s = new Scanner(System.in);
	    System.out.printf("Reading input values from stdin.\n");
	}
	
	int graphNum = 0;
	double totalTimeSeconds = 0;
	
	//Read graphs until EOF is encountered (or an error occurs)
	while(true){
	    graphNum++;
	    if(graphNum != 1 && !s.hasNextInt())
		break;
	    System.out.printf("Reading graph %d\n",graphNum);
	    int n = s.nextInt();
	    int[][][] adj = new int[n][][];
	    
	    int valuesRead = 0;
	    for (int i = 0; i < n && s.hasNextInt(); i++){
		LinkedList<int[]> edgeList = new LinkedList<int[]>(); 
		for (int j = 0; j < n && s.hasNextInt(); j++){
		    int weight = s.nextInt();
		    if(weight > 0) {
			edgeList.add(new int[]{j, weight});
		    }
		    valuesRead++;
		}
		adj[i] = new int[edgeList.size()][2];
		Iterator it = edgeList.iterator();
		for(int k = 0; k < edgeList.size(); k++) {
		    adj[i][k] = (int[]) it.next();
		}
	    }
	    if (valuesRead < n * n){
		System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
		break;
	    }
	    
		
	    // output the adjacency list representation of the graph
	    for(int i = 0; i < n; i++) {
	    	System.out.print(i + ": ");
	    	for(int j = 0; j < adj[i].length; j++) {
	    	    System.out.print("(" + adj[i][j][0] + ", " + adj[i][j][1] + ") ");
	    	}
	    	System.out.print("\n");
	    }
	    
	    long startTime = System.currentTimeMillis();
	    
	    ShortestPaths(adj, 0);
	    PrintPaths(0);
	    long endTime = System.currentTimeMillis();
	    totalTimeSeconds += (endTime-startTime)/1000.0;
	    
	    //System.out.printf("Graph %d: Minimum weight of a 0-1 path is %d\n",graphNum,totalWeight);
	}
	graphNum--;
	System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
    }
}
