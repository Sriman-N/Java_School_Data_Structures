/**
 * Array implementation of a stack that holds a stack of string objects.
 * Stack is Last In First Out (LIFO), add to top, remove from top.
 * Must provide the capacity at the time creating the stack.
 * Objects are stored in a block of consecutive memory addresses.
 * Potentially underflow (capacity too large) and overflow (capacity not enough)
 * 
 * @author Lily Chang
 */
public class StackofStringsArray {
    //declare a 1D array to hold the string objects.
    //declare a variable in integer type to keep track of the number of objects in the stack
    private String strings[];
    private int size; //n elements


    //constructor to create an object of this class
    public StackofStringsArray(int capacity) {
        strings = new String[capacity];
        size = 0;
    }

    //implement the Stack API -- push(), pop(), isEmpty(), size()
    /**
     * Push/Add a string to the end of array. size is the number of items in the stack so far,
     * so using size as the index to store the new item.
     * @param s the itme to be added to the stack.
     */
    public void push(String s) {
        strings[size] = s;
        size++;
    }
    /**
     * Pop/Remove a string from the array. size is the index for the new item, meaning the last
     * item added is in size - 1.
     * @return
     */
    public String pop() {
        String string = strings[size - 1];
        size--; 
        strings[size] = null; //so Java can recyle the memory  
        return string;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    /**
     * Write code to test the Stack API.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        StackofStringsArray stack = new StackofStringsArray(10);
        stack.push("cs112");
        stack.push("data structures");
        System.out.println((stack.pop()));
        System.out.println(stack.pop());
        //while (!stack.isEmpty())
        //    System.out.println(stack.pop());
    }
}
