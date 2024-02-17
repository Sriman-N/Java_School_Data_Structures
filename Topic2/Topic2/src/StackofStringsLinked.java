/**
 * Singly Linked List implementation of a stack that holds a stack of string objects.
 * Stack is Last In First Out (LIFO), add to top, remove from top.
 * Singly linked list is a sequence of objects, one followed by another. 
 * That is, an object in the list has exactly one predecessor and one successor
 * No need to determine the capacity at the time creating the stack.
 * Objects in the list are NOT in a consecutive memory addresses, thus need to keep track
 * of the objects with their addresses in memory.For this reason, need to define a Node class
 * to represent an object in the list, which not only contains the object but also holds
 * the address to the "next" node in the list.
 * @author Lily Chang
 */
public class StackofStringsLinked {
    //define a reference variable in Node type, holding the address of the first node in the list
    //declare a variable in integer type to keep track of the number of objects in the list
    private Node first; //default is null
    private int size; //default is 0


    //Node class - an inner class defining a "node" of a linked list
    private class Node {
        String item; //the data object
        Node   next; //the reference to the next Node in the list
    }

    //Constructor
    public StackofStringsLinked() {
        first = null;
        size = 0;
    }

    //implement the Stack API -- push(), pop(), isEmpty(), size()
    
    /**
     * Push/Add the new item to the head of the linked list.
     * The node at the head is the most recently added item.
     * @param item
     */
    public void push(String item) {
        Node node = new Node(); //create a new Node object
        node.item = item; //assign the data item
        node.next = first; //assign the next pointer (adding to the front of list)
        first = node; //make first points to the new node;
        size++;
    }
    
    /**
     * Remove a node from head of list (LIFO-> remove the most recently added item)
     * Assume that before this method is performed, the client checked if the stack is empty
     * @return
     */
    public String pop() {
        String s = first.item; //keep the string to be returned
        first = first.next; //then we can update the reference to first; old first will be recycled
        size--;
        return s;
    }

    public boolean isEmpty() {
        return size == 0;
        //OR, return first == null;
    }
    
    public int size() {
        return size;
    }
    
    /**
     * Traverse the singly linked list sequentially.
     */
    public void print() {
        Node x = first;
        while ( x != null) {
            System.out.println(x.item);
            x = x.next;
        }
    }
    /**
     * Write code to test the methods defined in this class.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        StackofStringsLinked stack = new StackofStringsLinked();
        stack.push("cs112");
        stack.push("data structures");
        stack.print();
        //System.out.println((stack.pop()));
        //System.out.println(stack.pop()); 
    }
}
