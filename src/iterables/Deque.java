package iterables;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int N = 0;
    private class Node
    {
        Item item;
        Node next;
        Node before;
    }
    public Deque()
    {
        first = null;
        last = null;
    }
    public boolean isEmpty()
    {
        return first == null;
    }
    public int size() { return N;}
    public void addFirst(Item item)
    {
        if(item == null) throw new NullPointerException();
        else N++;
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        first.before = null;
        if(last == null) last = first;
        else oldfirst.before = first;
    }
    public void addLast(Item item)
    {
        if(item == null) throw new NullPointerException();
        else N++;
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.before = oldlast;
        if(isEmpty()) first = last;
        else oldlast.next = last;
    }
    public Item removeFirst()
    {
        if(isEmpty()) throw new NoSuchElementException();
        else N--;
        Item item = first.item;
        first = first.next;
        if(isEmpty()) last = null;
        else first.before = null;
        return item;
    }
    public Item removeLast()
    {
        if(isEmpty()) throw new NoSuchElementException();
        else N--;
        Item item = last.item;
        last = last.before;
        if(last == null) first = last;
        else last.next = null;
        return item;
    }
    public Iterator<Item> iterator() { return new DequeIterator(); }
    // return an iterator over items in order from front to end

    private class DequeIterator implements Iterator<Item>
    {
        private Node current = first;
        public boolean hasNext() { return current != null;}
        public void remove() { throw new UnsupportedOperationException();}
        public Item next()
        {
            if(!hasNext()) throw new NoSuchElementException();
            else{
                Item item = current.item;
                current = current.next;
                return item;
            }
        }
    }
    public static void main(String[] args){} // unit testing (optional)
}
