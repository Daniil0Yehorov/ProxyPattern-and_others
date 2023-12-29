import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductReview implements Observable{
    private int product_Id;
    private List<Observer> observers;
    private String reviewerName;
    private String reviewerText;
    private float  rating;
    private int Id;
    public int getProduct_Id() {
        return product_Id;
    }

    public int getId() {
        return Id;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public String getReviewerText() {
        return reviewerText;
    }

    public float getRating() {
        return rating;
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
        private int product_Id;

        private String reviewerName;
        private String reviewerText;
        private float  rating;
        private int Id;
        public Builder(int product_Id) {
            this.product_Id = product_Id;
        }

        public ProductReview.Builder reviewerName(String reviewerName) {
            this.reviewerName= reviewerName;
            return this;
        }

        public ProductReview.Builder reviewerText(String reviewerText) {
            this.reviewerText = reviewerText;
            return this;
        }

        public ProductReview.Builder rating(float rating) {
            this.rating = rating;
            return this;
        }
        public ProductReview.Builder Id(int Id) {
            this.Id = Id;
            return this;
        }
        public ProductReview build() {
            return new ProductReview(this);
        }
    }

    private ProductReview(ProductReview.Builder builder) {
        product_Id = builder.product_Id;
        reviewerName = builder.reviewerName;
        reviewerText = builder.reviewerText;
        rating= builder.rating;
        Id=builder.Id;
        this.observers = new ArrayList<>();
    }
}
