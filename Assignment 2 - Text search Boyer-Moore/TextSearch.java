import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextSearch {
    private String text;

    public TextSearch(String text) {
        this.text = text;
    }

    public void displayText() {
        System.out.println(this.text);
    }

    public void search(String pattern) {
        List<Integer> list = searchBoyerMoore(pattern);
        String output = "";
        int counter = 0;

        for (int i = 0; i < this.text.length(); i++) {
            if (list.contains(i)) {
                counter = pattern.length();
                output += "[";
            }

            output += this.text.charAt(i);

            if (counter > 0) {
                counter--;
                if (counter == 0) {
                    output += "]";

                }
            }
        }

        System.out.println(output);

        for (Integer i : list) {
            System.out.println("Pattern match at index: " + i);
        }
    }

    private List<Integer> searchBoyerMoore(String pattern) {
        int patternLength = pattern.length();
        int textLength = this.text.length();
        List<Integer> matchIndices = new ArrayList<Integer>();

        int i = patternLength - 1;

        // Main loop
        while (i < textLength) {
            char character = this.text.charAt(i);
            int patternEndIndex = patternLength - 1;
            boolean isMatch = false;

            // Start at the end of the pattern
            int j = patternEndIndex;

            // Scan loop
            while (j >= 0) {
                // Compare character with each pattern characters starting from the end
                if (Character.toLowerCase(pattern.charAt(j)) != Character.toLowerCase(character)) {
                    j--;
                } else {
                    isMatch = true;
                    break;
                }
            }

            if (isMatch) {
                i += patternLength - j;
                j = patternEndIndex;

                // Compare
                while (j >= 0 && i < textLength) {
                    int textIndex = i - (pattern.length() - j);
                    if (Character.toLowerCase(pattern.charAt(j)) == Character
                            .toLowerCase(text.charAt(textIndex))) {
                        if (j > 0) {
                            j--;
                        } else {
                            // System.out.println("Pattern match at index: " + (i - pattern.length()));
                            matchIndices.add(i - pattern.length());
                            i += patternLength;
                        }
                    } else {
                        break;
                    }
                }
            } else {
                i += patternLength;
            }

        }

        return matchIndices;
    }

    public static void main(String[] args) {
        String states = "Alabama Alaska Arizona Arkansas California Colorado " +
                "Connecticut Delaware Florida Georgia Hawaii Idaho " +
                "Illinois Indiana Iowa Kansas Kentucky Louisiana " +
                "Maine Maryland Massachusetts Michigan Minnesota " +
                "Mississippi Missouri Montana Nebraska Nevada " +
                "New Hampshire New Jersey New Mexico New York " +
                "North Carolina North Dakota Ohio Oklahoma Oregon " +
                "Pennsylvania Rhode Island South Carolina South Dakota " +
                "Tennessee Texas Utah Vermont Virginia Washington " +
                "West Virginia Wisconsin Wyoming";
        TextSearch textSearch = new TextSearch(states);
        Scanner scanner = new Scanner(System.in);

        // Main menu loop
        boolean exit = false;
        int choice = 0;

        while (!exit) {
            System.out.println("\nBoyer-Moore Text Searching Algorithm Menu:");
            System.out.println("1. Display the text");
            System.out.println("2. Search");
            System.out.println("3. Exit program");
            System.out.print("Select an option: ");

            // Scanner can throw exceptions if unsupported input is provided.
            // Handle exceptions with a try/catch
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                // If the scanner encounters an unsupported option, exit the program.
                choice = 10;
            }

            switch (choice) {
                case 1:
                    // Create a new binary search tree with default values 1-7
                    System.out.println("Binary search tree created with values 1 to 7.");
                    break;

                case 2:
                    // Consume leftover new line
                    scanner.nextLine();
                    System.out.print("Enter a search term: ");
                    String pattern = scanner.nextLine();
                    if (pattern.length() > 0) {
                        textSearch.search(pattern);
                    }
                    break;

                case 3:
                    exit = true;
                    System.out.println("Exiting program. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please select a number between 1 and 3.");
            }
        }

        scanner.close();
    }
}