import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class product_details implements Observable {
    private int productId;
    private Float weight;
    private String manufactuer;
    private String expire_date;
    private String form;
    private List<Observer> observers;

    public int getProductId() {
        return productId;
    }

    public Float getWeight() {
        return weight;
    }

    public String getManufactuer() {
        return manufactuer;
    }

    public String getExpire_date() {
        return expire_date;
    }

    public String getForm() {
        return form;
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
    public static class Builder {
        private int productId;
        private Float weight;
        private String manufactuer;
        private String expire_date;
        private String form;

        public Builder(int productId) {
            this.productId = productId;
        }

        public Builder weight(Float weight) {
            this.weight = weight;
            return this;
        }

        public Builder manufactuer(String manufactuer) {
            this.manufactuer = manufactuer;
            return this;
        }

        public Builder expire_date(String expire_date) {
            this.expire_date = expire_date;
            return this;
        }

        public Builder form(String form) {
            this.form = form;
            return this;
        }

        public product_details build() {
            return new product_details(this);
        }
    }

    private product_details(Builder builder) {
        productId = builder.productId;
        weight = builder.weight;
        manufactuer = builder.manufactuer;
        expire_date = builder.expire_date;
        form = builder.form;
        this.observers = new ArrayList<>();
    }
}