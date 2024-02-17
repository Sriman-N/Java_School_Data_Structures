import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

 /**
  *  The QuickFindUF class represents a union–find abstract data type that 
  *  implements the quick-find algorithm.
  *  A 1D array is used to keep track of the component IDs. 
  *  Array indexes represent the elements. Elements with the same component  
  *  ID are in the same connected component.
  *
  *  Run without debugging --> copy the data in tinyUF.txt and paste them on the
  *                            terminal. CTRL + D to stop the loop.
  *                            (CTRL + SHIFT + D for MacOS)
  *
  *  @author Robert Sedgewick
  *  @author Kevin Wayne
  *  @author Lily Chang, modified on Jan. 22, 2024.
  */
 
 public class QuickFindUF {
     private int[] id;    // id[i] = component id of element i
     private int count;   // number of connected components
 
     /**
      * Constructor to initialize an empty union-find data structure n elements.  
      * Initially, each element is in its own set. There are n disjoint sets initially
      *
      * @param  n the number of elements
      */
     public QuickFindUF(int n) {
         count = n; //there are n connected components intially
         id = new int[n];
         for (int i = 0; i < n; i++) //index i represent the element number
             id[i] = i; //initialize the component id to itself
     }
 
     /**
      * Returns the number of sets.
      *
      * @return the number of sets (between 1 and n)
      */
     public int count() {
         return count;
     }
 
     /**
      * Returns the component ID containing element p.
      *
      * @param  p an element
      * @return the canonical element of the set containing p
      */
     public int find(int p) {
         return id[p]; //component id of p
     }
 
     /**
      * Returns true if the two elements are in the same set.
      *
      * @param  p one element
      * @param  q the other element
      * @return true if p and q have the same component ID; false otherwise
      */
     public boolean connected(int p, int q) {
         return id[p] == id[q]; //if the IDs are the same, they are connected.
     }
 
     /**
      * Merges the set containing element p with the set containing element q.
      *
      * @param  p one element
      * @param  q the other element
      */
     public void union(int p, int q) {
         int pID = id[p];    
         int qID = id[q];    
 
         // p and q are already in the same disjoint set
         if (pID == qID) return;
 
         for (int i = 0; i < id.length; i++)
             if (id[i] == pID) id[i] = qID; //change the id of p to id of q
         count--; //merge 2 disjoint sets, so the number of connected component decreased by 1
     }
     
     /**
      * Print the current id[] array.
      */
     public void printComponent() {
        for (int i = 0; i < id.length; i++) {
            System.out.println("component id:" + id[i] + " element: " + i);
        }
     }

     /**
      * Reads an integer n and a sequence of pairs of integers between 0 and n-1
      * if the elements are in different sets, merge (union) the two sets
      * and print the pair to standard output.
      * @param args the command-line arguments
      */
     public static void main(String[] args) {
         int n = StdIn.readInt();
         QuickFindUF uf = new QuickFindUF(n);
         while (!StdIn.isEmpty()) {
             int p = StdIn.readInt();
             int q = StdIn.readInt();
             if (uf.find(p) == uf.find(q)) continue; //if they are in the same component
             uf.union(p, q); //if they are not in the same component, add the edge
             StdOut.println("union " + p + " " + q);
             uf.printComponent(); //print the current disjoint set
         }
         StdOut.println(uf.count() + " connected components");
     }
 
 }
 
 /******************************************************************************
  *  Copyright 2002-2022, Robert Sedgewick and Kevin Wayne.
  *
  *  This file is part of algs4.jar, which accompanies the textbook
  *
  *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
  *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
  *      http://algs4.cs.princeton.edu
  *
  *
  *  algs4.jar is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  algs4.jar is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU General Public License for more details.
  *
  *  You should have received a copy of the GNU General Public License
  *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
  ******************************************************************************/