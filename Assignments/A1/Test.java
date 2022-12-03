import java.util.*;
import java.io.*;

public class Test {
	
	private static int flag = 0;
	public static void main(String[] args){
		/*
		int[] list1 = {45, 82, 1, 94, 600, 85, 9, 4, 12, 44};
		int[] list2 = Arrays.copyOfRange(list1,7,list1.length + 1);
		
		for(int i = 0;i<list2.length;i++){
			System.out.print(list2[i] + " ");
		}
		*/
		/*
		System.out.println("Initial Value: "+Test.flag);
		//System.out.println(10/9);
		good();
		good();
		good();
		good();
		good();
		
		System.out.println("Final Value: "+Test.flag);
		System.out.println(2/5 + 1) ;
		*/
		
		for(int i=1;i<=100000;i++){
			System.out.print(i + " ");
		}
	}
	
	public static void good(){
		
		//Test.flag++;
		
		if(Test.flag == 0){
			Test.flag++;
		}
		
	}
}