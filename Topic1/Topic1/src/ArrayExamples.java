import edu.princeton.cs.algs4.StdOut;

public class ArrayExamples {
    public static void main(String[] args) {
        StdOut.println("Demo arrays with different data types.");
        //declare a 1D array with int, double and String types (array capacity 5)
        int a[] = new int[5];
        double b[] = new double[5]; //int and double are primitive data types
        String strings[]  = new String[5]; //String is a reference type (holding a memory addr.)

        //store a string at index 0, which contains the referece to the string object "Data Striuctures"
        strings[0] = "Data Structures"; 
        for (int i  = 0; i <  strings.length; i++)
            System.out.println(strings[i]); //default value for a reference type is null
         
        for (int i  = 0; i <  b.length; i++)
            System.out.println(b[i]); //default value for numeric types is 0

        //declare a 2D array with int and String types (3 rows and 5 columns)
        String c[][] = new String[3][5];
        int d[][] = new int[3][5];
        c[2][3] = "CS 112"; //at row index 2 column index 3, it holds the reference to "CS 112"
        /**
        declare a 2D array with no capacity but with a set of inital values
        below will give you a 4 X 2 table, i.e., 4 rows 2 column
        */
        int arr[][] = { {1, 2}, {3, 4}, {5, 6}, {7, 8} };

        /**
        sequentially access a 1D array
        use a counter i to control the iteration, i will be running from 0 to a.length - 1
        int i = 0; will only be executed once
        i < a.length; will be executed before each iteration
        i++ will be executed after each iteration
        System.out.println(); will be executed a.length time 
        */
        for (int i = 0; i < a.length; i++) 
            System.out.println(a[i]);

        /**
        sequentially access a 2D array, row by row (row-wise)
        a 2D array is a consecutive memory block in the heap memory
        arr contains the address to the beginning of the block
        arr.length is the number of rows
        arr[row].length is the number of columns, Java JVM uses the number of columns to figure out
        the offset to the next row.
        */
        for (int row = 0; row < arr.length; row++) //row index increases by 1 after we're done with one row
            for (int col = 0; col < arr[row].length; col++) //for the same row sequentially access each column
                System.out.println(arr[row][col]); 

        //reading or writing values from/to a cell in a 2D array
        a[0] = 5; //write 5 to index 0; 1 array access
        a[4] = a[0]; //read value from index 0 and write the value to index 4; 2 array accesses
        d[2][3] = 10; //write 10 to row index 2 column index 3; 1 array access
        //read value from row index 2 column index 3 and write to row index 0 column index 0; 2 array accesses
        d[0][0] = d[2][3];

        //sequentially access the array column-wise
        for (int col = 0; col < arr[0].length; col++) { //for each column index
            for (int row = 0; row < arr.length; row++) { //access all the row indexes from 0 to arr.length - 1
                System.out.println(arr[row][col]);
            }
        }
    }
}
