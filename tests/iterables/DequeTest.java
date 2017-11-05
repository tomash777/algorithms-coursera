package iterables;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tomasz on 02.11.17.
 */
public class DequeTest {

    private Deque<Integer> deque = new Deque<>();

    @Test (expected = NullPointerException.class)
    public void addNull() throws Exception {
        deque.addFirst(null);
    }

    @Test
    public void name() throws Exception {
        deque.addFirst(4);
        deque.addFirst(3);
        deque.removeLast();
        int i = deque.removeLast();
        assertEquals(i, 3);
    }


}