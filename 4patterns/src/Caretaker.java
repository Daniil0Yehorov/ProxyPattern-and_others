/*import java.util.ArrayList;
import java.util.List;

public class Caretaker {
    private List<Memento> mementoList = new ArrayList();
    private int index = 0;

    public Memento previous() {

        return (index > 0)?this.mementoList.get(--index):null;

    }
    public Memento next() {

        return (index < this.mementoList.size())?this.mementoList.get(++index):null;

    }

    public void addMemento(Memento memento) {
        if(index < this.mementoList.size())
        {
            for(int i = index; i < this.mementoList.size(); i++)
                this.mementoList.remove(i);
        }
        this.mementoList.add(memento);
        index++;
    }
}*/
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
