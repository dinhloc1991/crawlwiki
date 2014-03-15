package util;

import java.util.ArrayList;
import java.util.Scanner;

public class Common {
	public static void print(String s){
		System.out.println(s);
	}
	
	public static void print(ArrayList<String> array){
		for (String s : array) {
			print (s);
			
		}
	}
	
	public static int getInputInt(){
		Scanner scanner = new Scanner(System.in);
		int chon = scanner.nextInt();
		return chon;
	}
	public static String getInputStr(){
		Scanner scanner = new Scanner(System.in);
		String value = scanner.nextLine();
		return value;
	}
}
