class AlreadyExistsException extends Exception {
    private int value;

    public AlreadyExistsException(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}