import static org.junit.jupiter.api.Assertions.*;

import ar.edu.itba.eda.Queue.QueueImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class QueueImpTest {

    private QueueImp<Integer> queue;

    @BeforeEach
    void setUp() {
        queue = new QueueImp<>(Integer.class, 5);
    }

    @Test
    void testQueueOperations() {
        queue.queue(10);
        queue.queue(20);
        queue.queue(30);

        assertEquals(3, queue.size());

        assertEquals(10, queue.dequeue());
        assertEquals(20, queue.dequeue());
        assertEquals(30, queue.dequeue());

        assertTrue(queue.isEmpty());
        assertThrows(RuntimeException.class, queue::dequeue);
    }

    @Test
    void testFullQueue() {
        for (int i = 0; i < 5; i++) {
            queue.queue(i);
        }

        assertEquals(5, queue.size());
        assertThrows(RuntimeException.class, () -> queue.queue(6));

        assertEquals(0, queue.dequeue());
        assertEquals(1, queue.dequeue());
        queue.queue(6);

        // 4 6 null null 3
        //   l           f
        assertEquals(4, queue.size());
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.dequeue());
        assertEquals(2,queue.size());
        assertEquals(4, queue.dequeue());
        assertEquals(6, queue.dequeue());
        assertEquals(0,queue.size());
    }



    @Test
    void testPeek() {
        queue.queue(10);
        assertEquals(10, queue.peek());
        assertEquals(1, queue.size());
    }
}