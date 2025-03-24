/*************************************************************************
 *  Compilation:  javac CheckDigit.java
 *  Execution:    java CheckDigit 020131452
 *
 *  @author:
 *
 *  Takes a 12 or 13 digit integer as a command line argument, then computes
 *  and displays the check digit
 *
 *  java CheckDigit 048231312622
 *  0
 *
 *  java CheckDigit 9780470458310
 *  0
 * 
 *  java CheckDigit 9780470454310
 *  8
 * 
 *  Print only the check digit character, nothing else.
 *
 *************************************************************************/

public class CheckDigit {

    public static void main (String[] args) {
        if (args.length < 1) {
            System.out.println("Error");
            return;
        }
        long num = Long.parseLong(args[0]);
        int sum1 = 0, sum2 = 0;
        boolean alt = false;
        while (num > 0) {
            int val = (int) (num % 10);
            if (alt) {
                sum2 += val;
            } else {
                sum1 += val;
            }
            num /= 10;
            alt = !alt;
        }
        int total = (sum1 + 3 * sum2) % 10;
        System.out.println(total);
    }
}