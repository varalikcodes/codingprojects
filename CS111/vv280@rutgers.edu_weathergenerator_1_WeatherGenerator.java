/*
 *
 * @author: Haolin Jin
 * 
 * To generate weather for location at longitude -98.76 and latitude 26.70 for
 * the month of February do:
 * java WeatherGenerator -98.76 26.70 3
 */

public class WeatherGenerator {

    static final int WET = 1; // Use this value to represent a wet day
    static final int DRY = 2; // Use this value to represent a dry day 
    
    // Number of days in each month, January is index 0, February is index 1...
    static final int[] numberOfDaysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    
    
    public static void populateArrays(double[][] drywet, double[][] wetwet) {

        StdIn.setFile("drywet.txt");

	for(int i=0; i < drywet.length; i++){
            for(int j=0; j<14; j++){
                drywet[i][j] = StdIn.readDouble();
            }
        }

	StdIn.setFile("wetwet.txt");

	for(int i=0; i < drywet.length; i++){
            for(int j=0; j<14; j++){
                wetwet[i][j] = StdIn.readDouble();
            }
        }
    }

    
    public static void populateLocationProbabilities( double[] drywetProbability, double[] wetwetProbability, double longitude, double latitude,  double[][] drywet, double[][] wetwet){
           
            int x =0;
            for(int i = 0; i<drywet.length; i++){
                
                    if(drywet[i][0] == longitude && drywet[i][1] == latitude ){
                        
                        for(int j = 2; j<14; j++){
                            drywetProbability[x] = drywet[i][j];
                            wetwetProbability[x] = wetwet[i][j];

                            x++;
                            
                        }
                         
                        return;
                    }
                

              }
              
              
                
        
    }

    
    public static int[] forecastGenerator( double drywetProbability, double wetwetProbability, int numberOfDays) {
        int [] arr = new int [numberOfDays];
        
        double day1 = StdRandom.uniform();
    
        if(day1<=0.5){
            arr[0] = WET;
        }else{
            arr[0] =DRY;
        }

        for(int i = 1; i<arr.length; i++){

          double x = StdRandom.uniform();
           // System.out.println(x);
            if(arr[i-1] == DRY){
                if(x<=drywetProbability){
                    arr[i] = WET;
                }
                else{
                    arr[i] = DRY; 
                }

            }else{
                if(x<=wetwetProbability){
                    arr[i] = WET;
                }
                else{
                    arr[i] = DRY; 
                }
            }
        }

        return arr;
    }

    
    public static int[] oneMonthForecast(int numberOfLocations, int month, double longitude, double latitude ){
        double[][] drywet = new double[numberOfLocations][14];
          double[][] wetwet = new double[numberOfLocations][14];
          
          populateArrays(drywet, wetwet);
        double[] drywetProbability = new double[12];
          double[] wetwetProbability = new double[12];
       populateLocationProbabilities(drywetProbability, wetwetProbability, longitude, latitude, drywet, wetwet); 
        int numberOfDays = 0;

        switch(month){
            case 0: 
            numberOfDays = 31;
            break;
            case 1: numberOfDays = 28;
            break;
            case 2: numberOfDays = 31;
            break;
            case 3: numberOfDays = 30;
            break;
            case 4: numberOfDays = 31;
            break;
            case 5: numberOfDays = 30;
            break;
            case 6: numberOfDays = 31;
            break;
            case 7: numberOfDays = 31;
            break;
            case 8: numberOfDays = 30;
            break;
            case 9: numberOfDays = 31;
            break;
            case 10: numberOfDays = 30;
            break;
            case 11: numberOfDays = 31;
            break;

        }
      
      
      
       int [] forecast = forecastGenerator(drywetProbability[month], wetwetProbability[month], numberOfDays);

       return forecast;
    }

    
    public static int numberOfWetDryDays (int[] forecast, int mode) {
        int mode_count = 0;
        for(int i = 0; i< forecast.length; i++){
            if(forecast[i] == mode){
                mode_count++;
            }

        }
        return mode_count;
        
    }

   
    public static int lengthOfLongestSpell (int[] forecast, int mode) {
        int max = Integer.MIN_VALUE;
        int consec_count =0;
        for(int i = 0; i<forecast.length; i++){
                if(forecast[i] == mode){
                    consec_count +=1;
                    if(consec_count >max){
                        max = consec_count;
                    }
                }else{
                    consec_count = 0;
                }
        }
        return max;
    }

    
    public static int bestWeekToTravel(int[] forecast){
        int max = Integer.MIN_VALUE;
        int consec_count =0;
        int index = 0;
        
        for(int i = 0; i<forecast.length; i++){
                if(forecast[i] == DRY){
                    consec_count +=1;
                    if(consec_count >= 7){
                        max = consec_count;
                        index = i;
                    }
                }else{
                    consec_count = 0;
                }

        }

        if(max>=7){
            return (index-max)+1;
        }
        else{
            return -1;
        }
    }

   
    public static void main (String[] args) {

        int numberOfRows    = 4100; // Total number of locations
        int numberOfColumns = 14;   // Total number of 14 columns in file 
        
        // File format: longitude, latitude, 12 months of transition probabilities
        double longitude = Double.parseDouble(args[0]);
        double latitude  = Double.parseDouble(args[1]);
        int    month     = Integer.parseInt(args[2]);
        
        int[] forecast = oneMonthForecast( numberOfRows,  month,  longitude,  latitude );
        
  

        int drySpell = lengthOfLongestSpell(forecast, DRY);
        int wetSpell = lengthOfLongestSpell(forecast, WET);
        int bestWeek = bestWeekToTravel(forecast);

        StdOut.println("There are " + forecast.length + " days in the forecast for month " + month);
        StdOut.println(drySpell + " days of dry spell.");
        StdOut.println("The bestWeekToTravel starts on:" + bestWeek );


    }
}
