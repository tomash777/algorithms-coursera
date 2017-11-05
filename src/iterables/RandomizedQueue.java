package iterables;

import edu.princeton.cs.algs4.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;

    public RandomizedQueue()
    {
        items = (Item[]) new Object[1];
        size = 0;
    }

    public boolean isEmpty() { return size == 0; }
    public int size() { return size; }

    private void resize (int newCapacity)
    {
        Item[] copy = (Item[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) copy[i] = items[i];
        items = copy;
    }
    public void enqueue(Item item)
    {
        if(item == null) throw new NullPointerException();
        if(size == items.length) resize(2 * items.length);
        items[size++] = item;
    }
    public Item dequeue()
    {
        if(isEmpty()) throw new NoSuchElementException();
        int n = StdRandom.uniform(size);
        Item item = items[n];
        items[n] = items[--size];
        items[size] = null;
        if(size > 0 && size == items.length/4) resize(items.length/2);
        return item;
    }
    public Item sample()
    {
        if(isEmpty()) throw new NoSuchElementException();
        int n = StdRandom.uniform(size);
        return items[n];
    }

    public Iterator<Item> iterator() { return new RandomIterator();}
    // return an independent iterator over items in random order

    private class RandomIterator implements Iterator<Item>
    {
        Item[] copy;
        int copySize;

        public RandomIterator() {
            copy = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) copy[i] = items[i];
            copySize = size;
        }

        public void remove() { throw new UnsupportedOperationException();}
        public boolean hasNext() { return copySize != 0;}
        public Item next()
        {
            if (copySize == 0) throw new NoSuchElementException();
            else {
                int n = StdRandom.uniform(copySize);
                Item item = copy[n];
                copy[n] = copy[--copySize];
                copy[copySize] = null;
                return item;
            }
        }
    }

    public static void main(String[] args){}  // unit testing (optional)
}