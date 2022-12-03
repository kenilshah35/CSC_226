import java.util.*;

public class Test2{
	
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
		}*/
	}
	
	/*
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
	*/
	/*
	static int mst(int[][][] adj) {
		
		
		int n = adj.length;
		ArrayList<Edge> mst = new ArrayList<Edge>();
		
		PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
		for(int i=0;i<n;i++){
			for(int j=0;j<adj[i].length;j++){
				Edge e = new Edge(i,adj[i][j][0],adj[i][j][1]);
				if(!Edge.edgeInPQ(pq,e)) pq.add(e);
			}
		}
		
		WeightedQuickUnion uf = new WeightedQuickUnion(n);
		while(pq.size() == 0 && mst.size() < n-1){
			
			Edge eMin = pq.poll();
			int v = eMin.either();
			int w = eMin.other(v);
			
			if(!uf.connected(v,w)){
				uf.union(v,w);
				mst.add(eMin);
			}
		}
		
		/* Add the weight of each edge in the minimum spanning tree
	   to totalWeight, which will store the total weight of the tree.
		
		int totalWeight = 0;
		/* ... Your code here ... 
		for (int i=0;i<mst.size();i++){
			Edge edge = mst.get(i);
			totalWeight += edge.weight;
		}
		
		return totalWeight;
		
    }
	*/
	public static void main(String[] args){
		
		PriorityQueue<Edge> pq2 = new PriorityQueue<Edge>();
		Edge e1 = new Edge(1,2,6);
		Edge e2 = new Edge(0,2,2);
		Edge e3 = new Edge(0,1,3);
		
		System.out.println(pq2.size());
		pq2.add(e1);
		pq2.add(e2);
		pq2.add(e3);
		System.out.println(pq2.size());
		/*
		Iterator<Edge> it = pq.iterator();
		while(it.hasNext()){
			Edge e = it.next();
			System.out.println(e.src + ", " + e.dest + ", " + e.weight);
		}
		*/
		
		
		for(Edge e: pq2){
			System.out.println(e.src+", " + e.dest +", " + e.weight);	
		}
		
		
		/*
		//pq.add(e1);
		pq.add(e2);
		//pq.add(e3);
		
		System.out.println(Edge.edgeInPQ(pq,e1));
		*/
		/*
		System.out.println(pq2.size());
		System.out.println(pq2.poll().weight);
		System.out.println(pq2.poll().weight);
		System.out.println(pq2.poll().weight);
		System.out.println(pq2.poll().weight);
		System.out.println(pq2.poll().weight);
		*/
		
		
	}
}