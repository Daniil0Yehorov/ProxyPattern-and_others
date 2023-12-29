import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class type_product implements Observable{
    private String type_name;
    private int productId;
    private List<Observer> observers;
    public int getProductId() {
        return productId;
    }
        public String getType_name() {
            return type_name;
        }

    public static class Builder {
        private String type_name;
        private int productId;

        public Builder(int productId) {
            this.productId = productId;
        }

        public Builder typeName(String type_name) {
            this.type_name = type_name;
            return this;
        }

        public type_product build() {
            return new type_product(this);
        }
    }

    private type_product(Builder builder) {
        productId=builder.productId;
        type_name = builder.type_name;
        this.observers = new ArrayList<>();
    }
    public void registerObserver(Observer observer) {
        if(observer != null){
            this.observers.add(observer);
        }
    }

    public void notifyObservers() {
        Iterator<Observer> it = observers.iterator();
        while(it.hasNext()){
            Observer observer = it.next();
            observer.update(this);
        }
    }

    public void removeObserver(Observer observer) {
        if(observer != null){
            this.observers.remove(observer);
        }
    }
}
