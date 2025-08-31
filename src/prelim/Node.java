package prelim;

/**
 * Node class for the linked list implementation
 * @param <T> the type of data stored in the node
 */
public class Node<T> {
    private T data;
    private Node<T> next;
    
    /**
     * Constructor for Node
     * @param data the data to store in the node
     */
    public Node(T data) {
        this.data = data;
        this.next = null;
    }
    
    /**
     * Gets the data stored in the node
     * @return the data
     */
    public T getData() {
        return data;
    }
    
    /**
     * Sets the data stored in the node
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }
    
    /**
     * Gets the next node
     * @return the next node
     */
    public Node<T> getNext() {
        return next;
    }
    
    /**
     * Sets the next node
     * @param next the next node to set
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }
}

