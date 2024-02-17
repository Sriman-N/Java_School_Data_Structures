import java.io.IOException;

/** Demonstrates the example on the lecture slide
    1. Add a breakpoint on line #19 so the program execution stops before executing #19
    2. Click Run/Start Debugging to trace the values of the loop counters i and j.
        -The program execution will stop at the breakpoint (line #19), 
        -Click "Step Over (F10)" on the tool bar or Run/Step Over to trace the counter values. 
    3. Click Run/Start Without Debugging if you don't want to trace the counter values.
    4. You can change the value of n.
    5. The variable "count" keeps track of the # of iterations, i.e., # of times
        System.out statement is executed.
*/
public class DoubleLoops {
    private static final int n = 10;
    public static void main(String[] args) throws IOException{
        int count = 0; 
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++) {
                System.out.println(++count); //count the number of iterations  
            }
    }
}
