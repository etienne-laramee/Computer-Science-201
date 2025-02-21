import java.util.Arrays;
import java.util.Scanner;

class BinaryTree {
    private Node root = null;

    // Constructor
    public BinaryTree(int[] data) {
        Arrays.sort(data);
        this.root = buildTree(data, 0, data.length - 1);
    }

    // Recursively builds a balanced tree from an ordered data set
    private Node buildTree(int[] dataset, int start, int end) {
        // Checks for a negative range, meaning we reached leaf
        if (start > end) {
            return null;
        }

        // Find the middle of the data set to use as a parent
        int middleIndex = (start + end) / 2;
        Node node = new Node(dataset[middleIndex]);

        // Recursively build the children
        node.setLeft(buildTree(dataset, start, middleIndex - 1));
        node.setRight(buildTree(dataset, middleIndex + 1, end));

        return node;
    }

    public Node find(int value) {
        return this.root.find(value);
    }

    public void remove(int value) {
        this.root = Node.remove(this.root, value);
    }

    public void add(int value) {
        this.root = Node.insertNode(this.root, value);
    }

    public void printInOrder() {
        this.root.printNodeInOrder();
    }

    public void printPreOrder() {
        this.root.printNodePreOrder();
    }

    public void printPostOrder() {
        this.root.printNodePostOrder();
    }

    public static void main(String[] args) {
        int[] defaultData = { 1, 2, 3, 4, 5, 6, 7 };

        // Main menu loop
        Scanner scanner = new Scanner(System.in);
        BinaryTree binaryTree = null;
        boolean exit = false;
        int choice = 0;

        while (!exit) {
            System.out.println("\nBinary Search Tree Menu:");
            System.out.println("1. Create a binary search tree");
            System.out.println("2. Add a node");
            System.out.println("3. Delete a node");
            System.out.println("4. Print nodes by InOrder");
            System.out.println("5. Print nodes by PreOrder");
            System.out.println("6. Print nodes by PostOrder");
            System.out.println("7. Exit program");
            System.out.print("Select an option: ");

            // Scanner can throw exceptions if unsupported input is provided.
            // Handle exceptions with a try/catch
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                // If the scanner encounters an unsupported option, exit the program.
                choice = 7;
            }

            switch (choice) {
                case 1:
                    // Create a new binary search tree with default values 1-7
                    binaryTree = new BinaryTree(defaultData);
                    System.out.println("Binary search tree created with values 1 to 7.");
                    break;

                case 2:
                    if (binaryTree == null) {
                        System.out.println("Please create a binary search tree first.");
                        break;
                    }
                    System.out.print("Enter a value to add: ");
                    int addValue = scanner.nextInt();
                    binaryTree.add(addValue);
                    System.out.println("Node " + addValue + " added.");
                    break;

                case 3:
                    if (binaryTree == null) {
                        System.out.println("Please create a binary search tree first.");
                        break;
                    }
                    System.out.print("Enter a value to delete: ");
                    int deleteValue = scanner.nextInt();
                    binaryTree.remove(deleteValue);
                    System.out.println("Node " + deleteValue + " deleted.");
                    break;

                case 4:
                    if (binaryTree == null) {
                        System.out.println("Please create a binary search tree first.");
                        break;
                    }
                    System.out.print("InOrder Traversal: ");
                    binaryTree.printInOrder();
                    break;

                case 5:
                    if (binaryTree == null) {
                        System.out.println("Please create a binary search tree first.");
                        break;
                    }
                    System.out.print("PreOrder Traversal: ");
                    binaryTree.printPreOrder();
                    break;

                case 6:
                    if (binaryTree == null) {
                        System.out.println("Please create a binary search tree first.");
                        break;
                    }
                    System.out.print("PostOrder Traversal: ");
                    binaryTree.printPostOrder();
                    break;

                case 7:
                    exit = true;
                    System.out.println("Exiting program. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please select a number between 1 and 7.");
            }
        }

        scanner.close();
    }
}
