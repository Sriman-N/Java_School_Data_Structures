 import edu.princeton.cs.algs4.StdIn;
 import edu.princeton.cs.algs4.StdOut;

 /**
  *  The WeightedQuickUnionUF class represents a union–find data type.
  *  This implementation uses weighted quick union by size.
  *
  *  Run without debugging --> copy the data in tinyUF.txt and paste them on the
  *                            terminal. CTRL + D to stop the loop.
  *                            (CTRL + SHIFT + D for MacOS)
  *  @author Robert Sedgewick
  *  @author Kevin Wayne
  *  @author Lily Chang, modified on Jan 14, 2024
  */
 public class WeightedQuickUnionUF {
     private int[] parent;   // parent[i] = parent of i
     private int[] size;     // size[i] = number of elements in subtree rooted at i
     private int count;      // number of connected components
 
     /**
      * Initializes an empty union-find data structure with n elements.  
      * Initially, each element is in its own set.
      *
      * @param  n the number of elements
      */
     public WeightedQuickUnionUF(int n) {
         count = n; //n connected components initially
         parent = new int[n]; 
         size = new int[n]; //use the index to retrieve the size of a subtree
         for (int i = 0; i < n; i++) {
             parent[i] = i;
             size[i] = 1;
         }
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
      * Returns the root node of the tree containing element p.
      *
      * @param  p an element
      * @return the root node of the tree containing p
      */
     public int find(int p) {
         while (p != parent[p])
             p = parent[p];
         return p;
     }
 
     /**
      * Returns true if the two elements are in the same set (with same root node).
      *
      * @param  p one element
      * @param  q the other element
      * @return true if p and q are in the same set; false otherwise
      */
     public boolean connected(int p, int q) {
         return find(p) == find(q);
     }
 
     /**
      * Merges the set containing element p with the set containing element q.
      *
      * @param  p one element
      * @param  q the other element
      */
     public void union(int p, int q) {
         int rootP = find(p);
         int rootQ = find(q);
         if (rootP == rootQ) return;
 
         // make smaller root point to larger one
         if (size[rootP] < size[rootQ]) { //the size at rootP is strictly smaller than the size at rootQ
             parent[rootP] = rootQ; //make the root of p as child of root of q
             size[rootQ] += size[rootP]; //2 tress become 1 tree, need to combine the sizes
         }
         else { //the size at rootP is >= the size at rootQ
             parent[rootQ] = rootP; //make the root of q as child of root of p
             size[rootP] += size[rootQ]; //update the size
         }
         count--;
     }
 
    /**
    * Print the current parent[] array.
    */
    public void printComponent() {
        for (int i = 0; i < parent.length; i++) {
            System.out.println("root: " + find(i) + " " + "size: " + size[find(i)]
                + " parent:" + parent[i] + " element: " + i);
        }
    }

     /**
      * Reads an integer n and a sequence of pairs of integers between 0 and n-1
      * from standard input, where each integer in the pair represents some element;
      * if the elements are in different sets, merge the two sets and print the pair 
      * to standard output.
      *
      * @param args the command-line arguments
      */
     public static void main(String[] args) {
         int n = StdIn.readInt();
         WeightedQuickUnionUF uf = new WeightedQuickUnionUF(n);
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
