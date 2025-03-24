/*************************************************************************
 *  Compilation:  javac RecursiveAppend.java
 *  Execution:    java RecursiveAppend
 *
 *  @author:
 *
 *************************************************************************/

 public class RecursiveAppend {

    // Returns the original string appended to the original string n times 
    public static String appendNTimes (String original, int n) {
        if (n >= 1) {
            return original + appendNTimes(original, n-1);
        }
        return original;
    }

public static void main (String[] args) {
    System.out.println( appendNTimes("hello",2));
    System.out.println( appendNTimes("cat",4));
    System.out.println( appendNTimes("dog",5));
    System.out.println( appendNTimes("cow",6));
    System.out.println( appendNTimes("cow",0));
    
    // Test case 1: n = 0
        //String original = "cat";
        //int n = 0;
        //String expected = "";
        //String result = appendNTimes(original, n);
        //System.out.println(result.equals(expected)); // output: true

        // Test case 2: n = 1
        //original = "cat";
        //n = 1;
        //expected = "cat";
        //result = appendNTimes(original, n);
        //System.out.println(result.equals(expected)); // output: true

        // Test case 3: n = 2
        //original = "cat";
       // n = 2;
        //expected = "catcat";
        //result = appendNTimes(original, n);
        //System.out.println(result.equals(expected)); // output: true

        // Test case 4: n = 3, empty string
        //original = "";
       // n = 3;
        //expected = "";
       // result = appendNTimes(original, n);
        //System.out.println(result.equals(expected)); // output: true

        // Test case 5: n = 4, special characters
        //original = "$#%";
        //n = 4;
       // expected = "$#%$#%$#%$#%";
        //result = appendNTimes(original, n);
        //System.out.println(result.equals(expected)); // output: true
    }
}
