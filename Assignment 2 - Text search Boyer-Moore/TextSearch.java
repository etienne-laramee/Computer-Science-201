import java.util.ArrayList;
import java.util.List;

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
        for (Integer i : list) {
            System.out.println("Match at index: " + i);
        }
    }

    private List<Integer> searchBoyerMoore(String pattern) {
        int patternLength = pattern.length();
        int textLength = this.text.length();
        List<Integer> matchIndices = new ArrayList<Integer>();

        int i = patternLength - 1;

        // Main loop
        while (i < textLength) {
            System.out.println(text);
            System.out.println(" ".repeat(i - (patternLength-1)) + pattern);
            char character = this.text.charAt(i);
            int patternEndIndex = patternLength - 1;
            boolean isMatch = false;

            // Start at the end of the pattern
            int j = patternEndIndex;

            // Scan loop
            while (j >= 0) {
                // Compare character with each pattern characters starting from the end
                if (pattern.charAt(j) != character) {
                    j--;
                } else {
                    isMatch = true;
                    break;
                }
            }

            if (isMatch) {
                i += patternLength - j;
                j = patternEndIndex;

                System.out.println(text);
                System.out.println(" ".repeat(i - (patternLength)) + pattern);

                // Compare
                while (j >= 0) {
                    if (pattern.charAt(j) == text.charAt(i - (pattern.length() - j))) {
                        if (j > 0) {
                            j--;
                        } else {
                            //System.out.println("Pattern match at index: " + (i - pattern.length()));
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

        //states = "California";

        String pattern = "nia";

        TextSearch textSearch = new TextSearch(states);
        textSearch.displayText();
        textSearch.search(pattern);
    }
}