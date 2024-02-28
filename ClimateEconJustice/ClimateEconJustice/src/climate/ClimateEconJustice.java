package climate;

import java.util.ArrayList;



/**
 * This class contains methods which perform various operations on a layered 
 * linked list structure that contains USA communitie's Climate and Economic information.
 * 
 * @author Navya Sharma
 */

public class ClimateEconJustice {

    private StateNode firstState;
    
    /*
    * Constructor
    * 
    * **** DO NOT EDIT *****
    */
    public ClimateEconJustice() {
        firstState = null;
    }

    /*
    * Get method to retrieve instance variable firstState
    * 
    * @return firstState
    * 
    * **** DO NOT EDIT *****
    */ 
    public StateNode getFirstState () {
        // DO NOT EDIT THIS CODE
        return firstState;
    }

    /**
     * Creates 3-layered linked structure consisting of state, county, 
     * and community objects by reading in CSV file provided.
     * 
     * @param inputFile, the file read from the Driver to be used for
     * @return void
     * 
     * **** DO NOT EDIT *****
     */
    public void createLinkedStructure ( String inputFile ) {
        
        // DO NOT EDIT THIS CODE
        StdIn.setFile(inputFile);
        StdIn.readLine();
        
        // Reads the file one line at a time
        while ( StdIn.hasNextLine() ) {
            // Reads a single line from input file
            String line = StdIn.readLine();
            // IMPLEMENT these methods
            addToStateLevel(line);
            addToCountyLevel(line);
            addToCommunityLevel(line);
        }
    }

    /*
    * Adds a state to the first level of the linked structure.
    * Do nothing if the state is already present in the structure.
    * 
    * @param inputLine a line from the input file
    */
    public void addToStateLevel ( String inputLine ) {
        
        StateNode cur = null;
        StateNode prev = null;
        String[] lineArray = inputLine.split(",");

        //creates a new StateNode state
        StateNode state = new StateNode(lineArray[2], null, null);

        //if firstState is null then state becomes firstState, else the statenode cur will become the first state
        if(firstState == null) {
            firstState = state;
        } else {
             prev = null;
             cur = firstState;
        }

        //traversing through the state node
        while(cur != null) {

            //if state's name is equal to cur's name, then do nothing
            if(cur.getName().equals(state.getName())) {
                return;
            }

            //traversing through the state node by setting the prev to cur and cur to the next node
            prev = cur;
            cur = cur.getNext();
        }

        //sets firstState to the first StateNode
        if(prev == null) {
            firstState = state;
        } else {
            prev.setNext(state);
        }
    }

