package forensic;


import java.util.*;

import javax.swing.plaf.metal.MetalIconFactory.TreeControlIcon;

/**
 * This class represents a forensic analysis system that manages DNA data using
 * BSTs.
 * Contains methods to create, read, update, delete, and flag profiles.
 * 
 * @author Kal Pandit
 */
public class ForensicAnalysis {

    private TreeNode treeRoot;            // BST's root
    private String firstUnknownSequence;
    private String secondUnknownSequence;

    public ForensicAnalysis () {
        treeRoot = null;
        firstUnknownSequence = null;
        secondUnknownSequence = null;
    }

    /**
     * Builds a simplified forensic analysis database as a BST and populates unknown sequences.
     * The input file is formatted as follows:
     * 1. one line containing the number of people in the database, say p
     * 2. one line containing first unknown sequence
     * 3. one line containing second unknown sequence
     * 2. for each person (p), this method:
     * - reads the person's name
     * - calls buildSingleProfile to return a single profile.
     * - calls insertPerson on the profile built to insert into BST.
     *      Use the BST insertion algorithm from class to insert.
     * 
     * DO NOT EDIT this method, IMPLEMENT buildSingleProfile and insertPerson.
     * 
     * @param filename the name of the file to read from
     */
    public void buildTree(String filename) {
        // DO NOT EDIT THIS CODE
        StdIn.setFile(filename); // DO NOT remove this line

        // Reads unknown sequences
        String sequence1 = StdIn.readLine();
        firstUnknownSequence = sequence1;
        String sequence2 = StdIn.readLine();
        secondUnknownSequence = sequence2;
        
        int numberOfPeople = Integer.parseInt(StdIn.readLine()); 

        for (int i = 0; i < numberOfPeople; i++) {
            // Reads name, count of STRs
            String fname = StdIn.readString();
            String lname = StdIn.readString();
            String fullName = lname + ", " + fname;
            // Calls buildSingleProfile to create
            Profile profileToAdd = createSingleProfile();
            // Calls insertPerson on that profile: inserts a key-value pair (name, profile)
            insertPerson(fullName, profileToAdd);
        }
    }

    /** 
     * Reads ONE profile from input file and returns a new Profile.
     * Do not add a StdIn.setFile statement, that is done for you in buildTree.
    */
    public Profile createSingleProfile() {
        int numOfStrs = StdIn.readInt();
        STR[] strArray = new STR[numOfStrs];

        for(int i = 0; i < numOfStrs; i++) {
            String strString = StdIn.readString();
            int occurrences = StdIn.readInt();
            STR str = new STR(strString, occurrences);

            strArray[i] = str;
        }

        return new Profile(strArray);
    }

    /**
     * Inserts a node with a new (key, value) pair into
     * the binary search tree rooted at treeRoot.
     * 
     * Names are the keys, Profiles are the values.
     * USE the compareTo method on keys.
     * 
     * @param newProfile the profile to be inserted
     */
    public void insertPerson(String name, Profile newProfile) {

        TreeNode tree = new TreeNode(name, newProfile, null, null);

        if(treeRoot == null) {
            treeRoot = tree;
        } else {
            TreeNode cur = treeRoot;

            while(cur != null) {
                if(cur.getName().compareTo(tree.getName()) > 0) {
                    if (cur.getLeft() == null) {
                        cur.setLeft(tree);
                        break;
                    } else {
                        cur = cur.getLeft();
                    }
                } else if (cur.getName().compareTo(tree.getName()) < 0) {
                    if (cur.getRight() == null) {
                        cur.setRight(tree);
                        break;
                    } else {
                        cur = cur.getRight();
                    }
                }

            }
        }
    }

    /**
     * Finds the number of profiles in the BST whose interest status matches
     * isOfInterest.
     *
     * @param isOfInterest the search mode: whether we are searching for unmarked or
     *                     marked profiles. true if yes, false otherwise
     * @return the number of profiles according to the search mode marked
     */
    public int getMatchingProfileCount(boolean isOfInterest) {
        TreeNode cur = treeRoot;

        return traverseWithIsofinterest(cur, isOfInterest);

    }

    /**
     * Helper method that counts the # of STR occurrences in a sequence.
     * Provided method - DO NOT UPDATE.
     * 
     * @param sequence the sequence to search
     * @param STR      the STR to count occurrences of
     * @return the number of times STR appears in sequence
     */
    private int numberOfOccurrences(String sequence, String STR) {
        
        // DO NOT EDIT THIS CODE
        
        int repeats = 0;
        // STRs can't be greater than a sequence
        if (STR.length() > sequence.length())
            return 0;
        
            // indexOf returns the first index of STR in sequence, -1 if not found
        int lastOccurrence = sequence.indexOf(STR);
        
        while (lastOccurrence != -1) {
            repeats++;
            // Move start index beyond the last found occurrence
            lastOccurrence = sequence.indexOf(STR, lastOccurrence + STR.length());
        }
        return repeats;
    }

