package election;

/* 
 * Election Analysis class which parses past election data for the house/senate
 * in csv format, and implements methods which can return information about candidates
 * and nationwide election results. 
 * 
 * It stores the election data by year, state, then election using nested linked structures.
 * 
 * The years field is a Singly linked list of YearNodes.
 * 
 * Each YearNode has a states Circularly linked list of StateNodes
 * 
 * Each StateNode has its own singly linked list of ElectionNodes, which are elections
 * that occured in that state, in that year.
 * 
 * This structure allows information about elections to be stored, by year and state.
 * 
 * @author Colin Sullivan
 */
public class ElectionAnalysis {

    // Reference to the front of the Years SLL
    private YearNode years;

    public YearNode years() {
        return years;
    }

    /*
     * Read through the lines in the given elections CSV file
     * 
     * Loop Though lines with StdIn.hasNextLine()
     * 
     * Split each line with:
     * String[] split = StdIn.readLine().split(",");
     * Then access the Year Name with split[4]
     * 
     * For each year you read, search the years Linked List
     * -If it is null, insert a new YearNode with the read year
     * -If you find the target year, skip (since it's already inserted)
     * 
     * If you don't find the read year:
     * -Insert a new YearNode at the end of the years list with the corresponding year.
     * 
     * @param file String filename to parse, in csv format.
     */
    public void readYears(String file) {
      StdIn.setFile(file);
      while (StdIn.hasNextLine()) {
        String[] split = StdIn.readLine().split(",");
        int year = Integer.parseInt(split[4]);
        if (years == null) {
          YearNode front = new YearNode(year);
          front.setYear(year);
          years = front;
        }
        else {
          YearNode ptr = years;
          while (ptr.getNext() != null && ptr.getYear() != year) {
            ptr = ptr.getNext();
          }
          if (ptr.getYear() == year) {
            continue;
          }
          else if (ptr.getNext() == null) {
            YearNode newNode = new YearNode(year);
            newNode.setYear(year);
            ptr.setNext(newNode);
          }
        }
      }
        
    }

    /*
     * Read through the lines in the given elections CSV file
     * 
     * Loop Though lines with StdIn.hasNextLine()
     * 
     * Split each line with:
     * String[] split = StdIn.readLine().split(",");
     * Then access the State Name with split[1] and the year with split[4]
     * 
     * For each line you read, search the years Linked List for the given year.
     * 
     * In that year, search the states list. If the target state exists, continue
     * onto the next csv line. Else, insert a new state node at the END of that year's
     * states list (aka that years "states" reference will now point to that new node).
     * Remember the states list is circularly linked.
     * 
     * @param file String filename to parse, in csv format.
     */
    public void readStates(String file) {
      StdIn.setFile(file);
      while (StdIn.hasNextLine()) {
        String[] split = StdIn.readLine().split(",");
        int year = Integer.parseInt(split[4]);
        String state = split[1];

        YearNode ptr = years;
        while (ptr != null) {
            if (ptr.getYear() == year) {
              break;
            }
            else {
            ptr = ptr.getNext();
            }
        }
        StateNode states = ptr.getStates();
        if (states == null){
          StateNode front = new StateNode();
          front.setStateName(state);
          ptr.setStates(front);
          front.setNext(front);
        }
        else {
          boolean exists = false;
          StateNode curr = ptr.getStates();
          do {
            if (curr.getStateName().equalsIgnoreCase(state)) {
              exists = true;
            }
            curr = curr.getNext();
          }while (curr != states);
          
        if (!exists){
            StateNode newNode = new StateNode();
            newNode.setStateName(state);
            newNode.setNext(states.getNext());
            states.setNext(newNode);
            ptr.setStates(newNode);
          }
        }


      }
        
    }

    /*
     * Read in Elections from a given CSV file, and insert them in the
     * correct states list, inside the correct year node.
     * 
     * Each election has a unique ID, so multiple people (lines) can be inserted
     * into the same ElectionNode in a single year & state.
     * 
     * Before we insert the candidate, we should check that they dont exist already.
     * If they do exist, instead modify their information new data.
     * 
     * The ElectionNode class contains addCandidate() and modifyCandidate() methods for you to use.
     * 
     * @param file String filename of CSV to read from
     */
    public void readElections(String file) {
		  StdIn.setFile(file);
      while (StdIn.hasNextLine()) {

        String[] split = StdIn.readLine().split(",");

        int raceID = Integer.parseInt(split[0]);

        String stateName = split[1];

        int officeID = Integer.parseInt(split[2]);

        boolean senate = split[3].equals("U.S. Senate");

        int year = Integer.parseInt(split[4]);

        String canName = split[5];

        String party = split[6];

        int votes = Integer.parseInt(split[7]);

        boolean winner = split[8].toLowerCase().equals("true");

        YearNode ptr = years;
        while (ptr != null) {
            if (ptr.getYear() == year) {
              break;
            }
            else {
            ptr = ptr.getNext();
            }
        }
        StateNode states = ptr.getStates();
        StateNode ptr2 = states;
        do {
          if (ptr2.getStateName().equalsIgnoreCase(stateName)) {
            break;
          }
          else {
          ptr2 = ptr2.getNext();
          }
        }while (ptr2 != ptr.getStates());

        if (ptr2.getElections() == null) {
          ElectionNode newNode = new ElectionNode();
          newNode.setRaceID(raceID);
          newNode.setSenate(senate);
          newNode.setoOfficeID(officeID);
          newNode.addCandidate(canName, votes, party, winner);
          ptr2.setElections(newNode);
        }
        else {
          ElectionNode ptr3 = ptr2.getElections();
          boolean exists = false;
          ElectionNode back = null;
          while (ptr3 != null) {
            if (ptr3.getRaceID() == raceID) {
              if (ptr3.isCandidate(canName)) {
                ptr3.modifyCandidate(canName, votes, party);
              }
              else {
                ptr3.addCandidate(canName, votes, party, winner);
              }
              exists = true;
              break;
            }
            back = ptr3;
            ptr3 = ptr3.getNext();
          }
          
          if (!exists) {
            ElectionNode newNode2 = new ElectionNode();
            newNode2.setRaceID(raceID);
            newNode2.setSenate(senate);
            newNode2.setoOfficeID(officeID);
            newNode2.addCandidate(canName, votes, party, winner);
            if (back != null) {
              back.setNext(newNode2);
            }
          }
        }
      }
        
    }