    /*
    * Adds a county to a state's list of counties.
    * 
    * Access the state's list of counties' using the down pointer from the State class.
    * Do nothing if the county is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCountyLevel ( String inputLine ) {

        StateNode curS = firstState;
        String[] lineArray = inputLine.split(",");
        String state = lineArray[2];

        //creates a new County node
        CountyNode county = new CountyNode(lineArray[1], null, null);

        //if state node's county node is null, then create the county node inside the state node
        if(curS.getDown() == null) {
            curS.setDown(county);
        }

        //gets the state node that is equal to state from line 123
        while(curS != null && !curS.getName().equals(state)) {
            curS = curS.getNext();
        }

        //creates the county node and connects it inside the state node
        CountyNode curC = curS.getDown();
        CountyNode prevC = null;

        //traversing through the County Node
        while(curC != null) {

            //if curC's name is equal to county's name, then it will stop
            if(curC.getName().equals(county.getName())) {
                return;
            }
            prevC = curC;
            curC = curC.getNext();
        }

        //if prevC is null still after the while loop above it will set 
        if(prevC == null) {
            curS.setDown(county);
        } else {
            prevC.setNext(county);
        }
    }

    /*
    * Adds a community to a county's list of communities.
    * 
    * Access the county through its state
    *      - search for the state first, 
    *      - then search for the county.
    * Use the state name and the county name from the inputLine to search.
    * 
    * Access the state's list of counties using the down pointer from the StateNode class.
    * Access the county's list of communities using the down pointer from the CountyNode class.
    * Do nothing if the community is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCommunityLevel ( String inputLine ) {

        StateNode curS = firstState;
        String[] inputArray = inputLine.split(",");
        String county = inputArray[1];
        String state = inputArray[2];
        double percAfricanAmerican = Double.parseDouble(inputArray[3]);
        double percNative = Double.parseDouble(inputArray[4]);
        double percAsian = Double.parseDouble(inputArray[5]);
        double percWhite = Double.parseDouble(inputArray[8]);
        double percHispanic = Double.parseDouble(inputArray[9]);
        String disadvantage = inputArray[19];
        double pmLevel = Double.parseDouble(inputArray[49]);
        double chanceFlood = Double.parseDouble(inputArray[37]);
        double povertyLine = Double.parseDouble(inputArray[121]);

        Data data = new Data(percAfricanAmerican,percNative, percAsian, percWhite,
                            percHispanic, disadvantage, pmLevel, chanceFlood,
                            povertyLine);

        CommunityNode community = new CommunityNode(inputArray[0], null, data);

        //goes through the State linkedlist and finds the one node where that specific node is the same as the state
        while(curS != null && !curS.getName().equals(state)) {
            curS = curS.getNext();
        }

        CountyNode curC = curS.getDown();

        //goes through the County linkedlist and finds the one node where that specific node is the same as the county
        while(curC != null && !curC.getName().equals(county)) {
            curC = curC.getNext();
        }

        //if the county.getdown is null then it sets down the new node
        if(curC.getDown() == null) {
            curC.setDown(community);
        }

        CommunityNode curCom = curC.getDown();
        CommunityNode prevCom = null;

        //this goes through the community linkedlist
        while(curCom != null) {
            //this checks if the node.name is already present in the linkedlist, if so, then it stops
            if(curCom.getName().equals(inputArray[0])) {
                return;
            }
            prevCom = curCom;
            curCom = curCom.getNext();
        }

        //if prevCom is still null, then it will add the new community
        if(prevCom == null) {
            curC.setDown(community);
        } else {
            prevCom.setNext(community);
        }
    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int disadvantagedCommunities ( double userPrcntage, String race ) {

        StateNode curS = firstState;
        int count = 0;

        //traversing through the state node
        while(curS != null) {
            CountyNode curC = curS.getDown();
            //traversing through the county node
            while(curC != null) {
                CommunityNode curCom = curC.getDown();
                double percentage = 0.0;
                //traversing through the community node
                while(curCom != null) {
                    //gets the data from each community
                    Data data = curCom.getInfo();

                    //goes through each 'race' choice
                    switch (race) {
                        case "African American":
                            percentage = data.getPrcntAfricanAmerican() * 100;
                            break;
                        case "Native American":
                            percentage = data.getPrcntNative() * 100;
                            break;
                        case "Asian American":
                            percentage = data.getPrcntAsian() * 100;
                            break;
                        case "White American":
                            percentage = data.getPrcntWhite() * 100;
                            break;
                        case "Hispanic American":
                            percentage = data.getPrcntHispanic() * 100;
                            break;
                        default:
                            break;
                    }

                    // if percentage is greater or equal to user_percentage, and advantageStatus from data is true, then count increases
                    if(percentage >= userPrcntage && data.getAdvantageStatus().equals("True")) {
                        count++;
                    }
                    curCom = curCom.getNext();
                }
                curC = curC.getNext();
            }
            curS = curS.getNext();
        }

        return count;
    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as non disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int nonDisadvantagedCommunities ( double userPrcntage, String race ) {

        StateNode curS = firstState;
        int count = 0;

        //traversing through state nodes

        while(curS != null) {
            CountyNode curC = curS.getDown();

            //traversing through county nodes

            while(curC != null) {
                CommunityNode curCom = curC.getDown();
                double percentage = 0.0;

                //traversing through community nodes
                while(curCom != null) {

                    //gets the data from each community
                    Data data = curCom.getInfo();

                    //goes through each 'race' choice
                    switch (race) {
                        case "African American":
                            percentage = data.getPrcntAfricanAmerican() * 100;
                            break;
                        case "Native American":
                            percentage = data.getPrcntNative() * 100;
                            break;
                        case "Asian American":
                            percentage = data.getPrcntAsian() * 100;
                            break;
                        case "White American":
                            percentage = data.getPrcntWhite() * 100;
                            break;
                        case "Hispanic American":
                            percentage = data.getPrcntHispanic() * 100;
                            break;
                        default:
                            break;
                    }

                    // if percentage is greater or equal to user_percentage, and advantageStatus from data is false, then count increases
                    if(percentage >= userPrcntage && data.getAdvantageStatus().equals("False")) {
                        count++;
                    }
                    curCom = curCom.getNext();
                }
                curC = curC.getNext();
            }
            curS = curS.getNext();
        }

        return count;
    }
    
    /** 
     * Returns a list of states that have a PM (particulate matter) level
     * equal to or higher than value inputted by user.
     * 
     * @param PMlevel the level of particulate matter
     * @return the States which have or exceed that level
     */ 
    public ArrayList<StateNode> statesPMLevels ( double PMlevel ) {

        ArrayList<StateNode> arrayListState = new ArrayList<>();
        StateNode curS = firstState;

        //traversing through State node
        while(curS != null) {
            CountyNode curC = curS.getDown();

            //traversing through county node
            while(curC != null) {
                CommunityNode curCom = curC.getDown();

                //traversing through community node
                while(curCom != null) {
                    Data data = curCom.getInfo();

                    //if data from each community is greater than the user inputted pm level 'PMlevel' and then if
                    // the arraylist has that contained state from the community, it adds it into the array list
                    if(data.getPMlevel() >= PMlevel) {
                        if(!arrayListState.contains(curS)) {
                            arrayListState.add(curS);
                        }
                    }

                    curCom = curCom.getNext();
                }
                curC = curC.getNext();
            }
            curS = curS.getNext();
        }

        return arrayListState;
    }

