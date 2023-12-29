public class Observers implements Observer {

    public void update(product prod) {
        System.out.println("Виявлена зміна у продукті з ім'ям: " + prod.getName());
    }


    public void update(product_details pd) {
        System.out.println("Виявлена зміна у  деталях продукта з id продукту: " + pd.getProductId());
    }
    public void update(type_product tp){
        System.out.println("Виявлена зміна у  типі продукту з id продукту: " + tp.getProductId());
    }
    public void update(ProductReview productReview){
        System.out.println("Виявлена зміна  відгуку у ревьювера: " + productReview.getReviewerName());
    }
    public void delete(int id) {
        System.out.println("Product Deleted: " + id);
    }
}