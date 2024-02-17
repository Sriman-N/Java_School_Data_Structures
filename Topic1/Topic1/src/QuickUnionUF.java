

 import edu.princeton.cs.algs4.StdIn;
 import edu.princeton.cs.algs4.StdOut;

 /******************************************************************************
 *  The QuickUnionUF class represents a union–find abstract data type that 
 *  implements the quick-union algorithm.
 *  A 1D array is used to keep track of the parent nodes.
 *  If an element's parent is itself, then it is the root node. 
 *  Array indexes represent the elements. Elements with the same root are in 
 *  the same connected component (tree).
 *
 *  Run without debugging --> copy the data in tinyUF.txt and paste them on the
 *                            terminal. CTRL + D to stop the loop.
 *                            (CTRL + SHIFT + D for MacOS)
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Lily Chang, modified on Jan 23, 2024
 ******************************************************************************/
 public class QuickUnionUF {
     private int[] parent;  // parent[i] = parent of i
     private int count;     // number of connected components
 
     /**
      * Constructor to initialize an empty union-find data structure with n elements.
      * Initially, each element is in its own set, i.e., every element is a root node.
      * There are n trees (disjoint sets) initially.
      *
      * @param  n the number of elements 
      */
     public QuickUnionUF(int n) {
         parent = new int[n];
         count = n; //there are n connected components (n trees) initially
         for (int i = 0; i < n; i++) {
             parent[i] = i; //every element's parent is itself (every element is root)
         }
     }
 
     /**
      * Returns the number of sets.
      *
      * @return the number of sets between 1 and n
      */
     public int count() {
         return count;
     }
 
     /**
      * Returns the component id (root) of the set containing element p.
      *
      * @param  p an element
      * @return the component id containing p
      */
     public int find(int p) {
         while (p != parent[p]) //haven't reach the root node yet
             p = parent[p]; //traverse up to the root by following the parent
         return p; //end of while loop -> reach the root node; p the root  
     }
 
     /**
      * Returns true if the two elements are in the same set.
      *
      * @param  p one element
      * @param  q the other element
      * @return true p and q are in the same set; false otherwise
      * @throws IllegalArgumentException unless
      */
     public boolean connected(int p, int q) {
         return find(p) == find(q); //check if p and q have the same root node
     }
 
     /**
      * Merges the set containing element pthe set containing element q.
      *
      * @param  p one element
      * @param  q the other element
      */
     public void union(int p, int q) {
         int rootP = find(p);
         int rootQ = find(q);
         if (rootP == rootQ) return;
         parent[rootP] = rootQ; //make the root of p the child of the root of q
         count--; //decrease the number of connected component by 1 after the merge
     }
 
     /**
      * Print the current parent[] array.
      */
      public void printComponent() {
        for (int i = 0; i < parent.length; i++) {
            System.out.println("root: " + find(i) + " " + 
            "parent:" + parent[i] + " element: " + i);
        }
     }

     /**
      * Reads an integer n and a sequence of pairs of integers between 0  and n-1
      * from standard input, where each integer in the pair represents some element;
      * if the elements are in different sets, merge the two sets and print the pair 
      * to standard output.
      *
      * @param args the command-line arguments
      */
     public static void main(String[] args) {
         int n = StdIn.readInt();
         QuickUnionUF uf = new QuickUnionUF(n);
         while (!StdIn.isEmpty()) {
             int p = StdIn.readInt();
             int q = StdIn.readInt();
             if (uf.find(p) == uf.find(q)) continue;
             uf.union(p, q);
             StdOut.println(p + " " + q);
             uf.printComponent();
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