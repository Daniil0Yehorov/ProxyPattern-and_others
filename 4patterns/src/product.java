import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class product implements Observable{
    private List<Observer> observers = new ArrayList<>();
        private String name;
        private float price;
        private int amount;
        private  float age_restrictions;
        private int id;

public int getId() {
    return id;
}
    public String getName(){
            return name;
        }
        public float getPrice(){
            return price;
        }
        public int getAmount() {
            return amount;
        }
        public float getAge_restrictions() {
            return age_restrictions;
        }

    @Override
    public void registerObserver(Observer observer) {
        if(observer != null){
            this.observers.add(observer);
        }
    }

    @Override
    public void notifyObservers() {
        Iterator<Observer> it = observers.iterator();
        while(it.hasNext()){
            Observer observer = it.next();
            observer.update(this);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        if(observer != null){
            this.observers.remove(observer);
        }
    }
    public static class Builder {
        private String name;
        private float price;
        private int amount;
        private float age_restrictions;

        public Builder(String name) {
            this.name = name;
        }

        public Builder price(float price) {
            this.price = price;
            return this;
        }

        public Builder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder age_restrictions(float age_restrictions) {
            this.age_restrictions = age_restrictions;
            return this;
        }


        public product build() {
            return new product(this);
        }
    }
    private product(Builder builder) {
        name = builder.name;
        price = builder.price;
        amount = builder.amount;
        age_restrictions = builder.age_restrictions;
        this.observers = new ArrayList<>();
    }
    private product state;

    public void setState(product state) {
        this.name = state.getName();
        this.price = state.getPrice();
        this.amount = state.getAmount();
        this.age_restrictions = state.getAge_restrictions();
    }

    public  product getState() {
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setAge_restrictions(float age_restrictions) {
        this.age_restrictions = age_restrictions;
    }

    public  Memento saveState() {
        return new Memento(this);
    }

    public void restoreState(Memento memento) {
        setState(memento.getState());
    }
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", age_restrictions=" + age_restrictions +
                ", id=" + id +
                '}';
    }

}
