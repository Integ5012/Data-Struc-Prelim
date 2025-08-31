package prelim;

/**
 * Interface for a generic list implementation
 * @param <T> the type of elements in the list
 */
public interface MyList<T> {
    /**
     * Adds an item to the list
     * @param item the item to add
     */
    void add(T item);
    
    /**
     * Removes an item at the specified index
     * @param index the index of the item to remove
     */
    void remove(int index);
    
    /**
     * Gets an item at the specified index
     * @param index the index of the item to get
     * @return the item at the specified index
     */
    T get(int index);
    
    /**
     * Returns the size of the list
     * @return the number of elements in the list
     */
    int size();
}
