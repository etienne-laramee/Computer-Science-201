import java.util.Arrays;

/**
 * BinaryTree
 */
class BinaryTree {
    private Node root = null;

    // Default constructor
    public BinaryTree() {
    }

    /**
     * Binary tree constructor. Takes in an array of integers.
     * @param data
     */
    public BinaryTree(int[] data) {
        // Find the middle value to be used as the root and put it first.
        Arrays.sort(data);
        int middleIndex = data.length/2;
        int tmp = data[0];
        data[0] = data[middleIndex];
        data[middleIndex] = tmp;

        // For each data element, create a new node, then insert it into the tree.
        for (int datum : data) {
            this.AddNode(datum);
        }
    }

    /**
     * AddNode: Adds a node to the tree from a given value
     * 
     * @param datum
     */
    public void AddNode(int datum) {
        Node node = new Node(datum);

        // If root is null, create new node and assign it as root.
        if (null == this.root) {
            this.root = node;
        }
        // Otherwise, add it to the existing tree, starting from root.
        else {
            try {
                this.root.addNode(node);
            } catch (AlreadyExistsException e) {
                System.out.println("Value " + e.getValue() + " already exists. Node not added.");
            }
        }
    }

    public void printInOrder() {
        this.root.printNodeInOrder();
    }

    /**
     * Node: Part of a tree. Can have up to 2 child nodes.
     */
    private class Node {
        private int data;
        private Node left;
        private Node right;

        // Constructor
        public Node(int data) {
            this.data = data;
            this.right = null;
            this.left = null;
        }

        /**
         * addNode: Adds a new node to the tree
         * @param node
         * @throws AlreadyExistsException
         */
        public void addNode(Node node) throws AlreadyExistsException {
            if (node.data < this.data) {
                if (null != this.left) {
                    this.left.addNode(node);
                } else {
                    this.left = node;
                }
            } else if (node.data > this.data) {
                if (null != this.right) {
                    this.right.addNode(node);
                } else {
                    this.right = node;
                }
            } else {
                throw new AlreadyExistsException(node.data);
            }
        }

        /**
         * printNode: Print the node's value
         */
        public void printNode() {
            System.out.print("[Data: " + this.data + "]");
        }

        /**
         * printNodeInOrder: Print the node's own and child values in order.
         */
        public void printNodeInOrder() {
            if (null != this.left) this.left.printNodeInOrder();
            this.printNode();
            if (null != this.right) this.right.printNodeInOrder();

        }
    }

    /**
     * Main function
     */
    public static void main(String[] args) {
        int[] defaultData = { 1, 2, 3, 4, 5, 6, 7 };

        // Menu loop

        // Test
        BinaryTree binaryTree = new BinaryTree(defaultData);
        binaryTree.printInOrder();
    }
}

/**
 * AlreadyExistsException: Custom exception for when a node value already exists in the tree.
 */
class AlreadyExistsException extends Exception {
    private int value;
    // Used for notifying when a new node already exists.
    public AlreadyExistsException(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
