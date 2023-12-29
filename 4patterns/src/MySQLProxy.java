public class MySQLProxy implements  IDAO
{
    private int idUser;
    IDAO dao =MySQLDAO.getInstance();
    MySQLProxy(){

    }
    public MySQLProxy(int id_Userrole) {
        this.idUser=id_Userrole;
    }

    @Override
    public product InsertP(String name, float price, int amount, float age_restrictions) {
        int userRoleid=getRolesIdByUserId(idUser);
        if(userRoleid!=1){// не адмін
            return null;
        }
        else {return dao.InsertP(name,price,amount,age_restrictions);}
    }

    @Override
    public product_details InsertDetailsForProduct(int productId, float weight, String manufactuer, String expire_date, String form) {
        int userRoleid=getRolesIdByUserId(idUser);
        if(userRoleid!=1){// не адмін
            return null;
        }
        else {return dao.InsertDetailsForProduct(productId,weight,manufactuer,expire_date,form);}
    }

    @Override
    public type_product InsertTypeofProduct(int productId, String type_name) {
        int userRoleid=getRolesIdByUserId(idUser);
        if(userRoleid!=1){// не адмін
            return null;
        }
        else {return dao.InsertTypeofProduct(productId,type_name);}
    }

    @Override
    public ProductReview InsertProductReview(int product_Id, String reviewerName, String reviewerText, float rating, int Id) {
        int userRoleid=getRolesIdByUserId(idUser);
        if(userRoleid!=2){// не користувач
            return null;
        }
        else {return dao.InsertProductReview(product_Id,reviewerName,reviewerText,rating,Id);}
    }

    @Override
    public product deleteProduct(int id) {
        int userRoleid=getRolesIdByUserId(idUser);
        if(userRoleid!=1){// не адмін
            return null;
        }
        else {return dao.deleteProduct(id);}
    }

    @Override
    public void searchProductsByAgeRestrictionsRange(float minAge, float maxAge) {
       dao.searchProductsByAgeRestrictionsRange(minAge,maxAge);
    }

    @Override
    public void searchProductsByName(String name) {
        dao.searchProductsByName(name);
    }

    @Override
    public void searchProductsByPriceRange(float minPrice, float maxPrice) {
    dao.searchProductsByPriceRange(minPrice, maxPrice);
    }

    @Override
    public product UpdateProduct(int id, String name, float price, int amount, float age_restrictions) {
        int userRoleid=getRolesIdByUserId(idUser);
        if(userRoleid!=1){// не адмін
            return null;
        }
        else {return dao.UpdateProduct(id, name, price, amount, age_restrictions);}
    }

    @Override
    public product_details UpdateProductDetails(int productId, float weight, String manufactuer, String expire_date, String form) {
        int userRoleid=getRolesIdByUserId(idUser);
        if(userRoleid!=1){// не адмін
            return null;
        }
        else {return dao.UpdateProductDetails(productId, weight, manufactuer, expire_date, form);}
    }

    @Override
    public type_product UpdateTypeProduct(int productId, String type_name) {
        int userRoleid=getRolesIdByUserId(idUser);
        if(userRoleid!=1){// не адмін
            return null;
        }
        else {return dao.UpdateTypeProduct(productId, type_name);}
    }

    @Override
    public ProductReview UpdateProductReview(int product_Id, String reviewerName, String reviewerText, float rating, int Id) {
        int userRoleid=getRolesIdByUserId(idUser);
        if(userRoleid!=1){// не адмін
            return null;
        }
        else {return dao.UpdateProductReview(product_Id, reviewerName, reviewerText, rating, Id);}
    }

    @Override
    public int getRolesIdByUserId(int userId) {
        return dao.getRolesIdByUserId(userId);
    }
    @Override
    public User insertUser(int id, String login, String password, int role_id) {
        int userRoleid=getRolesIdByUserId(idUser);
        if(userRoleid!=2){// не користувач
                        return null;
        }
        else {return dao.insertUser(id, login, password, role_id);}
    }

    @Override
    public int authenticateUser(String login, String password) {
        return dao.authenticateUser(login, password);
    }


}
