import java.util.ArrayList;
import java.util.List;

public class Pattern {
    private char[] pattern;
    private int length;
    
    public Pattern(String pattern) {
        this.pattern = pattern.toCharArray();
        this.length = pattern.length();
    }

    public int getLength() {
        return this.length;
    }

    public int getLastIndex() {
        return this.length - 1;
    }

    public int getReverseIndex(int index) {
        return this.getLastIndex() - index;
    }

    // Cycle through the pattern and try to find a matching character
    public List<Integer> contains(char character) {
        List<Integer> indices = new ArrayList<Integer>();

        for (int i = this.getLength() - 1; i >= 0; i--) {
            if (this.getCharacterAt(i) == character) {
                indices.add(i);
            }
        }

        return indices;
    }

    public char getCharacterAt(int index) {
        return Character.toLowerCase(this.pattern[index]);
    }

    public String toString() {
        return new String(this.pattern);
    }
}
