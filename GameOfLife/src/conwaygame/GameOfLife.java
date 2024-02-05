package conwaygame;
import java.util.ArrayList;
import java.util.HashSet;
/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid row, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */
    public GameOfLife (String file) {
        StdIn.setFile(file);
        int r = StdIn.readInt();  //number of rows
        int c = StdIn.readInt();  // number of columns
        grid = new boolean[r][c]; //making a grid using r and c

        for(int i = 0; i < r; i++) {
            for(int j = 0; j < c; j++) {
                grid[i][j] = StdIn.readBoolean();
            }
        }
    }

    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col) {

        return grid[row][col];
    }

    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive () {

        int trueCounter = 0;

        //This for loop checks if each cell is alive or not alive, if alive, then counter goes up
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length;  j++) {
                if (grid[i][j] == ALIVE) {
                    trueCounter++;
                }
            }
        }

        //if counter is greater or equal to 1 then the grid is alive
        if (trueCounter >= 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are 
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col) {

        int neighboringCells = 0;
        int r = grid.length;
        int c = grid[0].length;

        //checks the neighbors or its proximity
        int[][] proximity = {
            {row -1, col - 1}, {row -1, col}, {row -1, col +1},
            {row, col -1},                    {row, col + 1}, 
            {row + 1, col -1}, {row +1, col}, {row +1, col+1}
        };
        for(int[] p : proximity) {
            int pr = (p[0] + r) % r;
            int pc = (p[1] + c) % c;

            if(grid[pr][pc]) {
                neighboringCells++;
            }
        }
        return neighboringCells;
    }

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid () {

        int row = grid.length;
        int col = grid[0].length;

        boolean[][] newGrid = new boolean[row][col];


        //the rules are online
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == ALIVE) {
                    if(numOfAliveNeighbors(i, j) <= 1) {
                        newGrid[i][j] = DEAD;
                    } else if(numOfAliveNeighbors(i, j) == 2 || numOfAliveNeighbors(i, j) == 3) {
                        newGrid[i][j] = ALIVE;
                    }else if(numOfAliveNeighbors(i, j) >= 4) {
                        newGrid[i][j] = DEAD;
                    }
                } else if(grid[i][j] == DEAD) {
                    if(numOfAliveNeighbors(i, j) == 3) {
                        newGrid[i][j] = ALIVE;
                    }
                }
            }
        }

        return newGrid;// update this line, provided so that code compiles
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () {
        grid = computeNewGrid();
        int count = 0;
        
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j]) {
                    count++;
                }
            }
        }
        totalAliveCells = count;
    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n) {

        for(int i = 0; i < n; i++) {
            grid = computeNewGrid();
        }
    }

    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities() {

        WeightedQuickUnionUF union = new WeightedQuickUnionUF(grid.length, grid[0].length);
        ArrayList<Integer> find = new ArrayList<>();
        

        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == ALIVE) {
                    int r = grid.length;
                    int c = grid[0].length;
            
                    int[][] proximity = {
                        {i - 1, j - 1}, {i - 1, j}, {i - 1, j +1},
                        {i, j - 1},                 {i, j + 1}, 
                        {i + 1, j - 1}, {i + 1, j}, {i + 1, j + 1}
                    };
                    for(int[] p : proximity) {
                        int pr = (p[0] + r) % r;
                        int pc = (p[1] + c) % c;
            
                        if(grid[pr][pc]) {
                            union.union(i, j, pr, pc);
                        }
                    }
                }
            }
        }

        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == ALIVE) {
                    find.add(union.find(i,j));
                }
            }
        }        
        
        if(find.size() == 0) {
            return 0;
        }

        HashSet<Integer> h = new HashSet<>(find);

        // a.add(find.get(0));

        // for(int num : find) {
        //     for(int i = 0; i < a.size(); i++) {
        //         if(num != a.get(i)) {
        //             a.add(num);
        //         }
        //     }
        // }
        return h.size();
    }
}