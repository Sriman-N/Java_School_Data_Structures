/**
 * Singly Linked List implementation of a queue that holds a list of string objects.
 * Queue is First In First Out (FIFO), add to end, remove from front.
 * Singly linked list is a sequence of objects, one followed by another. 
 * That is, an object in the list has exactly one predecessor and one successor
 * No need to determine the capacity at the time creating the queue.
 * Objects in the list are NOT in a consecutive memory addresses, thus need to keep track
 * of the objects with their addresses in memory. For this reason, need to define a Node class
 * to represent an object in the list, which not only contains the object but also holds
 * the address to the "next" node in the list.
 * @author Lily Chang
 */
public class QueueLinkedList {
    private Node first, last; //FIFO, need to know the last node to add, first node to remove
    private int  size; //number of objects in the list
    /**
     * An inner class to define a node in the linked list
     */
    private class Node {
        String item; //data
        Node   next;
    }
    
    /**
     * Enqueue/Add a node to the tail of list.
     * 1. create a new node
     * 2. assign item and next
     * 3. Update first and last
     * 4. specila case: add a new node to an empty queue
     * @param item
     */
    public void enqueue(String item) { //Need to update first and last pointers
        //adding the first node to the list, how do you update first and last?
        //adding a node to size of 1 or more
        Node oldlast = last; //keep the reference to the last node before add a new last
        Node node = new Node();
        node.item = item;
        node.next = null;
        last = node; //make the new node as the new last node
        if (isEmpty()) //if this is the only node then first is last
            first = last;
        else
            oldlast.next = last; //link the old last to the new last
        size++;
    }

    /**
     * Dequeue/Remove a node from the head of list.
     * Assume that before this method is performed, the client checked if the queue is empty
     * 1. Keep the item to be returned
     * 2. make the first pointing to the second node (first.next)
     * 3. special case: if the list is empty after the remove, make last = null
     * @return
     */
    public String dequeue() { //need to update first and last pointers
        //removing the only node in the list, how do you update first and last
        String s = first.item; //save the item to be returned
        first = first.next; //make the second node to be the new first
        size--;
        if (isEmpty()) { //if the queue is empty after the dequeue
            last = null;
        }
        return s;
    }

    /**
     * Check if the list is empty.
     * @return true if the list is empty; false otherwise;
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Get the size of the list
     * @return the number of items in the list.
     */
    public int size() {
        return size;
    }

    /**
     * Traverse the list starting from first.
     * @param args
     */
    public void print() {
        for (Node ptr = first; ptr != null; ptr = ptr.next) {
            System.out.println(ptr.item);
        }
    }

    /**
     * Test the Queue API
     */
    public static void main(String[] args) {
        QueueLinkedList q = new QueueLinkedList();
        q.enqueue("a");
        q.enqueue("b");
        q.dequeue();
        q.enqueue("c");
        q.enqueue("d");
        q.print(); //should print b c d
        //while (!q.isEmpty()) 
        //    System.out.println(q.dequeue());
    }
}
