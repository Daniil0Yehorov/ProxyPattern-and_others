import java.util.List;

public interface Observer {
    void update(product prod);
    void update(product_details pd);

    void update(type_product tp);
    void update(ProductReview productReview);

}