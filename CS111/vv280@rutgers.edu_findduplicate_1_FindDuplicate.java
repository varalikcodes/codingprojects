/*************************************************************************
 *  Compilation:  javac FindDuplicate.java
 *  Execution:    java FindDuplicate
 *
 *  @author:
 *
 * FindDuplicate that reads n integer arguments from the command line 
 * into an integer array of length n, where each value is between is 1 and n, 
 * and displays true if there are any duplicate values, false otherwise.
 *
 *  % java FindDuplicate 10 8 5 4 1 3 6 7 9
 *  false
 *
 *  % java FindDuplicate 4 5 2 1 
 *  true
 *************************************************************************/
public class FindDuplicate{ 
	public static void main(String[] args) {
		int n = args.length;
		boolean[] duplicate = new boolean[n];
		boolean spotted = false; //like spotted from gg
	
		for (int i = 0; i < n; i++) {
			int val = Integer.parseInt(args[i]);
			if (val < 1 || val > n) {
				System.out.println("false");
				return;
			}
			if (duplicate[val - 1]) {
				System.out.println("true");
				spotted = true;
				break;
			}
			duplicate[val - 1] = true;
		}
	
		if (!spotted) {
			System.out.println("false");
		}
	 }
	}