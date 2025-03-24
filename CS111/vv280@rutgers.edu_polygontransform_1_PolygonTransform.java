/*************************************************************************
 *  Compilation:  javac PolygonTransform.java
 *  Execution:    java PolygonTransform
 *
 *  @author:
 *************************************************************************/

public class PolygonTransform {

    // Returns a new array that is an exact copy of the given array. 
    // The given array is not mutated. 
    public static double[] copy(double[] array) {
        // WRITE YOUR CODE HERE
        double[] copyArray = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            copyArray[i] = array[i];
        }
        return copyArray;
    }

    // Scales the given polygon by the factor alpha. 
    public static void scale(double[] x, double[] y, double alpha) {
        // WRITE YOUR CODE HERE
        for (int i = 0; i < x.length; i++) {
            x[i] *= alpha;
            y[i] *= alpha;
        }
    }

    // Translates the given polygon by (dx, dy). 
    public static void translate(double[] x, double[] y, double dx, double dy) {
        // WRITE YOUR CODE HERE
        for (int i = 0; i < x.length; i++) {
            x[i] += dx;
            y[i] += dy;
        }
    }

    // Rotates the given polygon theta degrees counterclockwise, about the origin. 
    public static void rotate(double[] x, double[] y, double theta) {
        // WRITE YOUR CODE HERE
        double radians = Math.toRadians(theta);
        for (int i = 0; i < x.length; i++) {
            double xi = x[i];
            double yi = y[i];
            x[i] = xi * Math.cos(radians) - yi * Math.sin(radians);
            y[i] = yi * Math.cos(radians) + xi * Math.sin(radians);
        }
    }

    // Tests each of the API methods by directly calling them. 
    public static void main(String[] args) {
        // WRITE YOUR CODE HERE
        StdDraw.setScale(-5.0, +5.0); 
        double[] x = { 0, 1, 1, 0 }; 
        double[] y = { 0, 0, 2, 1 }; 

        // Test copy()
        double[] copyX = copy(x);
        double[] copyY = copy(y);

        // Test scale()
        double alpha = 2.0; 
        StdDraw.setPenColor(StdDraw.RED); 
        StdDraw.polygon(x, y); 
        scale(x, y, alpha); 
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.polygon(x, y);

        // Test translate()
        double dx = 2.0, dy = 1.0; 
        StdDraw.setScale(-5.0, +5.0); 
        StdDraw.setPenColor(StdDraw.RED); 
        StdDraw.polygon(copyX, copyY); 
        translate(copyX, copyY, dx, dy); 
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.polygon(copyX, copyY);

        // Test rotate()
        double theta = 45.0; 
        StdDraw.setScale(-5.0, +5.0); 
        StdDraw.setPenColor(StdDraw.RED); 
        StdDraw.polygon(copyX, copyY); 
        rotate(copyX, copyY, theta); 
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.polygon(copyX, copyY);
    }
}

