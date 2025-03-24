/*************************************************************************
 *  Compilation:  javac LargestOfFive.java
 *  Execution:    java LargestOfFive 35 10 32 1 8
 *
 *  @author:
 *
 *  Takes five distinct integers as command-line arguments and prints the 
 *  largest value
 *
 *
 *  % java LargestOfFive 35 10 32 1 8
 *  35
 *
 *  Assume the inputs are 5 distinct integers.
 *  Print only the largest value, nothing else.
 *
 *************************************************************************/

public class LargestOfFive {

    public static void main (String[] args) {

        if (args.length != 5) {
            System.out.println("Please enter 5 numbers as input.");
            return;
        }
        int val = Integer.parseInt(args[0]);
        // Loop through the remaining input values
        for (int i = 1; i < args.length; i++) {
            int currentValue = Integer.parseInt(args[i]);
            if (currentValue > val) {
                // Update val if the current value is greater
                val = currentValue;
            }
        }
        // Print out the largest value
        System.out.println("The largest number is: " + val);
    }
}