import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;
    private int size;

    private class Node {
        K key;
        V value;
        Node left;
        Node right;

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

    private V get(K k, Node node) {
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
        root = put(key, value, root);
        size += 1;
    }

    private Node put(K k, V v, Node node) {
        if (node == null) {
            return new Node(k, v);
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

    private void printInOrder(Node node) {
        if (node == null) {
            return;
        }

        String prefix = ", ";
        String suffix = ", ";
        if (node.left == null) {
            prefix = "";
        }
        if (node.right == null) {
            suffix = "";
        }

        printInOrder(node.left);
        System.out.print(prefix + node.key + suffix);
        printInOrder(node.right);
    }

    @Override
    public Set<K> keySet() {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        // TODO
        throw new UnsupportedOperationException();
    }
}
