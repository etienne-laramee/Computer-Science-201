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
        boolean isBadCharacter;

        int textLength = this.text.length();
        int patternLength = pattern.length();
        int patternEndIndex = (patternLength - 1);
        
        List<Integer> matchIndices = new ArrayList<Integer>();
        
        
        // Main loop
        int i = patternEndIndex;
        while (i < textLength) {
            isBadCharacter = true;
            char character = Character.toLowerCase(this.text.charAt(i));
            char patternChar;

            // Start at the end of the pattern
            int j = patternEndIndex;

            // DEBUG
            printDebugOutput(this.text, pattern, i);
            // DEBUG

            // Bad Character Rule scan loop
            // Scenario 1: move the pattern until match with character
            while (j >= 0) {
                // Compare the Bad Character with each of the pattern characters starting from the end
                patternChar = Character.toLowerCase(pattern.charAt(j));

                if (patternChar == character) {
                    // If there is a match, stop there and at the next step, align the pattern with the text
                    isBadCharacter = false;
                    break;
                } else {
                    j--;
                }
            }

            if (isBadCharacter) {
                // Scenario 2: No match with character, move pattern past the character
                i += patternLength;
            } else {
                // Mismatch becomes a match
                // Align pattern with matched character
                i += (patternEndIndex - j);

                // Reset pattern starting index at the end
                j = patternEndIndex;

                // Compare pattern aligned with text
                while (j >= 0 && i < textLength) {
                    int textIndex = i - (patternEndIndex - j);
                    char textChar = Character.toLowerCase(text.charAt(textIndex));
                    patternChar = Character.toLowerCase(pattern.charAt(j));

                    if (textChar == patternChar) {
                        if (j > 0) {
                            // If we have not scanned the whole pattern yet, continue with the next character
                            j--;
                        } else {
                            // If all the pattern characters match with the text, add the index to a list and
                            // continue comparing with the rest of the text.
                            matchIndices.add(textIndex);
                            i += patternLength;
                        }
                    } else {
                        // If no match, abandon the comparison and go back to scan with the bad character rule
                        i += patternLength;
                        break;
                    }
                }
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
            System.out.println("\n--------------------------------------------------------------------------------");
            System.out.println("Boyer-Moore Text Searching Algorithm Menu:");
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("1. Display the text");
            System.out.println("2. Search");
            System.out.println("3. Exit program");
            System.out.print("Select an option: ");

            // Scanner can throw exceptions if unsupported input is provided.
            // Handle exceptions with a try/catch
            try {
                // Read a number
                choice = scanner.nextInt();

                // Consume leftover new line if any
                scanner.nextLine();
            } catch (Exception e) {
                // If the scanner encounters an unsupported option, exit the program.
                choice = 9;
            }

            switch (choice) {
                case 1:
                    textSearch.displayText();
                    break;

                case 2:
                    System.out.print("Enter a search term: ");
                    String pattern = scanner.nextLine();
                    if (pattern.length() >= textSearch.text.length()) {
                        System.out.println("The search term should be shorter than the text to be searched: "
                                + textSearch.text.length() + " characters");
                        break;
                    }
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
                    // Consume leftover new line if any
                    scanner.nextLine();
            }
        }

        scanner.close();
    }

    public static void printDebugOutput(String text, String pattern, int i) {
        // Ensure i is within bounds
        if (i < 0 || i >= text.length()) {
            System.out.println("Index i is out of bounds.");
            return;
        }

        // Calculate starting point so that i is 10 characters from the start
        int start = Math.max(0, i - 10);
        int end = Math.min(text.length(), start + 80);

        // Get the relevant substring and build the top line
        String substring = text.substring(start, end);
        StringBuilder topLine = new StringBuilder(substring);

        // Calculate position relative to the substring
        int relativePosition = i - start;

        // Insert brackets around the character at i
        topLine.insert(relativePosition, '[');
        topLine.insert(relativePosition + 2, ']'); // +2 to account for the first bracket

        // Ensure top line is exactly 80 characters
        if (topLine.length() > 80) {
            topLine.setLength(80);
        } else {
            while (topLine.length() < 80) {
                topLine.append(" ");
            }
        }

        // Calculate padding for pattern alignment
        int patternEndPosition = relativePosition + 1; // After the enclosed character
        int patternStartPosition = patternEndPosition - pattern.length() - 2; // -2 for the brackets
        StringBuilder bottomLine = new StringBuilder(" ");

        // Add padding to align the pattern
        for (int j = 0; j <= patternStartPosition; j++) {
            bottomLine.append(" ");
        }
        bottomLine.append(pattern);

        // Output the result
        System.out.println(topLine);
        System.out.println(bottomLine);
    }
}