    /**
     * Traverses the BST at treeRoot to mark profiles if:
     * - For each STR in profile STRs: at least half of STR occurrences match (round
     * UP)
     * - If occurrences THROUGHOUT DNA (first + second sequence combined) matches
     * occurrences, add a match
     */
    public void flagProfilesOfInterest() {
        Stack<TreeNode> s = new Stack<TreeNode>();
        TreeNode cur = treeRoot;

        while(cur != null || !s.isEmpty()) {
            while(cur != null) {
                s.push(cur);
                cur = cur.getLeft();
            }

            cur = s.pop();
            int halfLenSTRs = (cur.getProfile().getStrs().length + 1) / 2;

            STR[] strs = cur.getProfile().getStrs();
            
            int countMatchingSTRs = 0;
            for(int i = 0; i < strs.length; i++) {
                int firstSequenceOccur = numberOfOccurrences(firstUnknownSequence, strs[i].getStrString());
                int secondSequenceOccur = numberOfOccurrences(secondUnknownSequence, strs[i].getStrString());
                int profileOccur = strs[i].getOccurrences();

                if(profileOccur == firstSequenceOccur + secondSequenceOccur) {
                    countMatchingSTRs++;
                }
            }

            if(countMatchingSTRs >= halfLenSTRs) {
                cur.getProfile().setInterestStatus(true);
            }
            cur = cur.getRight();
        }
    }

    /**
     * Uses a level-order traversal to populate an array of unmarked Strings representing unmarked people's names.
     * - USE the getMatchingProfileCount method to get the resulting array length.
     * - USE the provided Queue class to investigate a node and enqueue its
     * neighbors.
     * 
     * @return the array of unmarked people
     */
    public String[] getUnmarkedPeople() {

        Queue<TreeNode> q = new Queue<TreeNode>();
        String[] array = new String[getMatchingProfileCount(false)];
        TreeNode cur = treeRoot;
        q.enqueue(cur);
      
        int index = 0;
        while(cur != null && !q.isEmpty()) {
            cur = q.dequeue();
            if(cur.getProfile().getMarkedStatus() == false) {
                array[index] = cur.getName();
                index++;
            }
        
            if(cur.getLeft() != null) {
                q.enqueue(cur.getLeft());
            }
            if(cur.getRight() != null) {
                q.enqueue(cur.getRight());
            }
        }

        
        return array; // update this line
    }

    /**
     * Removes a SINGLE node from the BST rooted at treeRoot, given a full name (Last, First)
     * This is similar to the BST delete we have seen in class.
     * 
     * If a profile containing fullName doesn't exist, do nothing.
     * You may assume that all names are distinct.
     * 
     * @param fullName the full name of the person to delete
     */
    public void removePerson(String fullName) {

        treeRoot = delete(treeRoot, fullName);

    }

    /**
     * Clean up the tree by using previously written methods to remove unmarked
     * profiles.
     * Requires the use of getUnmarkedPeople and removePerson.
     */
    public void cleanupTree() {
        String[] unmarkedPeople = getUnmarkedPeople();

        for(int i = 0; i < unmarkedPeople.length; i++) {
            removePerson(unmarkedPeople[i]);
        }
    }

    /**
     * Gets the root of the binary search tree.
     *
     * @return The root of the binary search tree.
     */
    public TreeNode getTreeRoot() {
        return treeRoot;
    }

    /**
     * Sets the root of the binary search tree.
     *
     * @param newRoot The new root of the binary search tree.
     */
    public void setTreeRoot(TreeNode newRoot) {
        treeRoot = newRoot;
    }

    /**
     * Gets the first unknown sequence.
     * 
     * @return the first unknown sequence.
     */
    public String getFirstUnknownSequence() {
        return firstUnknownSequence;
    }

    /**
     * Sets the first unknown sequence.
     * 
     * @param newFirst the value to set.
     */
    public void setFirstUnknownSequence(String newFirst) {
        firstUnknownSequence = newFirst;
    }

    /**
     * Gets the second unknown sequence.
     * 
     * @return the second unknown sequence.
     */
    public String getSecondUnknownSequence() {
        return secondUnknownSequence;
    }

    /**
     * Sets the second unknown sequence.
     * 
     * @param newSecond the value to set.
     */
    public void setSecondUnknownSequence(String newSecond) {
        secondUnknownSequence = newSecond;
    }

    private int traverseWithIsofinterest(TreeNode x, boolean isOfInterest) {

        if(x == null) {
            return 0;
        }

        int count = 0;

        if(x.getProfile().getMarkedStatus() == isOfInterest) {
            count++;
        }

        count+=traverseWithIsofinterest(x.getLeft(), isOfInterest);
        count+=traverseWithIsofinterest(x.getRight(), isOfInterest);

        return count;
    }

    private TreeNode min(TreeNode t) {
        if(t.getLeft() == null) {
            return t;
        } else {
            return min(t.getLeft());
        }
    }

    private TreeNode deleteMin(TreeNode x) {
        if(x.getLeft() == null) {
            return x.getRight();
        }
        x.setLeft(deleteMin(x.getLeft()));
        return x;
    }
    private TreeNode delete(TreeNode x, String fullName) {
        if(x == null) return null;
        int cmp = fullName.compareTo(x.getName());
        if(cmp > 0) {
            x.setRight(delete(x.getRight(), fullName));
        } else if(cmp < 0) {
            x.setLeft(delete(x.getLeft(), fullName));
        } else {
            if(x.getRight() == null) {
                return x.getLeft();
            }
            if(x.getLeft() == null) {
                return x.getRight();
            }

            TreeNode t = x;
            x = min(t.getRight());
            x.setRight(deleteMin(t.getRight()));
            x.setLeft(t.getLeft());
        }
        
        return x;
    }

}
