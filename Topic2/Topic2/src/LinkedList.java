/**
 * Demo how to link Node objects in a list.
 */
public class LinkedList {
    Node first; //contains the reference to the head of the list

    private class Node {
        private String item; //data in String type 
        private Node   next; //reference to the next String object in the list
        //2-parameter constructor
        private Node(String item, Node next) {
            this.item = item;
            this.next = next;
        }
        //default construcore (no-argument constructor)
        private Node() {}
    }

    /**
     * Add, remove and traverse.
     */
    public void addNodes() {
        //adding the first node of the list; make "first" pointing to the head.
        Node node = new Node(); //first node in the list
        node.item = "Carol";
        node.next = null;
        first = node;

        //adding the second node to the list (add to the head)
        node = new Node(); //create a new Node object
        node.item = "Bob"; //assign the string
        node.next = first; //update the next pointer pointing to the "first"; new node becomes the new "first"
        first = node;      //make first pointing to the new node;  

        //adding the third node to the list (add to the head)
        node = new Node();
        node.item = "Alice";
        node.next = first;
        first = node;

        //adding the fourth node to the list (add to the head)
        node = new Node();
        node.item = "David";
        node.next = first;
        first = node;
    }

    /**
     * Add to front of list. See the pattern in addNodes() above.
     */
    public void add2Front(String item) {
        Node node = new Node();
        node.item = item;
        node.next = first;
        first = node;
    }

    public void removeFirst() {
        //removing a node from the head of the list
        String item = first.item; //keep the data so we don't lose the data.
        first = first.next;
        System.out.println(item + " is removed from list");
    }

    /**
     * Traverse the list sequentially and print the data items.
     */
    public void print() {
        System.out.print("\n");
        for (Node x = first; x!= null; x = x.next) { //follow the next pointer
            System.out.print(x.item);
            if (x.next != null)
                System.out.print(" --> ");
        }
        System.out.print("\n");
    }

    /**
     * Test the code in this class.
     * @param args
     */
    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        list.addNodes();
        list.print();
        list.removeFirst();
        list.print();
    }
}