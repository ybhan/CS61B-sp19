package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private Node root;

    private static class Node {
        Point point;
        Node leftChild;
        Node rightChild;

        Node(Point p) {
            point = p;
        }
    }

    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = add(p, root, 'x');
        }
    }

    @Override
    public Point nearest(double x, double y) {
        return nearest(root, new Point(x, y), root, 'x').point;
    }

    private Node nearest(Node node, Point goal, Node best, char axis) {
        if (node == null) {
            return best;
        }

        if (Point.distance(goal, node.point) < Point.distance(goal, best.point)) {
            best = node;
        }

        double axisDifference = axisDiff(goal, node.point, axis);

        Node goodSide;
        Node badSide;
        if (axisDifference <= 0) {
            goodSide = node.leftChild;
            badSide = node.rightChild;
        } else {
            goodSide = node.rightChild;
            badSide = node.leftChild;
        }

        best = nearest(goodSide, goal, best, switchAxis(axis));
        // Check to prune bad side or not.
        if (axisDifference * axisDifference < Point.distance(goal, best.point)) {
            best = nearest(badSide, goal, best, switchAxis(axis));
        }
        return best;
    }

    private Node add(Point p, Node node, char axis) {
        if (node == null) {
            return new Node(p);
        }
        if (axisDiff(p, node.point, axis) <= 0) {
            node.leftChild = add(p, node.leftChild, switchAxis(axis));
        } else {
            node.rightChild = add(p, node.rightChild, switchAxis(axis));
        }
        return node;
    }

    private double axisDiff(Point p1, Point p2, char axis) {
        if (axis == 'x') {
            return p1.getX() - p2.getX();
        } else {
            return p1.getY() - p2.getY();
        }
    }

    private char switchAxis(char axis) {
        if (axis == 'x') {
            return 'y';
        } else {
            return 'x';
        }
    }
}
