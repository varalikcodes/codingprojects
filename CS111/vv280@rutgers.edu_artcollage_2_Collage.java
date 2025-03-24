import java.awt.Color;

/*
 * This class contains methods to create and perform operations on a collage of images.
 * 
 * @author Ana Paula Centeno
 */ 

public class Collage {

    // The orginal picture
    private Picture originalPicture;

    // The collage picture is made up of tiles.
    // Each tile consists of tileDimension X tileDimension pixels
    // The collage picture has collageDimension X collageDimension tiles
    private Picture collagePicture;

    // The collagePicture is made up of collageDimension X collageDimension tiles
    // Imagine a collagePicture as a 2D array of tiles
    private int collageDimension;

    // A tile consists of tileDimension X tileDimension pixels
    // Imagine a tile as a 2D array of pixels
    // A pixel has three components (red, green, and blue) that define the color 
    // of the pixel on the screen.
    private int tileDimension;
    
    /*
     * One-argument Constructor
     * 1. set default values of collageDimension to 4 and tileDimension to 150
     * 2. initializes originalPicture with the filename image
     * 3. initializes collagePicture as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see constructors for the Picture class).
     * 4. update collagePicture to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public Collage (String filename) {
        tileDimension = 150;
        collageDimension = 4;
        originalPicture = new Picture(filename);
        collagePicture = new Picture(collageDimension*tileDimension, collageDimension*tileDimension);
        scale(originalPicture, collagePicture);
        // WRITE YOUR CODE HERE
    }

    /*
     * Three-arguments Constructor
     * 1. set default values of collageDimension to cd and tileDimension to td
     * 2. initializes originalPicture with the filename image
     * 3. initializes collagePicture as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collagePicture to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */    
    public Collage (String filename, int td, int cd) {
        tileDimension = td;
        collageDimension = cd;
        originalPicture = new Picture(filename);
        collagePicture = new Picture(cd*td, cd*td);
        scale(originalPicture, collagePicture);
        // WRITE YOUR CODE HERE
    }


    /*
     * Scales the Picture @source into Picture @target size.
     * In another words it changes the size of @source to make it fit into
     * @target. Do not update @source. 
     *  
     * @param source is the image to be scaled.
     * @param target is the 
     */
    public static void scale (Picture source, Picture target) {

        // WRITE YOUR CODE HERE
            int width  = target.width();
            int height = target.height();
            for (int targetCol = 0; targetCol < width; targetCol++) {
                for (int targetRow = 0; targetRow < height; targetRow++) {
                    int sourceCol = targetCol * source.width()  / width;
                    int sourceRow = targetRow * source.height() / height;
                    Color color = source.get(sourceCol, sourceRow);
                    target.set(targetCol, targetRow, color);
                }
            target.show();
        }
    }

     /*
     * Returns the collageDimension instance variable
     *
     * @return collageDimension
     */   
    public int getCollageDimension() {
        return collageDimension;
    }

    /*
     * Returns the tileDimension instance variable
     *
     * @return tileDimension
     */    
    public int getTileDimension() {
        return tileDimension;
    }

    /*
     * Returns original instance variable
     *
     * @return original
     */
    
    public Picture getOriginalPicture() {
        return originalPicture;
    }

    /*
     * Returns collage instance variable
     *
     * @return collage
     */
    
    public Picture getCollagePicture() {
        return collagePicture;
    }

    /*
     * Display the original image
     * Assumes that original has been initialized
     */    
    public void showOriginalPicture() {
        originalPicture.show();
    }

    /*
     * Display the collage image
     * Assumes that collage has been initialized
     */    
    public void showCollagePicture() {
       collagePicture.show();
    }

    /*
     * Updates collagePicture to be a collage of tiles from original Picture.
     * collagePicture will have collageDimension x collageDimension tiles, 
     * where each tile has tileDimension X tileDimension pixels.
     */    
    public void makeCollage () {
        Picture scaledOriginal = new Picture(tileDimension, tileDimension);
        scale(originalPicture, scaledOriginal);

        // copy the mini version of original into the collage a bunch of times
        for (int i = 0; i < collagePicture.height()/tileDimension; i++) {
            for (int j = 0; j < collagePicture.height()/tileDimension; j++) {
                //i * 600 = i * collage.height()
                copy(i*tileDimension, j*tileDimension, scaledOriginal, collagePicture);
               //collagePicture.show();
                // System.out.print(i*target.height());
                // System.out.print(", ");
                // System.out.print(j*target.height());
            }

            // System.out.println();
        }
        //collagePicture.show();
        // WRITE YOUR CODE HERE
    }

    public static void copy(int row, int col, Picture source, Picture target) {
        for (int i = 0; i < source.height(); i++) {
            for (int j = 0; j < source.height(); j++) {

                // bug
                // Color color = source.get(i+col, j+row);
                Color color = source.get(i, j);
                target.set(i+col, j+row, color);
            }
        }
    }

