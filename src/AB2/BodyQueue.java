package AB2;

import java.util.Arrays;

/**
 * A queue of bodies. A collection designed for holding bodies prior to processing.
 * The bodies of the queue can be accessed in a FIFO (first-in-first-out) manner,
 * i.e., the body that was first inserted by 'add' is retrieved first by 'poll'.
 * The number of elements of the queue is not limited.
 */
public class BodyQueue {

    private Body[] queue;
    private int capacity;
    private int pointer;


    /**
     * Initializes this queue with an initial capacity.
     * @param initialCapacity the length of the internal array in the beginning,
     *                        initialCapacity > 0.
     */
    public BodyQueue(int initialCapacity) {

        queue = new Body[initialCapacity];
        capacity = initialCapacity;
        pointer = 0;

    }

    /**
     * Initializes this queue as an independent copy of the specified queue.
     * Calling methods of this queue will not affect the specified queue
     * and vice versa.
     * @param q the queue from which elements are copied to the new queue, q != null.
     */
    public BodyQueue(BodyQueue q) {
        int s = q.size();
        Body[] nq = new Body[s];

        for (int i = 0; i < s; i++) {
            nq[i] = q.queue[i];
        }
        queue = nq;
        capacity = s;
        pointer = s;
    }

    /**
     * Adds the specified body 'b' to this queue.
     * @param b the element that is added to the queue.
     */
    public void add(Body b) {

        if (pointer == capacity) {
            Body[] newQueue = new Body[capacity *2];

            for (int i = 0; i < pointer; i++) {
                newQueue[i] = queue[i];
            }
            capacity = capacity * 2;
            queue = newQueue;
        }

        queue[pointer++] = b;
    }

    /**
     * Retrieves and removes the head of this queue,
     * or returns 'null' if this queue is empty.
     * @return the head of this queue (or 'null' if this queue is empty).
     */
    public Body poll() {
        if (pointer == 0) {
            return null;
        }

        Body head = queue[0];
        Body[] nq = new Body[capacity];

        for (int i = 1; i < pointer; i++) {
            nq[i-1] = queue[i];
        }

        queue = nq;
        pointer--;
        return head;
    }

    /**
     * Returns the number of bodies in this queue.
     * @return the number of bodies in this queue.
     */
    public int size() {
        return pointer;
    }

    public boolean contains(Body find) {
        for (Body b: queue) {
            if (find.equals(b))
                return true;
        }
        return false;
    }

    public void remove(Body... bodiesToRemove){

        Body[] newQueue = new Body[capacity];
        int len = 0;
        for (int i = 0; i < pointer; i++) {

            boolean shouldRemove = false;
            for (Body b : bodiesToRemove) {
                if (queue[i].equals(b)) {
                    shouldRemove = true;
                    break;
                }
            }
            if (!shouldRemove) {
                newQueue[len] = queue[i];
                len++;
            }
        }
        queue = newQueue;
        pointer = len;
    }

    @Override
    public String toString() {
        return "BodyQueue{" +
                "queue=" + Arrays.toString(queue) +
                ", capacity=" + capacity +
                ", pointer=" + pointer +
                '}';
    }
}
