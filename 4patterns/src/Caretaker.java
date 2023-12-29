// Caretaker
import java.util.ArrayList;
import java.util.List;

public class Caretaker {
    private List<Memento> mementoList = new ArrayList<>();
    private int index = -1;

    public Memento previous() {
        if (index > 0) {
            return mementoList.get(--index);
        }
        return null;
    }

    public Memento next() {
        if (index < mementoList.size() - 1) {
            return mementoList.get(++index);
        }
        return null;
    }

    public void addMemento(Memento memento) {
        mementoList.subList(index + 1, mementoList.size()).clear();
        mementoList.add(memento);
        index = mementoList.size() - 1;
    }
}
