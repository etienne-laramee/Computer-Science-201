public class TextSearch {
    public void displayText() {

    }

    public static void main(String[] args) {
        for (String state : States.getStates()) {
            System.out.println("[" + state + "]");
        }
    }
}
