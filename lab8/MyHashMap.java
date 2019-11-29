import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    private int initialCapacity;
    private double loadFactor;
    private int threshold;
    private int size = 0;
    private BucketEntity<K, V>[] hashTable;

    private static class BucketEntity<K, V> {
        private K key;
        private V value;
        private BucketEntity<K, V> next;

        private BucketEntity(K k, V v) {
            this(k, v, null);
        }

        private BucketEntity(K k, V v, BucketEntity<K, V> n) {
            key = k;
            value = v;
            next = n;
        }

        private BucketEntity<K, V> find(K key) {
            return find(key, this);
        }

        private BucketEntity<K, V> find(K key, BucketEntity<K, V> be) {
            if (be == null) {
                return null;
            }
            if (be.key.equals(key)) {
                return be;
            }
            return find(key, be.next);
        }

        /** If key is newly added, return true, else (an update of key's value) return false. */
        private boolean put(K key, V value) {
            return put(key, value, this);
        }

        private boolean put(K key, V value, BucketEntity<K, V> be) {
            if (be.key.equals(key)) {
                be.value = value;
                return false;
            }
            if (be.next == null) {
                be.next = new BucketEntity<>(key, value);
                return true;
            }
            return put(key, value, be.next);
        }

        private void add(BucketEntity<K, V> beToAdd) {
            BucketEntity<K, V> be = this;
            while (be.next != null) {
                be = be.next;
            }
            be.next = beToAdd;
        }
    }

    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity, double loadFactor) {
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
        this.threshold = (int) (initialCapacity * loadFactor);
        hashTable = new BucketEntity[initialCapacity];
    }

    @Override
    public void clear() {
        hashTable = new BucketEntity[hashTable.length];
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int keyHash = hash(key);
        if (hashTable[keyHash] == null) {
            return false;
        }
        return hashTable[keyHash].find(key) != null;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int keyHash = hash(key);
        if (hashTable[keyHash] == null) {
            return null;
        }
        BucketEntity<K, V> be = hashTable[keyHash].find(key);
        return be == null ? null : be.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        if (value == null) {
            remove(key);
        }

        if (size + 1 > threshold) {
            resize();
        }

        int keyHash = hash(key);

        if (hashTable[keyHash] == null) {
            hashTable[keyHash] = new BucketEntity<>(key, value);
            size += 1;
        } else {
            boolean keyIsNew = hashTable[keyHash].put(key, value);
            if (keyIsNew) {
                size += 1;
            }
        }
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> keySet = new HashSet<>();
        for (BucketEntity<K, V> bucket : hashTable) {
            while (bucket != null) {
                keySet.add(bucket.key);
                bucket = bucket.next;
            }
        }
        return keySet;
    }

    @Override
    public V remove(K key) {
        int keyHash = hash(key);
        V valueToRemove = get(key);
        if (valueToRemove == null) {
            return null;
        }
        hashTable[keyHash] = remove(key, hashTable[keyHash]);
        return valueToRemove;
    }

    @Override
    public V remove(K key, V value) {
        int keyHash = hash(key);
        V valueToRemove = get(key);
        if (valueToRemove != value) {
            throw new IllegalArgumentException("key and value don't match.");
        }
        hashTable[keyHash] = remove(key, hashTable[keyHash]);
        return valueToRemove;
    }

    /** Return the modified BucketEntity after removing key from it. */
    private BucketEntity<K, V> remove(K key, BucketEntity<K, V> be) {
        if (be == null) {
            return null;
        }
        if (be.key.equals(key)) {
            return be.next;
        }
        be.next = remove(key, be.next);
        return be;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    private int hash(K key) {
        return hash(key, hashTable.length);
    }

    private int hash(K key, int capacity) {
        return (key.hashCode() & 0x7FFFFFFF) % capacity;
    }

    private void resize() {
        BucketEntity<K, V>[] newHashTable = new BucketEntity[initialCapacity * 2];
        threshold = (int) (newHashTable.length * loadFactor);

        for (BucketEntity<K, V> bucket : hashTable) {
            if (bucket == null) {
                continue;
            }

            int newHash = hash(bucket.key, newHashTable.length);

            if (newHashTable[newHash] == null) {
                newHashTable[newHash] = bucket;
            } else {
                newHashTable[newHash].add(bucket);
            }
        }
    }
}
