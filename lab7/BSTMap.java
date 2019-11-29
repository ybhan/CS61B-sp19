import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node<K, V> root;
    private int size;

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        return get(key, root);
    }

    private V get(K k, Node<K, V> node) {
        if (node == null) {
            return null;
        }
        if (k.compareTo(node.key) == 0) {
            return node.value;
        } else if (k.compareTo(node.key) < 0) {
            return get(k, node.left);
        } else {
            return get(k, node.right);
        }
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
        root = put(key, value, root);
    }

    private Node<K, V> put(K k, V v, Node<K, V> node) {
        if (node == null) {
            size += 1;
            return new Node<>(k, v);
        }
        if (k.compareTo(node.key) == 0) {
            node.value = v;
        } else if (k.compareTo(node.key) < 0) {
            node.left = put(k, v, node.left);
        } else {
            node.right = put(k, v, node.right);
        }
        return node;
    }

    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(Node<K, V> node) {
        if (node == null) {
            return;
        }

        String prefix = node.left == null ? "" : ", ";
        String suffix = node.right == null ? "" : ", ";

        printInOrder(node.left);
        System.out.print(prefix + node.key + suffix);
        printInOrder(node.right);
    }

    @Override
    public Set<K> keySet() {
        return keySet(root, new HashSet<>());
    }

    /** Recursively add all keys in subtree Node to keySet. */
    private Set<K> keySet(Node<K, V> node, Set<K> keySet) {
        if (node == null) {
            return keySet;
        }
        keySet = keySet(node.left, keySet);
        keySet.add(node.key);
        keySet = keySet(node.right, keySet);
        return keySet;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        V valueToRemove = get(key);
        if (valueToRemove == null) {
            return null;
        }
        root = remove(key, root);
        size -= 1;
        return valueToRemove;
    }

    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        V valueToRemove = get(key);
        if (valueToRemove != value) {
            throw new IllegalArgumentException("key and value don't match.");
        }
        root = remove(key, root);
        size -= 1;
        return valueToRemove;
    }

    /** Return the modified node after removing key from it. */
    private Node<K, V> remove(K key, Node<K, V> node) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            node.left = remove(key, node.left);
        } else if (cmp > 0) {
            node.right = remove(key, node.right);
        } else {
            /* node has 0 or 1 child. */
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            /* node with 2 children. */
            Node<K, V> successor = removeSuccessor(node);
            node.key = successor.key;
            node.value = successor.value;
        }
        return node;
    }

    /** Remove and return the successor of a two-child node. */
    private Node<K, V> removeSuccessor(Node<K, V> node) {
        Node<K, V> successorParent = node;
        Node<K, V> successor = node.right;

        if (successor.left == null) {
            successorParent.right = successor.right;
            return successor;
        }

        while (successor.left != null) {
            successorParent = successor;
            successor = successor.left;
        }

        successorParent.left = successor.right;
        return successor;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