    /*
     * Colorizes the tile at (collageCol, collageRow) with component 
     * (see Week 9 slides, the code for color separation is at the 
     *  book's website)
     *
     * @param component is either red, blue or green
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void colorizeTile (String component,  int collageCol, int collageRow) {
        for( int i = 0; i < collagePicture.height()/tileDimension; i++) {
            for (int j = 0; j < collagePicture.height()/tileDimension; j++) {
                //System.out.println(j);
                // go through each row and column to check if each tile args is the same as the input and call the setRGB method to change each
                if ( j == collageCol && i == collageRow ) {
                    //Picture pictureTile = new Picture(i,j);

                    for (int pixelRow = 0; pixelRow < tileDimension ; pixelRow++) {

                        for (int pixelCol = 0; pixelCol < tileDimension; pixelCol++) {
                            // will go through each individual pixel and iterate through until the tile dimension has been reached and change the color
                            //System.out.println(pixelCol);
                            Color color = collagePicture.get(pixelCol + (tileDimension * j), pixelRow + (tileDimension * i));
                            // need to figure out a way to get the color components of a pixel
                            int r = color.getRed();
                            int g = color.getGreen();
                            int b = color.getBlue();
                            if(component.equals("red")) {
                                collagePicture.set(pixelCol + (tileDimension * j), pixelRow + (tileDimension * i), new Color(r, 0, 0));
                            }
                            else if(component.equals("green")) {
                                collagePicture.set(pixelCol + (tileDimension * j), pixelRow + (tileDimension * i), new Color(0, g, 0));
                            }
                            else if(component.equals("blue")){
                                collagePicture.set(pixelCol + (tileDimension * j), pixelRow + (tileDimension * i), new Color(0, 0, b));
                            }
                        }
                    }
                }
            }
        }
        // WRITE YOUR CODE HERE
    }

    /*
     * Replaces the tile at collageCol,collageRow with the image from filename
     * Tile (0,0) is the upper leftmost tile
     *
     * @param filename image to replace tile
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void replaceTile (String filename,  int collageCol, int collageRow) {
        Picture replacement = new Picture(filename); // will create a replacement picture from the argument given from the command line
        Picture scaledReplacement = new Picture( tileDimension, tileDimension );
        // creates the scaled image from the new image to fit the tile existing

        scale(replacement, scaledReplacement);
        // scales the image to the tile dimension u want using the scale method

        for(int i = 0; i< collagePicture.height()/tileDimension; i++) {
            for(int j = 0; j< collagePicture.height()/tileDimension; j++) {
                if ( j == collageCol && i == collageRow ) {
                    //copy the image and replace it into this tile
                    copy (i*tileDimension, j*tileDimension, scaledReplacement, collagePicture);
                }
            }
        }
        // WRITE YOUR CODE HERE
    }

    /*
     * Grayscale tile at (collageCol, collageRow)
     *
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void grayscaleTile (int collageCol, int collageRow) {
        for( int i = 0; i < collagePicture.height()/tileDimension; i++) {
            for (int j = 0; j < collagePicture.height()/tileDimension; j++) {
                if ( j == collageCol && i == collageRow ) {
                    //Picture pictureTile = new Picture(i,j);
                    for (int pixelRow = 0; pixelRow < tileDimension ; pixelRow++) {

                        for (int pixelCol = 0; pixelCol < tileDimension; pixelCol++) {
                            // get the components of color of each pixel
                            Color color = collagePicture.get(pixelCol + (tileDimension * j), pixelRow + (tileDimension * i));
                            int y = (int) Math.round(lum(color));
                            Color gray = new Color(y, y, y);
                            collagePicture.set(pixelCol + (tileDimension * j), pixelRow + (tileDimension * i), gray);
                        }
                    }
                }
            }
        }
        // WRITE YOUR CODE HERE
    }

    public static double lum(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        // int gray = (int)Math.round(.299*r + .587*g + .114*b);
        // return gray;
        return .299*r + .587*g + .114*b;
    }

    /**
     * Returns the monochrome luminance of the given color as an intensity
     * between 0.0 and 255.0 using the NTSC formula
     * Y = 0.299*r + 0.587*g + 0.114*b. If the given color is a shade of gray
     * (r = g = b), this method is guaranteed to return the exact grayscale
     * value (an integer with no floating-point roundoff error).
     *
     * @param color the color to convert
     * @return the monochrome luminance (between 0.0 and 255.0)
     */
    private static double intensity(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        if (r == g && r == b) return r;   // to avoid floating-point issues
        return 0.299*r + 0.587*g + 0.114*b;
    }

    /**
     * Returns a grayscale version of the given color as a {@code Color} object.
     *
     * @param color the {@code Color} object to convert to grayscale
     * @return a grayscale version of {@code color}
     */
    private static Color toGray(Color color) {
        int y = (int) (Math.round(intensity(color)));   // round to nearest int
        Color gray = new Color(y, y, y);
        return gray;
    }

    /*
     * Closes the image windows
     */
    public void closeWindow () {
        if ( originalPicture != null ) {
            originalPicture.closeWindow();
        }
        if ( collagePicture != null ) {
            collagePicture.closeWindow();
        }
    }
}