    /**
     * Given a percentage inputted by user, returns the number of communities 
     * that have a chance equal to or higher than said percentage of
     * experiencing a flood in the next 30 years.
     * 
     * @param userPercntage the percentage of interest/comparison
     * @return the amount of communities at risk of flooding
     */
    public int chanceOfFlood ( double userPercntage ) {

        StateNode curS = firstState;
        int count = 0;

        //traversing through state node
        while(curS != null) {
            CountyNode curC = curS.getDown();

            //traversing through county node
            while(curC != null) {
                CommunityNode curCom = curC.getDown();

                //traversing through community node
                while(curCom != null) {
                    Data data = curCom.getInfo();

                    //the data's chance of flood from community is greater than or equal to the user input percentage
                    //then count increases

                    if(data.getChanceOfFlood() >= userPercntage) {
                        count++;
                    }
                    curCom = curCom.getNext();
                }
                curC = curC.getNext();
            }
            curS = curS.getNext();
        }

        return count;
    }

    /** 
     * Given a state inputted by user, returns the communities with 
     * the 10 lowest incomes within said state.
     * 
     *  @param stateName the State to be analyzed
     *  @return the top 10 lowest income communities in the State, with no particular order
    */
    public ArrayList<CommunityNode> lowestIncomeCommunities ( String stateName ) {

        StateNode curS = firstState;
        //creates an array list that has 10 slots
        ArrayList<CommunityNode> communityNodeArrayList = new ArrayList<>(10);

        //while loop continues when state node is not null and state node's name is equal to stateName
        while (curS != null && !(curS.getName().equals(stateName))) {
            curS = curS.getNext();
        }

        CountyNode curC = curS.getDown();

        //traversing through county node
        while(curC != null) {
            CommunityNode curCom = curC.getDown();

            //traversing through community node
            while(curCom != null) {

                //if communityNodeArrayList is empty, then add current community node
                if(communityNodeArrayList.isEmpty()) {
                    communityNodeArrayList.add(curCom);

                // else if communityNodeArrayList is full (meaning 10 slots are full), then do the following:
                } else if(communityNodeArrayList.size() == 10) {
                    int index = 0;

                    //poverty is equal to the poverty line from the data from the first community inside the arrayList
                    double poverty = communityNodeArrayList.get(0).getInfo().getPercentPovertyLine();

                    //going through the communityNodeArrayList
                    for(int i = 0; i < communityNodeArrayList.size(); i++) {

                        //if poverty is greater than the poverty line from the data from each community inside the arrayList
                        //then it will set the index i to index and the poverty will set to poverty line from the data from
                        //ith's arraylist's community
                        if(communityNodeArrayList.get(i).getInfo().getPercentPovertyLine() < poverty) {
                            index = i;
                            poverty = communityNodeArrayList.get(i).getInfo().getPercentPovertyLine();
                        }
                    }

                    //if the current's community node's poverty line is greater than the poverty line in the index's community
                    //node from the arraylist
                    if(curCom.getInfo().getPercentPovertyLine() > communityNodeArrayList.get(index).getInfo().getPercentPovertyLine()) {
                        communityNodeArrayList.set(index, curCom);
                    }
                // else if the communityNodeArrayList's size is in the range of 1 to 9 (inclusive) then it will add the current community
                // node in the arraylist
                } else {
                    communityNodeArrayList.add(curCom);
                }
                curCom = curCom.getNext();
            }
            curC = curC.getNext();
        }

        return communityNodeArrayList; // replace this line
    }
}
    
