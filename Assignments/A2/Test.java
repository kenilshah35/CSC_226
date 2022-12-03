/* MST.java
   CSC 226 - Fall 2019
   Problem Set 2 - Template for Minimum Spanning Tree algorithm
   
   The assignment is to implement the mst() method below, using Kruskal's algorithm
   equipped with the Weighted Quick-Union version of Union-Find. The mst() method computes
   a minimum spanning tree of the provided graph and returns the total weight
   of the tree. To receive full marks, the implementation must run in time O(m log m)
   on a graph with n vertices and m edges.
   
   This template includes some testing code to help verify the implementation.
   Input graphs can be provided with standard input or read from a file.
   
   To provide test inputs with standard input, run the program with
       java MST
   To terminate the input, use Ctrl-D (which signals EOF).
   
   To read test inputs from a file (e.g. graphs.txt), run the program with
       java MST graphs.txt
   
   The input format for both methods is the same. Input consists
   of a series of graphs in the following format:
   
       <number of vertices>
       <adjacency matrix row 1>
       ...
       <adjacency matrix row n>
   	
   For example, a path on 3 vertices where one edge has weight 1 and the other
   edge has weight 2 would be represented by the following
   
   3
   0 1 0
   1 0 2
   0 2 0
   	
   An input file can contain an unlimited number of graphs; each will be processed separately.
   
   NOTE: For the purpose of marking, we consider the runtime (time complexity)
         of your implementation to be based only on the work done starting from
	 the mst() method. That is, do not not be concerned with the fact that
	 the current main method reads in a file that encodes graphs via an
	 adjacency matrix (which takes time O(n^2) for a graph of n vertices).
*/

import java.util.Scanner;
import java.io.File;
import java.util.*;

public class Test {
	
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
		/*
		private static int either(){
			return src;
		}
		
		private static int other(int vertex){
			if (vertex == src) return dest;
			return src;
		}
		*/
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
	
	private static class WeightedQuickUnion{
		
		private static int[] id;
		private static int[] sz;
		private static int count;
		
		private WeightedQuickUnion(int N){
			count = N;
			id = new int[N];
			for(int i=0;i<N;i++) id[i] = i;
			sz = new int[N];
			for(int i=0;i<N;i++) sz[i] = 1;
		}
		
		private static boolean connected(int p, int q){
			return find(p) == find(q);
		}
		
		private static int find(int p){
			while(p != id[p]) p = id[p];
			return p;
		}
		
		private static void union(int p,int q){
			int i = find(p);
			int j = find(q);
			if(i == j) return;
			
			if(sz[i] < sz[j]) {id[i] =j; sz[j] += sz[i]; }
			else {id[j] =i; sz[i] += sz[j]; }
			count--;
			
		}
	}
		
    /* mst(adj)
       Given an adjacency matrix adj for an undirected, weighted graph, return the total weight
       of all edges in a minimum spanning tree.

       The number of vertices is adj.length
       For vertex i:
         adj[i].length is the number of edges
         adj[i][j] is an int[2] that stores the j'th edge for vertex i, where:
           the edge has endpoints i and adj[i][j][0]
           the edge weight is adj[i][j][1] and assumed to be a positive integer
    */
    static int mst(int[][][] adj) {
		
		
		int n = adj.length;
		ArrayList<Edge> mst = new ArrayList<Edge>();
		
		PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
		for(int i=0;i<n;i++){
			for(int j=0;j<adj[i].length;j++){
				Edge e = new Edge(i,adj[i][j][0],adj[i][j][1]);
				//System.out.println(e.src + ", " + e.dest + ", " + e.weight);
				//System.out.println(Edge.edgeInPQ(pq,e));
				if(!Edge.edgeInPQ(pq,e)) pq.add(e);
			}
		}
		
		WeightedQuickUnion uf = new WeightedQuickUnion(n);
		while(pq.size() != 0 && mst.size() < n-1){
			
			Edge eMin = pq.poll();
			//System.out.println(eMin.src + ", " + eMin.dest + ", " + eMin.weight );
			int v = eMin.src;
			int w = eMin.dest;
			
			if(!uf.connected(v,w)){
				uf.union(v,w);
				mst.add(eMin);
			}
		}
		
		
		/* Add the weight of each edge in the minimum spanning tree
	   to totalWeight, which will store the total weight of the tree.
		*/
		int totalWeight = 0;
		/* ... Your code here ... */
		for (int i=0;i<mst.size();i++){
			Edge edge = mst.get(i);
			//System.out.println(edge.weight);
			totalWeight += edge.weight;
		}
		
		return totalWeight;
		
    }


    public static void main(String[] args) {
	/* Code to test your implementation */
	/* You may modify this, but nothing in this function will be marked */

	int graphNum = 0;
	Scanner s;

	if (args.length > 0) {
	    //If a file argument was provided on the command line, read from the file
	    try {
		s = new Scanner(new File(args[0]));
	    }
	    catch(java.io.FileNotFoundException e) {
		System.out.printf("Unable to open %s\n",args[0]);
		return;
	    }
	    System.out.printf("Reading input values from %s.\n",args[0]);
	}
	else {
	    //Otherwise, read from standard input
	    s = new Scanner(System.in);
	    System.out.printf("Reading input values from stdin.\n");
	}
		
	//Read graphs until EOF is encountered (or an error occurs)
	while(true) {
	    graphNum++;
	    if(!s.hasNextInt()) {
		break;
	    }
	    System.out.printf("Reading graph %d\n",graphNum);
	    int n = s.nextInt();

	    int[][][] adj = new int[n][][];
	    
	    
	    
	    
	    int valuesRead = 0;
	    for (int i = 0; i < n && s.hasNextInt(); i++) {
		LinkedList<int[]> edgeList = new LinkedList<int[]>(); 
		for (int j = 0; j < n && s.hasNextInt(); j++) {
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
	    if (valuesRead < n * n) {
		System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
		break;
	    }

	    // // output the adjacency list representation of the graph
	    // for(int i = 0; i < n; i++) {
	    // 	System.out.print(i + ": ");
	    // 	for(int j = 0; j < adj[i].length; j++) {
	    // 	    System.out.print("(" + adj[i][j][0] + ", " + adj[i][j][1] + ") ");
	    // 	}
	    // 	System.out.print("\n");
	    // }

	    int totalWeight = mst(adj);
	    System.out.printf("Graph %d: Total weight of MST is %d\n",graphNum,totalWeight);

				
	}
    }

    
}
