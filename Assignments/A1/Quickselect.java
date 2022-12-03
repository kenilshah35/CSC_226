/**
 * Kenil Shah
 * V00903842
 * Assignment 1
 * Final Coding and Commenting: 28 Sept 2019
 * September 18, 2019
 * CSC 226 - Fall 2019
 */
 
 /*
	Major issues faced :-
		1) Answers off by 1 index. Eg. for array = [1,2,3,4,5,6,7] 1st smallest = 2, 2nd element = 3 ...
			Tried: decrement of k inside the function.
		2) Due to recursion k would decrease by one each time.
			Tried: Introduced flag variable in Quickselect so as to only do decrement once.
		3) Variable flag would reset every time the function is called.
			Solution: Initialized a global variables flag so as to not reset every function call.
 */
 
import java.util.*;
import java.io.*;

public class Quickselect {
    
    // Global variable for decrement of k
    private static int flag = 0;
	
	/**
	 * Quickselect function returns kth smallest element
	 * from an array of n elements [1,2....n] in worst
	 * case linear time of O(n).
	 * @param integer array A containing n unsorted elements.
	 * @param integer k , the kth smallest to be found.
	 * @return kth smallest element of the list
	*/
	public static int Quickselect(int[] A, int k){
		
		// Number of elements in the array
		int n = A.length;
		
		// Decreasing k by one index
		if(Quickselect.flag == 0){
			k = k-1;
			
			Quickselect.flag++;
		}
		
		/*System.out.println(QuickselectTest2.flag);*/
		/*
		System.out.println(k);
		for(int j=0;j<n;j++){
			System.out.print(A[j] + ", ");
		}
		System.out.println();
		*/
		
		// Condition to check if only one element present in the array.
		if(n==1 && k==0){
			return A[0];
		}
		
		// Condition for invalid inputs.
		if(k>=n || k<0){
			return -1;
		}
		
		// Finding the pivot around which to partition the array, using the median of medians method.
		int pivot = medianOfMedians(A);
		
		// Partitioning the pivot using the pivot
		int pos = partition(A,pivot);
		
		// Recursively calling Quickselect 
		if(k == pos){
			return A[pos];
		}	
		else if(k<pos){
			return Quickselect(Arrays.copyOfRange(A,0,pos), k);
		}
		return Quickselect(Arrays.copyOfRange(A,pos+1,n), k-pos-1);
		
	}
	
	/**
	 * Median of medians function to find the best 
	 * approx median most efficiently.
	 * @param integer array list for which median is to be found.
	 * @return best approx median of the list.
	*/
	private static int medianOfMedians(int[] list){
		
		// Finding length of the array
		int n = list.length;
		
		/*System.out.println(n);*/
		
		// Initializing array of medians and deciding size depending on the number of elements
		int[] medians;
		if(n%9 == 0) {
			medians = new int[(n/9)];
		}
		else{
			medians = new int[(n/9) + 1];	
		}
		
		// Finding medians of a group of 9 elements.
		int i=0;
		for(i=0;i<(n/9);i++){
			medians[i] = segmentMedian(Arrays.copyOfRange(list,(i*9),(i*9 + 9)),9); 
		}
		
		// Finding median if the number of elements are not multiples of 9.
		if(n%9 != 0){
			
			medians[i] = segmentMedian(Arrays.copyOfRange(list,(i*9),(i*9 + (n%9))), (n%9));
			i++;
		}
		
		/*
		for(int k=0;k<medians.length;k++){
			System.out.print(medians[k] + " ");
		}
		System.out.println();
		*/
		
		// Finding median of the array of medians.
		int m = medians.length;
		int mem;
		
		// Returning the element itself if the only 1 element in the array.
		if(m == 1){
			mem = medians[0];
		}
		
		else if(m <= 9){
			
			/*System.out.println("medians array length = " + medians.length);*/
			
			mem = segmentMedian(medians,medians.length);
			
			/*System.out.println("djdsjdj");*/
		}
		
		else{
			
			/*System.out.println("HHHHHHHHHHHH");*/
			
			mem = medianOfMedians(medians);
		}
		
		return mem;
	}
	
	/**
	 * Segment median function, finds median of an array of distinct elements.
	 * @param integer array segment
	 * @param length of the array
	*/
	private static int segmentMedian(int[] segment, int p){
		
		// Sorting through insertion sort.
		int n = segment.length;
        for (int i = 1; i < n; i++) { 
            int key = segment[i]; 
            int j = i - 1; 
  
            while (j >= 0 && segment[j] > key) { 
                segment[j + 1] = segment[j]; 
                j = j - 1; 
            } 
            segment[j + 1] = key; 
        } 
		
		/*
		Arrays.sort(segment);
		
		for(int i=0;i<segment.length;i++){
			System.out.print(segment[i]+" ,");
		}
		System.out.println();
		*/
		
		return segment[p/2];
	}
	
	/**
	 * Partition function which partions the array around a pivot.
	 * Elements less than the pivot are moved to the left.
	 * Elements higher than the pivot are moved to the right.
	 * @param integer array list, containing the list of elements to be partitioned.
	 * @param integer pivot, around which to partition the pivot.
	 * @return Position of the pivot, in the partitioned array.
	*/
	private static int partition(int[] list, int pivot){
		
		// Finding the length of the array.
		int n = list.length;
		
		// Finding the pivot in the array and swap it with the last element.
		for(int i=0;i<n;i++){
			if(list[i] == pivot){
				int temp = list[i];
				list[i] = list[n-1];
				list[n-1] = temp;
				break;
			}
		}
		
		// Looping through indexes 0 - n-1, and moving all elements < pivot to the front part of the array.
		// Recording the position where to swap in the pivot.
		int pos=0;
		for(int i=0;i<n-1;i++){
			
			if(list[i] <= pivot){
				int temp = list[pos];
				list[pos] = list[i];
				list[i] = temp;
				pos++;
			}
		}
		
		// Swapping the pivot to the index - pos, so as to get a partitioned array.
		int temp = list[n-1];
		list[n-1] = list[pos];
		list[pos] = temp;
		
		/*
		for(int i=0;i<list.length;i++){
			System.out.print(list[i] + " ");
		}
		System.out.println();
		//System.out.println(pos);
		*/
		
		return pos ;
	}
	
    
    public static void main(String[] args) {
        Scanner s;
        int[] array;
        int k;
        if(args.length > 0) {
	    try{
		s = new Scanner(new File(args[0]));
		int n = s.nextInt();
		array = new int[n];
		for(int i = 0; i < n; i++){
		    array[i] = s.nextInt();
		}
	    } catch(java.io.FileNotFoundException e) {
		System.out.printf("Unable to open %s\n",args[0]);
		return;
	    }
	    System.out.printf("Reading input values from %s.\n", args[0]);
        }
	else {
	    s = new Scanner(System.in);
	    System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
	    int temp = s.nextInt();
	    ArrayList<Integer> a = new ArrayList<Integer>();
	    while(temp >= 0) {
		a.add(temp);
		temp=s.nextInt();
	    }
	    array = new int[a.size()];
	    for(int i = 0; i < a.size(); i++) {
		array[i]=a.get(i);
	    }
	    
	    System.out.println("Enter k");
        }
        k = s.nextInt();
        System.out.println("The " + k + "th smallest number is the list is "
			   + Quickselect(array,k));	
    }
}
