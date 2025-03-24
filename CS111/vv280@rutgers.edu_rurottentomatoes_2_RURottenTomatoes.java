/*************************************************************************
 *  Compilation:  javac RURottenTomatoes.java
 *  Execution:    
 *
 *  @author:
 *
 * RURottenTomatoes creates a 2 dimensional array of movie ratings 
 * from the command line arguments and displays the index of the movie 
 * that has the highest sum of ratings.
 *
 *  java RURottenTomatoes 3 2 5 2 3 3 4 1
 *  0
 *************************************************************************/
public class RURottenTomatoes {
    public static void main(String[] args) {
        int reviewers = Integer.parseInt(args[0]);
        int movies = Integer.parseInt(args[1]);
        int[][] ratings = new int[reviewers][movies];

        // fill the 2D array with movie ratings
        int index = 2;
        for (int i = 0; i < reviewers; i++) {
            for (int j = 0; j < movies; j++) {
                ratings[i][j] = Integer.parseInt(args[index]);
                index++;
            }
        }

        // find the movie with the highest sum of ratings
        int max = Integer.MIN_VALUE;
        int movieindex = -1;
        for (int j = 0; j < movies; j++) {
            int sum = 0;
            for (int i = 0; i < reviewers; i++) {
                sum += ratings[i][j];
            }
            if (sum > max) {
                max = sum;
                movieindex = j;
            }
        }

        System.out.println(movieindex);
    }
}