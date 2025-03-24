package quality;

import java.util.ArrayList;

/**
 * This class represents the AirQuality system which populates a hashtable with states and counties 
 * and calculates various statistics related to air quality.
 * 
 * This class is a part of the AirQuality project.
 * 
 * @author Anna Lu
 * @author Srimathi Vadivel
 */

public class AirQuality {

    private State[] states; // hash table used to store states. This HT won't need rehashing.

    /**
     * **DO NOT MODIFY THIS METHOD**
     * Constructor creates a table of size 10.
     */

    public AirQuality () {
        states  = new State[10];
    }     

    /**
     ** *DO NOT MODIFY THIS METHOD**
     * Returns the hash table.
     * @return the value held to represent the hash table
     */
    public State[] getHashTable() {
        return states;
    }
    
    /**
     * 
     * DO NOT UPDATE THIS METHOD
     * 
     * This method populates the hashtable with the information from the inputFile parameter.
     * It is expected to insert a state and then the counties into each state.
     * 
     * Once a state is added, use the load factor to check if a resize of the hash table
     * needs to occur.
     * 
     * @param inputLine A line from the inputFile with the following format:
     * State Name,County Name,AQI,Latitude,Longitude,Pollutant Name,Color
     */

    public void buildTable ( String inputFile ) {
        
        StdIn.setFile(inputFile); // opens the inputFile to be read
        StdIn.readLine();         // skips header
        
        while ( !StdIn.isEmpty() ) {

            String line = StdIn.readLine(); 
            State s = addState( line );
            addCountyAndPollutant(s, line );
        }
    }
    
    /**
     * Inserts a single State into the hash table states.
     * 
     * Note: No duplicate states allowed. If the state is already present, simply
     * return the State object. Otherwise, insert at the front of the list.
     * 
     * @param inputLine A line from the inputFile with the following format:
     * State Name,County Name,AQI,Latitude,Longitude,Pollutant Name,Color
     * 
     * USE: Math.abs("State Name".hashCode()) as the key into the states hash table.
     * USE: hash function as: hash(key) = key % array length
     * 
     * @return the State object if already present in the table or the newly created
     * State object inserted.
     */

    public State addState ( String inputLine ) {

        String[] split = inputLine.split(",");
        String stateName = split[0];

        int key = Math.abs(stateName.hashCode());
        int ind = key % states.length;

        State curr = states[ind];
        while (curr != null) {
            if (curr.getName().equals(stateName)) {
                return curr;
            }
            curr = curr.getNext();
        }

        State newState = new State(stateName);
        newState.setNext(states[ind]);
        states[ind] = newState;

        return newState;
    }
    
    /**
     * Returns true if the counties hash table (within State) needs to be resized (re-hashed) 
     *
     * Resize the hash table when (number of counties)/(array size) >= loadFactor
     * 
     * @return true if resizing needs to happen, false otherwise
     */

     public boolean checkCountiesHTLoadFactor ( State state ) {

	    if ((state.getNumberOfCounties() / state.getCounties().length) >= state.getLoadFactor()) {
            return true;
        }
	 
	    return false; // update this line
    }

    /**
     * Resizes (rehashes) the State's counties hashtable by doubling its size.
     * 
     * USE: county.hashCode() as the key into the State's counties hash table.
     */
    public void rehash ( State state ) {
        
        County[] oldCounties = state.getCounties();
        int newSize = oldCounties.length * 2;
        County[] newCounties = new County[newSize];

        for (int i = 0; i < oldCounties.length; i++) {
            County curr = oldCounties[i];
            while (curr != null) {
                String countyName = curr.getName();
                int index = Math.abs(countyName.hashCode() % (newSize));

                County toAdd = new County(countyName, curr.getLatitude(), curr.getLongitude(), newCounties[index]);
                toAdd.setPollutants(curr.getPollutants());
                newCounties[index] = toAdd;
                curr = curr.getNext();
            }
        }
        state.setCounties(newCounties);
    }

