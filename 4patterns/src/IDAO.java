public interface IDAO {
    //inserts
    product InsertP(String name,float price,int amount,float age_restrictions);
    product_details  InsertDetailsForProduct(int productId,float weight,String manufactuer,String expire_date,String form);
    type_product InsertTypeofProduct(int productId,String type_name);
    ProductReview  InsertProductReview(int product_Id,String reviewerName,String reviewerText,float rating,int Id);
    //delete FUll product,product_details,type_product  BECAUSE CASCADE CASCADE))
    product deleteProduct(int id);
    // Another functions
    void searchProductsByAgeRestrictionsRange(float minAge, float maxAge);
    void searchProductsByName(String name);

    void  searchProductsByPriceRange(float minPrice, float maxPrice);
    // Update
    product  UpdateProduct(int id,String name,float price,int amount,float age_restrictions);

    product_details  UpdateProductDetails(int productId,float weight,String manufactuer,String expire_date,String form);
    type_product  UpdateTypeProduct(int productId,String type_name);
    ProductReview  UpdateProductReview(int product_Id,String reviewerName,String reviewerText,float rating,int Id);
    //USER
    int getRolesIdByUserId(int userId);

    User insertUser(int id, String login, String password, int role_id);
    int authenticateUser(String login, String password);

}
