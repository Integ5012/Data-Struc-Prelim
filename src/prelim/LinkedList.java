package prelim;

/**
 * LinkedList implementation of the MyList interface
 * @param <T> the type of elements in the list
 */
public class LinkedList<T> implements MyList<T> {
    private Node<T> head;
    private int size;
    
    /**
     * Constructor for LinkedList
     */
    public LinkedList() {
        this.head = null;
        this.size = 0;
    }
    
    /**
     * Adds an item to the end of the list
     * @param item the item to add
     */
    @Override
    public void add(T item) {
        Node<T> newNode = new Node<>(item);
        
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
        size++;
    }
    
    /**
     * Removes an item at the specified index
     * @param index the index of the item to remove
     */
    @Override
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        if (index == 0) {
            head = head.getNext();
        } else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            current.setNext(current.getNext().getNext());
        }
        size--;
    }
    
    /**
     * Gets an item at the specified index
     * @param index the index of the item to get
     * @return the item at the specified index
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getData();
    }
    
    /**
     * Returns the size of the list
     * @return the number of elements in the list
     */
    @Override
    public int size() {
        return size;
    }
    
    /**
     * Checks if the list is empty
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }
}