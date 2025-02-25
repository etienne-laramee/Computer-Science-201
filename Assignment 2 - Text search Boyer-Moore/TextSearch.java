import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextSearch {
    private String text;

    public TextSearch(String text) {
        this.text = text;
    }

    public int getLength() {
        return this.text.length();
    }

    public char getCharacterAt(int index) {
        return Character.toLowerCase(this.text.charAt(index));
    }

    // Display the whole input text. Wrap at 80 characters for better readability
    public void displayText() {
        System.out.println("\n" + this.text.replaceAll("(.{80})", "$1\n"));
    }

    // Search the pattern within the input text and display all the found indices.
    // Also prints the input text with the found patterns enclosed in squared brackets.
    public void search(Pattern pattern) {
        // Search function
        List<Integer> list = searchBoyerMoore(pattern);

        String output = "";
        int counter = 0;

        // Insert square brackets where patterns have been found
        for (int i = 0; i < this.text.length(); i++) {
            if (list.contains(i)) {
                counter = pattern.getLength();
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

        // Wrap text for better readability
        System.out.println("\n" + output.replaceAll("(.{80})", "$1\n"));

        // Display summary
        System.out.println("\n--------------------------------------------------------------------------------");
        if (list.size() > 0) {
            System.out.println("One or more match fount for pattern: " + pattern.toString());
            for (Integer i : list) {
                System.out.println("Pattern match at index: " + i);
            }
        } else {
            System.out.println("No match found for pattern: " + pattern.toString());
        }
        System.out.println("--------------------------------------------------------------------------------");
    }

    // Main algorithm
    private List<Integer> searchBoyerMoore(Pattern pattern) {
        List<Integer> matchIndices = new ArrayList<Integer>();

        int i = pattern.getLastIndex();
        char character;

        // Loop through the whole text
        while (i < this.getLength()) {
            // For each character from the text, search if it
            // is contained in the pattern and store their index
            character = this.getCharacterAt(i);
            List<Integer> matchingPatternIndices = pattern.contains(character);

            if (matchingPatternIndices.isEmpty()) {
                // If not, move past mismatch
            } else {
                // If yes
                // Align each match and compare
                for (int matchIndex : matchingPatternIndices) {
                    // Align indices
                    int textIndex = (i + pattern.getReverseIndex(matchIndex));
                    int patternIndex = pattern.getLastIndex();

                    boolean isMatch = true;

                    // Compare character by character between text and pattern
                    while (patternIndex >= 0) {
                        if (textIndex < this.getLength()
                                && pattern.getCharacterAt(patternIndex) == this.getCharacterAt(textIndex)) {
                            // Keep going
                            if (patternIndex == 0) {
                                break;
                            } else {
                                patternIndex--;
                                textIndex--;
                            }
                        } else {
                            isMatch = false;
                            break;
                        }
                    }

                    // If the whole pattern matched, add its index to the list
                    if (isMatch) {
                        matchIndices.add(textIndex);
                    }
                }
            }

            // Move past match/mismatch
            i += pattern.getLength();
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
            System.out.println(
                    "\n\n--------------------------------------------------------------------------------");
            System.out.println("Boyer-Moore Text Searching Algorithm Menu:");
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("1. Display the text");
            System.out.println("2. Search");
            System.out.println("3. Exit program");
            System.out.print("Select an option: ");

            // Scanner can throw exceptions if unsupported input is provided.
            // Handle exceptions with a try/catch
            try {
                // Get choice from user
                choice = Integer.parseInt(scanner.next());

                // Consume leftover new line if any
                scanner.nextLine();
            } catch (Exception e) {
                // If the scanner encounters an unsupported option, go to default switch case.
                choice = 999;
            }

            switch (choice) {
                case 1:
                    textSearch.displayText();
                    break;

                case 2:
                    System.out.print("Enter a search term: ");

                    // Read pattern from user
                    String pattern = scanner.nextLine();

                    // Make sure pattern cannot be greater than input text
                    if (pattern.length() >= textSearch.text.length()) {
                        System.out.println("The search term should be shorter than the text to be searched: "
                                + textSearch.text.length() + " characters");
                        break;
                    }

                    // if pattern text is valid, go to search function
                    if (pattern.length() > 0) {
                        textSearch.search(new Pattern(pattern));
                    }

                    System.out.println(
                            "////////////////////////////////////////////////////////////////////////////////");

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