    /**
     * This method:
     * 1) Inserts the county (from the input line) into State, if not already present.
     *    Check the State's counties hash table load factor after inserting. The hash table may need
     *    to be resized.
     * 
     * 2) Then inserts the pollutant (from the input line) into County (from the input line), if not already present.
     *    If pollutant is present, update AQI.
     * 
     * Note: no duplicate counties in the State.
     * Note: no duplicate pollutants in the County.
     * 
     * @param inputLine A line from the inputFile with the following format:
     * State Name,County Name,AQI,Latitude,Longitude,Pollutant Name,Color
     * 
     * USE: Math.abs("County Name".hashCode()) as the key into the State's counties hash table.
     * USE: the hash function as: hash(key) = key % array length
     */

    public void addCountyAndPollutant ( State state, String inputLine ) {

        String[] split = inputLine.split(",");
        String countyName = split[1];
        int aqi = Integer.parseInt(split[2]);
        double latitude = Double.parseDouble(split[3]);
        double longitude = Double.parseDouble(split[4]);
        String pollutant= split[5];
        String color = split[6];

        int key = Math.abs(countyName.hashCode());
        int ind = key % (state.getCounties().length);

        County curr = state.getCounties()[ind];
        County countyExists = null;
        while (curr != null) {
            if(curr.getName().equals(countyName)) {
                countyExists = curr;
                break;
            }
            else {
                curr = curr.getNext();
            }
        }
        if (countyExists == null) {
            County toAdd = new County(countyName, latitude, longitude, state.getCounties()[ind]);
            state.addCounty(toAdd);
            if (checkCountiesHTLoadFactor(state)) {
                rehash(state);
            }
            countyExists = toAdd;
            /*
            ArrayList<Pollutant> pollutants = toAdd.getPollutants();
            Pollutant newPollutant = new Pollutant(pollutant, aqi, color);
            pollutants.add(newPollutant);
            */
        }
        ArrayList<Pollutant> pollutants = countyExists.getPollutants();
        Pollutant pollutantExists = null;
        for(int i = 0; i < pollutants.size(); i++) {
            if (pollutants.get(i).getName().equals(pollutant)) {
                pollutantExists = pollutants.get(i);
                break;
            }
        }
        if (pollutantExists == null) {
            Pollutant newPollutant = new Pollutant(pollutant, aqi, color);
            pollutants.add(newPollutant);
        }
        else {
            pollutantExists.setAQI(aqi);
            pollutantExists.setColor(color);
        }
        
    }

    /**
     * Sets states' simple stats AQI for each State in the hash table.
     */
    public void setStatesAQIStats() {
        for (State state : states) {
            while (state != null) {
                double sumAQI = 0;
                int sumPollutants = 0;

                County highCounty = null;
                County lowCounty = null;
                int highAQI = Integer.MIN_VALUE;
                int lowAQI = Integer.MAX_VALUE;

                for (County county : state.getCounties()) {
                    while (county != null) {
                        for (Pollutant pollutant : county.getPollutants()) {
                            int currAQI = pollutant.getAQI();
                            sumPollutants++;
                            sumAQI += currAQI;

                            if (currAQI < lowAQI) {
                                lowAQI = currAQI;
                                lowCounty = county;
                            }
                            if (currAQI > highAQI) {
                                highAQI = currAQI;
                                highCounty = county;
                            }
                        }
                        county = county.getNext();
                    }
                }
                double avgAQI = sumAQI / sumPollutants;
                state.setAvgAQI(avgAQI);
                state.setHighestAQI(highCounty);
                state.setLowestAQI(lowCounty);
                state = state.getNext();
        }
    }
        
    }

    /**
     * In this method you will find all the counties within a state that have the same parameter name
     * and above the AQI threshold.
     * 
     * @param stateName The name of the state
     * @param pollutantName The parameter name to filter by
     * @param AQIThreshold The AQI threshold
     * @return ArrayList<County> List of counties that meet the criteria
     */

    public ArrayList<County> meetsThreshold(String stateName, String pollutantName, int AQIThreshold) {

        ArrayList<County> res = new ArrayList<>();
        int key = Math.abs(stateName.hashCode());
        int ind = key % states.length;

        State state = states[ind];
        while (state != null) {
            if (state.getName().equals(stateName)) {
                break;
            }
            state = state.getNext();
        }
        for (County county : state.getCounties()) {
            while (county != null) {
                for (Pollutant pollutant : county.getPollutants()) {
                    if (pollutant.getName().equals(pollutantName) && pollutant.getAQI() >= AQIThreshold) {
                        res.add(county);
                        break;
                    }
                }
                county = county.getNext();
            }
        }

        return res; // update this line
    } 

}
