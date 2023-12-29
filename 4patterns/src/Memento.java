public class Memento {
    private final product state;

    public Memento(product state) {
        this.state = state;
    }

    public product getState() {
        return state;
    }
}
