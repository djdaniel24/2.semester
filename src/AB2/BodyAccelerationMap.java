package AB2;

import AB1.Vector3;

import java.util.Objects;

/**
 * A map that associates a body with an acceleration vector. The number of
 * key-value pairs is not limited.
 */
public class BodyAccelerationMap {

    private Body[] keys;
    private Vector3[] values;
    private int pointer;

    /**
     * Initializes this map with an initial capacity.
     *
     * @param initialCapacity specifies the size of the internal array. initialCapacity > 0.
     */
    public BodyAccelerationMap(int initialCapacity) {

        keys = new Body[initialCapacity];
        values = new Vector3[initialCapacity];
        pointer = 0;
    }

    /**
     * Adds a new key-value association to this map. If the key already exists in this map,
     * the value is replaced and the old value is returned. Otherwise, 'null' is returned.
     *
     * @param key          a body != null.
     * @param acceleration the acceleration vector to be associated with the key.
     * @return the old value if the key already existed in this map, or 'null' otherwise.
     */
    public Vector3 put(Body key, Vector3 acceleration) {
        int found = find(key);

        // replace existing
        if (found != pointer) {
            Vector3 oldValue = values[found];
            values[found] = acceleration;
            return oldValue;
        }

        // if capacity is reached
        if (pointer == keys.length) {
            extend(keys.length * 2);
        }

        // move all items till the right place is found
        int i;
        for (i = pointer; i > 0; i--) {
            if (keys[i - 1].getMass() < key.getMass()) {
                keys[i] = keys[i - 1];
                values[i] = values[i - 1];
            } else
                break;
        }
        keys[i] = key;
        values[i] = acceleration;
        pointer++;

        return null;
    }

    private void extend(int newCapacity) {
        Body[] newKeys = new Body[newCapacity];
        Vector3[] newValues = new Vector3[newCapacity];
        for (int i = 0; i < pointer; i++) {
            newKeys[i] = keys[i];
            newValues[i] = values[i];
        }
        keys = newKeys;
        values = newValues;
    }

    private int find(Body key) {
        for (int i = 0; i < pointer; i++) {
            if (keys[i] == key) {
                return i;
            }
        }
        return pointer;
    }


    /**
     * Returns the value associated with the specified key, i.e. the acceleration vector
     * associated with the specified body. Returns 'null' if the key is not contained in this map.
     *
     * @param key a body != null.
     * @return the value associated with the specified key (or 'null' if the key is not contained in
     * this map).
     */
    public Vector3 get(Body key) {
        int left = 0;
        int right = pointer - 1;

        while (left <= right) {
            int middle = left + ((right - left) / 2);
            if (keys[middle].getMass() < key.getMass()) { // || key.equals(keys[middle])
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }

        // Check if the id is right
        if (key.equals(keys[right])) {
            return values[right];
        }
        else if (keys[right].getMass() == key.getMass()) {
            System.out.println("key could not be found with binary search because it had the same mass as a different key");
            return values[find(key)];
        }
        else {
            return null;
        }
    }
}