    /*
     * DO NOT EDIT
     * 
     * Calls the next method to get the difference in voter turnout between two
     * years
     * 
     * @param int firstYear First year to track
     * 
     * @param int secondYear Second year to track
     * 
     * @param String state State name to track elections in
     * 
     * @return int Change in voter turnout between two years in that state
     */
    public int changeInTurnout(int firstYear, int secondYear, String state) {
        // DO NOT EDIT
        int last = totalVotes(firstYear, state);
        int first = totalVotes(secondYear, state);
        return last - first;
    }

    /*
     * Given a state name, find the total number of votes cast
     * in all elections in that state in the given year and return that number
     * 
     * If no elections occured in that state in that year, return 0
     * 
     * Use the ElectionNode method getVotes() to get the total votes for any single
     * election
     * 
     * @param year The year to track votes in
     * 
     * @param stateName The state to track votes for
     * 
     * @return avg number of votes this state in this year
     */
    public int totalVotes(int year, String stateName) {
      YearNode ptr = years;
      boolean yearExists = false;
      while (ptr != null) {
          if (ptr.getYear() == year) {
            yearExists = true;
            break;
          }
          else {
          ptr = ptr.getNext();
          }
      }
      if (yearExists == true)
      {
        StateNode states = ptr.getStates();
        StateNode ptr2 = states;
        boolean stateExists = false;
        do {
          if (ptr2.getStateName().equalsIgnoreCase(stateName)) {
            stateExists = true;
            break;
          }
          else {
            ptr2 = ptr2.getNext();
            }
        }while (ptr2 != states);

        if(stateExists == true) {
          ElectionNode elections = ptr2.getElections();
          ElectionNode ptr3 = elections;
          int totalVotes = 0;
          while (ptr3 != null) {
            totalVotes += ptr3.getVotes();;
            ptr3 = ptr3.getNext();
          }
            return totalVotes;
        }
        else {
          return 0;
        }
      }
      else {
        return 0;
      }
    }

    /*
     * Given a state name and a year, find the average number of votes in that
     * state's elections in the given year
     * 
     * @param year The year to track votes in
     * 
     * @param stateName The state to track votes for
     * 
     * @return avg number of votes this state in this year
     */
    public int averageVotes(int year, String stateName) {
      YearNode ptr = years;
      boolean yearExists = false;
      while (ptr != null) {
          if (ptr.getYear() == year) {
            yearExists = true;
            break;
          }
          else {
          ptr = ptr.getNext();
          }
      }
      if (yearExists == true)
      {
        StateNode states = ptr.getStates();
        StateNode ptr2 = states;
        boolean stateExists = false;
        do {
          if (ptr2.getStateName().equalsIgnoreCase(stateName)) {
            stateExists = true;
            break;
          }
          ptr2 = ptr2.getNext();
        }while (ptr2.getStateName() != states.getStateName());
        if (stateExists == true) {
          ElectionNode elections = ptr2.getElections();
          ElectionNode ptr3 = elections;
          int totalVotes = 0;
          int numElections = 0; 
          while (ptr3 != null) {
            int votes = ptr3.getVotes();
            totalVotes += votes;
            ptr3 = ptr3.getNext();
            numElections++;
          }
          int avgVotes = totalVotes / numElections;
          return avgVotes;
        }
        else {
          return 0;
        }
      }
      else {
        return 0;
      }
    }

    /*
     * Given a candidate name, return the party they most recently ran with
     * 
     * Search each year node for elections with the given candidate
     * name. Update that party each time you see the candidates name and
     * return the party they most recently ran with
     * 
     * @param candidateName name to find
     * 
     * @return String party abbreviation
     */
    public String candidatesParty(String candidateName) {
		  YearNode ptr = years;
      String party = null;
      while (ptr != null) {
        StateNode states = ptr.getStates();
        StateNode ptr2 = states;
        do {
          ElectionNode elections = ptr2.getElections();
          ElectionNode ptr3 = elections;
          while (ptr3 != null) {
            if (ptr3.isCandidate(candidateName)) {
              party = ptr3.getParty(candidateName);
              break;
            }
            ptr3 = ptr3.getNext();
          }
          ptr2 = ptr2.getNext();
        } while (ptr2.getNext() != states);

        ptr = ptr.getNext();
      }
        return party;
    }

}