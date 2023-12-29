import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MySQLDAO implements IDAO{


    private static Connection con = null;
   //SQL скріпти
    private static MySQLDAO instance;
    private static String INSERT_PRODUCT = "INSERT INTO product (name, price, amount, age_restrictions) VALUES (?, ?, ?, ?)";
    private static String SELECT_ALL_PRODUCTS = "SELECT * FROM product";
    private static String DELETE_BY_ID ="DELETE FROM product WHERE id = ?";
    private static String INSERT_DETAILS_FOR_PRODUCT_BY_ID = "INSERT INTO product_details " +
            "(product_id, weight, manufactuer, expire_date, form) VALUES (?, ?, ?, ?, ?)";
    private static String GET_product_details_for_product_id="SELECT * FROM product_details WHERE product_id = ?";
    private static String INSERT_type_of_product = "INSERT INTO type_product (product_id, type_name) VALUES (?, ?)";
    private static String Insert_ProductReview="INSERT INTO ProductReview(product_id,reviewerName,reviewerText,rating,id) Values (?,?,?,?,?)";
    private static String Get_All_productReviews="Select * From ProductReview where product_id=?";
    private static String Update_product="UPDATE product SET `name`=?,`price`=?,amount=?,age_restrictions=? WHERE product.id=?";
    private static String  SELECT_PRODUCT_BY_ID="Select * From product Where id=?";
    private static String Update_product_details="Update product_details Set weight=?,manufactuer=?," +
            "expire_date=?,form=? where product_id=?";
    private static String Update_type_product = "update type_product set type_name=? where product_id=?";
    private static String Update_ProductReview="UPDATE ProductReview SET reviewerName=?, reviewerText=?, rating=? WHERE id=?";
    private static String Search_range_age_restrictions="select * from product Where `age_restrictions`>? and `age_restrictions`<?;";
    private static String Search_range_price="select * from product Where `price`>? AND price<?;";
    private static String Search_name="select * from product Where `name`=?;";
    private static String SELECT_TYPE_FOR_PRODUCT_BY_ID="select * from type_product where product_id=?";
    private static String SelectroleidFromUser="SELECT roles_id FROM `user` WHERE id=?";
    private MySQLDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/mylastdb";
            String user = "root";
            String password ="JJi2dlwK23D23DgksqA";
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Помилка при завантаженні драйвера бази даних.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Помилка при підключенні до бази даних.");
        }
    }

    public static MySQLDAO getInstance() {
        if (instance == null) {
            instance = new MySQLDAO();
        }
        return instance;
    }

    public product InsertP(String name, float price, int amount, float age_restrictions) {
        product newProduct = null;
        try {
             newProduct = new product.Builder(name)
                    .price(price)
                    .amount(amount)
                    .age_restrictions(age_restrictions)
                    .build();

            PreparedStatement preparedStatement = con.prepareStatement(INSERT_PRODUCT);
            preparedStatement.setString(1, newProduct.getName());
            preparedStatement.setFloat(2, newProduct.getPrice());
            preparedStatement.setInt(3, newProduct.getAmount());
            preparedStatement.setFloat(4, newProduct.getAge_restrictions());
           preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (newProduct != null) {
            newProduct.notifyObservers();
        }
        return  newProduct;
    }
    public product[] getAllProducts() {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(SELECT_ALL_PRODUCTS);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<product> productList = new ArrayList<>();
            while (resultSet.next()) {
                product.Builder productBuilder = new product.Builder(resultSet.getString("name"))
                        .price(resultSet.getFloat("price"))
                        .amount(resultSet.getInt("amount"))
                        .age_restrictions(resultSet.getFloat("age_restrictions"));
                productList.add(productBuilder.build());
            }

            return productList.toArray(new product[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Обробити помилку SQL
        }
    }

    public product searchProductById(int productId) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(SELECT_PRODUCT_BY_ID);
            preparedStatement.setInt(1, productId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                float price = resultSet.getFloat("price");
                int amount = resultSet.getInt("amount");
                float ageRestrictions = resultSet.getFloat("age_restrictions");

                product foundProduct = new product.Builder(name)
                        .price(price)
                        .amount(amount)
                        .age_restrictions(ageRestrictions)
                        .build();

                return foundProduct;
            } else {
                System.out.println("Продукт з ID " + productId + " не знайдений.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public product deleteProduct(int id) {
        product deleteproduct=searchProductById(id);
        try {
            PreparedStatement preparedStatement = con.prepareStatement(DELETE_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            deleteproduct.notifyObservers();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleteproduct;
    }

    @Override
    public product_details InsertDetailsForProduct(int productId, float weight, String manufactuer, String expire_date, String form) {
        product_details newproductdetails = null;
        try {
            newproductdetails= new product_details.Builder(productId)
                    .weight(weight)
                    .manufactuer(manufactuer)
                    .expire_date(expire_date)
                    .form(form)
                    .build();

            PreparedStatement preparedStatement = con.prepareStatement(INSERT_DETAILS_FOR_PRODUCT_BY_ID);
            preparedStatement.setInt(1, newproductdetails.getProductId());
            preparedStatement.setFloat(2, newproductdetails.getWeight());
            preparedStatement.setString(3,newproductdetails.getManufactuer());
            preparedStatement.setString(4, newproductdetails.getExpire_date());
            preparedStatement.setString(5, newproductdetails.getForm());

           preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newproductdetails;
    }
public product_details[] getDetailsForProduct(int productId) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(GET_product_details_for_product_id);
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<product_details> detailsList = new ArrayList<>();

            while (resultSet.next()) {
                product_details details = new product_details.Builder(productId)
                        .weight(resultSet.getFloat("weight"))
                        .manufactuer(resultSet.getString("manufactuer"))
                        .expire_date(resultSet.getString("expire_date"))
                        .form(resultSet.getString("form"))
                        .build();
                detailsList.add(details);
            }

            return detailsList.toArray(new product_details[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Обробити помилку SQL
        }
    }
    public type_product[] getTypeForProduct(int productId){
        try {
            PreparedStatement preparedStatement = con.prepareStatement(SELECT_TYPE_FOR_PRODUCT_BY_ID);
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<type_product> typeProducts = new ArrayList<>();

            while (resultSet.next()) {
                type_product typeProduct = new type_product.Builder(resultSet.getInt("product_id"))
                        .typeName(resultSet.getString("type_name"))
                        .build();
                typeProducts.add(typeProduct);
            }

            return typeProducts.toArray(new type_product[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Обробити помилку SQL
        }
    }
    public type_product InsertTypeofProduct(int productId, String type_name) {
       type_product newTypeProduct=null;
        try {
            type_product.Builder builder = new type_product.Builder(productId);
            builder.typeName(type_name);
            newTypeProduct = builder.build();

            PreparedStatement preparedStatement = con.prepareStatement(INSERT_type_of_product);
            preparedStatement.setInt(1, newTypeProduct.getProductId());
            preparedStatement.setString(2, newTypeProduct.getType_name());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newTypeProduct;
    }
    public  ProductReview  InsertProductReview(int product_Id,String reviewerName,String reviewerText,float rating,int Id){
        ProductReview newProductReview=null;
        try {

            ProductReview.Builder builder = new ProductReview.Builder(product_Id);
            builder.reviewerName(reviewerName)
                    .reviewerText(reviewerText)
                    .rating(rating)
                    .Id(Id);
            newProductReview = builder.build();

            PreparedStatement preparedStatement = con.prepareStatement(Insert_ProductReview);
            preparedStatement.setInt(1, newProductReview.getProduct_Id());
            preparedStatement.setString(2, newProductReview.getReviewerName());
            preparedStatement.setString(3, newProductReview.getReviewerText());
            preparedStatement.setFloat(4, newProductReview.getRating());
            preparedStatement.setInt(5,newProductReview.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return newProductReview;
    }
    public ProductReview[] getProductReviewsForProduct(int product_Id) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(Get_All_productReviews);
            preparedStatement.setInt(1, product_Id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ProductReview> reviewsList = new ArrayList<>();

            while (resultSet.next()) {
                ProductReview review = new ProductReview.Builder(product_Id)
                        .reviewerName(resultSet.getString("reviewerName"))
                        .reviewerText(resultSet.getString("reviewerText"))
                        .rating(resultSet.getFloat("rating"))
                        .build();
                reviewsList.add(review);
            }

            return reviewsList.toArray(new ProductReview[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public product  UpdateProduct(int id,String name,float price,int amount,float age_restrictions){
        product updateProduct=searchProductById(id);
        try {

            PreparedStatement preparedStatement = con.prepareStatement(Update_product);
            preparedStatement.setString(1, name);
            preparedStatement.setFloat(2, price);
            preparedStatement.setInt(3, amount);
            preparedStatement.setFloat(4, age_restrictions);
            preparedStatement.setInt(5, id);

             preparedStatement.executeUpdate();
             updateProduct.notifyObservers();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateProduct;
    }
    public product_details SearchProductDetailsByProductId(int productId) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(GET_product_details_for_product_id);
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                product_details details = new product_details.Builder(productId)
                        .weight(resultSet.getFloat("weight"))
                        .manufactuer(resultSet.getString("manufactuer"))
                        .expire_date(resultSet.getString("expire_date"))
                        .form(resultSet.getString("form"))
                        .build();

                return details;
            } else {
                System.out.println("Деталі продукту з ID " + productId + " не знайдені.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public product_details UpdateProductDetails(int productId, float weight, String manufacturer, String expireDate, String form) {
       product_details UpdatedPD=SearchProductDetailsByProductId(productId);
        try {

            String updateDetailsQuery = Update_product_details;
            PreparedStatement preparedStatement = con.prepareStatement(updateDetailsQuery);
            preparedStatement.setFloat(1, weight);
            preparedStatement.setString(2, manufacturer);
            preparedStatement.setString(3, expireDate);
            preparedStatement.setString(4, form);
            preparedStatement.setInt(5, productId);
            preparedStatement.executeUpdate();
            UpdatedPD.notifyObservers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return UpdatedPD;
    }
    public type_product searchTypeProductByProductId(int productId) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(SELECT_TYPE_FOR_PRODUCT_BY_ID);
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                type_product typeProduct = new type_product.Builder(resultSet.getInt("product_id"))
                        .typeName(resultSet.getString("type_name"))
                        .build();

                return typeProduct;
            } else {
                System.out.println("Тип продукту з ID " + productId + " не знайдений.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public type_product UpdateTypeProduct(int productId, String type_name) {
        type_product tpUpdate=searchTypeProductByProductId(productId);
        try {
            PreparedStatement preparedStatement = con.prepareStatement(Update_type_product);
            preparedStatement.setString(1, type_name);
            preparedStatement.setInt(2, productId);

          preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Помилка при оновленні типу продукту.");
        }
        return tpUpdate;
    }
    public ProductReview searchProductReviewByHisID(int reviewId) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM ProductReview WHERE id = ?");
            preparedStatement.setInt(1, reviewId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                ProductReview review = new ProductReview.Builder(resultSet.getInt("product_id"))
                        .reviewerName(resultSet.getString("reviewerName"))
                        .reviewerText(resultSet.getString("reviewerText"))
                        .rating(resultSet.getFloat("rating"))
                        .Id(resultSet.getInt("id"))
                        .build();

                return review;
            } else {
                System.out.println("Рецензія з ID " + reviewId + " не знайдена.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public ProductReview  UpdateProductReview(int product_Id,String reviewerName,String reviewerText,float rating,int Id){
        ProductReview updatedProductReview=searchProductReviewByHisID(Id);
        try {
            ProductReview.Builder builder = new ProductReview.Builder(product_Id);
            builder.reviewerName(reviewerName)
                    .reviewerText(reviewerText)
                    .rating(rating)
                    .Id(Id);
            updatedProductReview = builder.build();
            PreparedStatement preparedStatement = con.prepareStatement(Update_ProductReview);
            preparedStatement.setString(1, updatedProductReview.getReviewerName());
            preparedStatement.setString(2, updatedProductReview.getReviewerText());
            preparedStatement.setFloat(3, updatedProductReview.getRating());
            preparedStatement.setInt(4, updatedProductReview.getId());
            preparedStatement.executeUpdate();
            updatedProductReview.notifyObservers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updatedProductReview;
    }
    public void searchProductsByAgeRestrictionsRange(float minAge, float maxAge) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(Search_range_age_restrictions);
            preparedStatement.setFloat(1, minAge);
            preparedStatement.setFloat(2, maxAge);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                product product = new product.Builder(resultSet.getString("name"))
                        .price(resultSet.getFloat("price"))
                        .age_restrictions(resultSet.getFloat("age_restrictions"))
                        .amount(resultSet.getInt("amount"))
                        .build();

                System.out.println("Назва продукту: " + product.getName());
                System.out.println("Ціна: " + product.getPrice());
                System.out.println("Вікові обмеження: " + product.getAge_restrictions());
                System.out.println("Кількість: " + product.getAmount());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchProductsByPriceRange(float minPrice, float maxPrice) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(Search_range_price);
            preparedStatement.setFloat(1, minPrice);
            preparedStatement.setFloat(2, maxPrice);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                product product = new product.Builder(resultSet.getString("name"))
                        .price(resultSet.getFloat("price"))
                        .age_restrictions(resultSet.getFloat("age_restrictions"))
                        .amount(resultSet.getInt("amount"))
                        .build();

                System.out.println("Назва продукту: " + product.getName());
                System.out.println("Ціна: " + product.getPrice());
                System.out.println("Вікові обмеження: " + product.getAge_restrictions());
                System.out.println("Кількість: " + product.getAmount());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchProductsByName(String name) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(Search_name);
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                product product = new product.Builder(resultSet.getString("name"))
                        .price(resultSet.getFloat("price"))
                        .age_restrictions(resultSet.getFloat("age_restrictions"))
                        .amount(resultSet.getInt("amount"))
                        .build();

                System.out.println("Назва продукту: " + product.getName());
                System.out.println("Ціна: " + product.getPrice());
                System.out.println("Вікові обмеження: " + product.getAge_restrictions());
                System.out.println("Кількість: " + product.getAmount());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // перероблені деякі функції для 4пз
    // реєстрація користувача
    public class SensorRole {

        public	String CancelRole() {
            return "Вам відказано у доступі";
        }
        public	String AceptRole() {
            return "Вам дозволено у доступі";
        }

    }
    public User insertUser(int id, String login, String password, int role_id) {
        try {
            String sqlQuery = "INSERT INTO `user` (id, login, password, Roles_id) VALUES (?, ?, ?, ?)";

            PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, password);
            preparedStatement.setInt(4, role_id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return new User(id, login, password, role_id);
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    //авторизація користувача
    public int authenticateUser(String login, String password) {
        try {
            String sqlQuery = "SELECT Roles_id FROM `user` WHERE login = ? AND password = ?";

            PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int role_id = resultSet.getInt("Roles_id");
                return role_id;
            } else {
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getRolesIdByUserId(int userId) {
        try {

            String sqlQuery = SelectroleidFromUser;

            PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("roles_id");
            } else {
                return -1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
