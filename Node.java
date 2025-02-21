public class Node {
    private int data;
    private Node left;
    private Node right;

    // Constructor
    public Node(int data) {
        this.data = data;
        this.right = null;
        this.left = null;
    }

    // Recursively find a node by a given value
    public Node find(int value) {
        if (value == this.data) {
            return this;
        } else if (value > this.data) {
            return this.right.find(value);
        } else if (value < this.data) {
            return this.left.find(value);
        } else {
            return null;
        }
    }

    // Recursively searches for the minimum value node of the bigger side of a node
    // This is used when removing a node that has a child that needs to be swapped
    // as a new parent to maintain a balanced graph
    public static Node findMin(Node parent) {
        if (null != parent.getLeft()) {
            return parent.getLeft();
        } else {
            return parent;
        }
    }

    public boolean isLeaf() {
        return (null == this.left && null == this.right);
    }

    // Recursively remove a node from the tree
    public static Node remove(Node node, int value) {
        // If node is null, nothing to do on it, return as is.
        if (null == node) {
            return node;
        }

        // We are looking for our target node to delete.
        // At this point, the object that called this instance
        // of the funcition is the parent node.
        // What we return is waht will be referenced as a
        // right or left child node.

        // Target node is left or right?
        if (value < node.data) {
            node.setLeft(remove(node.getLeft(), value));
        } else if (value > node.data) {
            node.setRight(remove(node.getRight(), value));
        }
        // We found our target node to delete
        else {
            // Case 1: Node is a leaf (no children)
            if (node.isLeaf()) {
                return null;
            }

            // Case 2: Node has 1 child
            if (null == node.getLeft()) {
                return node.getRight();
            } else if (null == node.getRight()) {
                return node.getLeft();
            }

            // Case 3: Node has 2 children
            // Find smallest leaf from the bigger branch,
            // use it to replace this current node that needs ot be deleted
            // and then delete smallest leaf that we just found
            Node successor = findMin(node.right);
            node.data = successor.data;

            node.setRight(remove(node.getRight(), successor.data));
        }

        return node;
    }

    // Recursively insert a node into the tree
    public static Node insertNode(Node node, int value) {
        // If node is free, add new one
        if (null == node) {
            return new Node(value);
        }

        if (value > node.getValue()) {
            node.setRight(insertNode(node.getRight(), value));
        } else if (value < node.getValue()) {
            node.setLeft(insertNode(node.getLeft(), value));
        }

        return node;
    }

    public void printNode() {
        System.out.print(this.toString());
    }

    // Display the node value
    public String toString() {
        return "[Data: " + this.data + "]";
    }

    // Tree traversal methods
    public void printNodeInOrder() {
        if (null != this.left)
            this.left.printNodeInOrder();
        this.printNode();
        if (null != this.right)
            this.right.printNodeInOrder();
    }

    public void printNodePreOrder() {
        this.printNode();
        if (null != this.left)
            this.left.printNodePreOrder();
        if (null != this.right)
            this.right.printNodePreOrder();
    }

    public void printNodePostOrder() {
        if (null != this.left)
            this.left.printNodePostOrder();
        if (null != this.right)
            this.right.printNodePostOrder();
        this.printNode();
    }
    
    // Getters and Setters
    public void setLeft(Node node) {
        this.left = node;
    }

    public Node getLeft() {
        return this.left;
    }

    public void setRight(Node node) {
        this.right = node;
    }

    public Node getRight() {
        return this.right;
    }

    public int getValue() {
        return this.data;
    }
}