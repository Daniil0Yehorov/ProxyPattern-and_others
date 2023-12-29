import java.util.Scanner;

public class TestMain {
    public static void main(String[] args) {
        //меню
        MySQLDAO dao = MySQLDAO.getInstance();
        Scanner scanner = new Scanner(System.in);

        if (dao != null) {
            System.out.println("Виберіть дію:");
            System.out.println("1 - Вставити продукт");
            System.out.println("2 - Видалити продукт");
            System.out.println("3 - Вставити деталі продукту");
            System.out.println("4 - Вставити тип продукту");
            System.out.println("5 - Оновити продукт");
            System.out.println("6 - Оновити деталі продукту");
            System.out.println("7 - Оновити тип продукту");
            System.out.println("8 - Пошук за віковими обмеженнями");
            System.out.println("9 - Пошук за ціною");
            System.out.println("10 - Пошук за назвою");
            System.out.println("11 - Вставити резенцію на продукт");
            System.out.println("12 - Оновити резенцію на продукт");
            System.out.println("0 - Вийти");

            while (true) {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Вставка продукту
                        scanner.nextLine();
                        System.out.println("назва продукту:");
                        String name = scanner.nextLine();
                        System.out.println("Ціна продукту:");
                        Float price = scanner.nextFloat();
                        System.out.println("Кількість товару:");
                        int amount = scanner.nextInt();
                        System.out.println("Вікові обмеження:");
                        float age_restrictions = scanner.nextFloat();

                        product prod = dao.InsertP(name, price, amount, age_restrictions);
                        if (prod != null) {
                            Observer someObserver = new Observers();
                            prod.registerObserver(someObserver);
                            prod.notifyObservers();
                                System.out.println("Назва продукту: " + prod.getName());
                                System.out.println("Ціна: " + prod.getPrice());
                                System.out.println("Кількість: " + prod.getAmount());
                                System.out.println("Вікові обмеження: " + prod.getAge_restrictions());

                        } else {
                            System.out.println("Помилка при додаванні продукту.");
                        }
                        break;

                    case 2:
                        // Видалення продукту
                        System.out.println("Введіть ID продукту, який потрібно видалити:");
                        int productIdToDelete = scanner.nextInt();
                        product toDelete = dao.searchProductById(productIdToDelete);

                        if (toDelete != null) {
                            System.out.println("Назва продукту: " + toDelete.getName());
                            System.out.println("Ціна: " + toDelete.getPrice());
                            System.out.println("Кількість: " + toDelete.getAmount());
                            System.out.println("Вікові обмеження: " + toDelete.getAge_restrictions());
                            Observer someObserver = new Observers();
                            toDelete.registerObserver(someObserver);
                            toDelete.notifyObservers();
                            toDelete.removeObserver(someObserver);

                            System.out.println("Продукт з ID " + productIdToDelete + " був видалений.");
                        } else {
                            System.out.println("Продукт з ID " + productIdToDelete + " не знайдений.");
                        }
                        break;

                    case 3:
                        // Вставка даних про продукт
                        System.out.println("Введіть ID продукту для вставки даних:");
                        int productIdForDetails = scanner.nextInt();
                        System.out.println("Вага:");
                        float weight = scanner.nextFloat();
                        scanner.nextLine();
                        System.out.println("Виробник:");
                        String manufactuer = scanner.nextLine();
                        System.out.println("Термін придатності (рік-місяць-день):");
                        String expireDate = scanner.nextLine();
                        System.out.println("Форма:");
                        String form = scanner.nextLine();

                        product_details details = dao.InsertDetailsForProduct(productIdForDetails, weight, manufactuer, expireDate, form);
                        if (details != null) {
                            Observer someObserver = new Observers();
                            details.registerObserver(someObserver);
                            details.notifyObservers();
                                System.out.println("ID деталі: " + details.getProductId());
                                System.out.println("Вага: " + details.getWeight());
                                System.out.println("Виробник: " + details.getManufactuer());
                                System.out.println("Термін придатності: " + details.getExpire_date());
                                System.out.println("Форма: " + details.getForm());
                        } else {
                            System.out.println("Помилка при додаванні деталей для продукта.");
                        }
                        break;

                    case 4:
                        // Вставка типу продукту
                        System.out.println("Введіть ID продукту для вставки типу:");
                        int productIdForType = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Тип продукту:");
                        String type_name = scanner.nextLine();
                        type_product typeProducts = dao.InsertTypeofProduct(productIdForType, type_name);
                        if (typeProducts != null) {
                            Observer someObserver = new Observers();
                            typeProducts.registerObserver(someObserver);
                            typeProducts.notifyObservers();
                                System.out.println("ID продукту: " + typeProducts.getProductId());
                                System.out.println("Тип їжі: " + typeProducts.getType_name());
                        } else {
                            System.out.println("Помилка при додаванні типу продукту.");
                        }
                        break;

                    case 5:
                        // Оновлення продукту1
                        System.out.println("Введіть ID продукту для оновлення:");
                        int productIdToUpdate = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Нова назва:");
                        String newName = scanner.nextLine();
                        System.out.println("Нова ціна:");
                        float newPrice = scanner.nextFloat();
                        System.out.println("Нова кількість:");
                        int newAmount = scanner.nextInt();
                        System.out.println("Нові вікові обмеження:");
                        float newAgeRestrictions = scanner.nextFloat();

                         product updatedProduct = dao.UpdateProduct(productIdToUpdate, newName, newPrice, newAmount, newAgeRestrictions);
                        if (updatedProduct != null) {
                                System.out.println("Назва продукту: " + updatedProduct.getName());
                                System.out.println("Ціна: " + updatedProduct.getPrice());
                                System.out.println("Кількість: " + updatedProduct.getAmount());
                                System.out.println("Вікові обмеження: " + updatedProduct.getAge_restrictions());
                            Observer someObserver = new Observers();
                            updatedProduct.registerObserver(someObserver);
                            updatedProduct.notifyObservers();
                            updatedProduct.removeObserver(someObserver);
                        } else {
                            System.out.println("Помилка при оновленні продукту.");
                        }
                        break;

                    case 6:
                        // Оновлення даних про продукт
                        System.out.println("Введіть ID деталі для оновлення:");
                        int detailIdToUpdate = scanner.nextInt();
                        System.out.println("Нова вага:");
                        float newWeight = scanner.nextFloat();
                        scanner.nextLine();
                        System.out.println("Новий виробник:");
                        String newManufacturer = scanner.nextLine();
                        System.out.println("Новий термін придатності (рік-місяць-день):");
                        String newExpireDate = scanner.nextLine();
                        System.out.println("Нова форма:");
                        String newForm = scanner.nextLine();

                        product_details updatedDetail = dao.UpdateProductDetails(detailIdToUpdate, newWeight, newManufacturer, newExpireDate, newForm);

                        if (updatedDetail != null) {
                                System.out.println("ID деталі: " + updatedDetail.getProductId());
                                System.out.println("Вага: " + updatedDetail.getWeight());
                                System.out.println("Виробник: " + updatedDetail.getManufactuer());
                                System.out.println("Термін придатності: " + updatedDetail.getExpire_date());
                                System.out.println("Форма: " + updatedDetail.getForm());
                            Observer someObserver = new Observers();
                            updatedDetail.registerObserver(someObserver);
                            updatedDetail.notifyObservers();
                            updatedDetail.removeObserver(someObserver);
                        } else {
                            System.out.println("Помилка при оновленні деталей продукту.");
                        }
                        break;

                    case 7:
                        // Оновлення типу продукту
                        System.out.println("Введіть ID типу продукту для оновлення:");
                        int typeIdToUpdate = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Новий тип продукту:");
                        String newTypeName = scanner.nextLine();
                        type_product updatedTypeProduct= dao.UpdateTypeProduct(typeIdToUpdate, newTypeName);

                        if (updatedTypeProduct != null) {
                                System.out.println("ID продукту: " + updatedTypeProduct.getProductId());
                                System.out.println("Тип їжі: " + updatedTypeProduct.getType_name());
                            Observer someObserver = new Observers();
                            updatedTypeProduct.registerObserver(someObserver);
                            updatedTypeProduct.notifyObservers();
                            updatedTypeProduct.removeObserver(someObserver);
                        } else {
                            System.out.println("Помилка при оновленні типу продукту.");
                        }
                        break;

                    case 8:
                        //Пошук з та до вікові обмеження
                        System.out.println("Введіть мінімальне значення вікових обмежень:");
                        float minAge = scanner.nextFloat();
                        scanner.nextLine();
                        System.out.println("Введіть максимальне значення вікових обмежень:");
                        float maxAge = scanner.nextFloat();
                        scanner.nextLine();
                        dao.searchProductsByAgeRestrictionsRange(minAge, maxAge);
                        break;

                    case 9:
                        // Пошук за ціною
                        System.out.println("Введіть мінімальну ціну:");
                        float minPrice = scanner.nextFloat();
                        System.out.println("Введіть максимальну ціну:");
                        float maxPrice = scanner.nextFloat();
                        dao.searchProductsByPriceRange(minPrice, maxPrice);
                        break;

                    case 10:
                        // Пошук за назвою
                        System.out.println("Введіть назву продукту:");
                        scanner.nextLine();
                        String productName = scanner.nextLine();
                        dao.searchProductsByName(productName);
                        break;
                    case 11:
                        // Вставка рецензії на продукт
                        System.out.println("Введіть ID продукту для рецензії:");
                        int productIdForReview = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Ім'я рецензента:");
                        String reviewerName = scanner.nextLine();
                        System.out.println("Текст рецензії:");
                        String reviewerText = scanner.nextLine();
                        System.out.println("Рейтінг:");
                        float rating = scanner.nextFloat();
                        System.out.println("Введіть ID рецензії:");
                        int reviewId = scanner.nextInt();
                        ProductReview reviews = dao.InsertProductReview(productIdForReview, reviewerName, reviewerText, rating, reviewId);
                        if (reviews != null) {
                            Observer someObserver = new Observers();
                            reviews.registerObserver(someObserver);
                            reviews.notifyObservers();

                                System.out.println("Рецензія від " + reviews.getReviewerName() + ": " + reviews.getReviewerText()
                                        + " (Рейтінг: " + reviews.getRating()
                                        + " ID рецензії: " + reviews.getId() + ")");

                        } else {
                            System.out.println("Помилка, або рецензія не була залишена.");
                        }
                        break;

                    case 12:
                        // Оновлення рецензії на продукт
                        System.out.println("Введіть ID продукту для оновлення рецензії:");
                        int productIdForUpdate = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Введіть ID рецензії для оновлення:");
                        int reviewIdForUpdate = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Нове ім'я рецензента:");
                        String updatedReviewerName = scanner.nextLine();
                        System.out.println("Новий текст рецензії:");
                        String updatedReviewerText = scanner.nextLine();
                        System.out.println("Новий рейтінг:");
                        float updatedRating = scanner.nextFloat();

                        ProductReview updatedReviews = dao.UpdateProductReview(productIdForUpdate, updatedReviewerName,
                                updatedReviewerText, updatedRating, reviewIdForUpdate);
                        if (updatedReviews != null) {
                            System.out.println("Оновлені рецензія на продукт:");
                            System.out.println("Продукт ID: " + updatedReviews.getProduct_Id());
                            System.out.println("Ім'я рецензента: " + updatedReviews.getReviewerName());
                            System.out.println("Текст рецензії: " + updatedReviews.getReviewerText());
                            System.out.println("Рейтінг: " + updatedReviews.getRating());
                            System.out.println("ID рецензії: " + updatedReviews.getId());
                            System.out.println("---------------");
                            Observer someObserver = new Observers();
                            updatedReviews.registerObserver(someObserver);
                            updatedReviews.notifyObservers();
                            updatedReviews.removeObserver(someObserver);
                        }

                        break;
                    case 0:
                        // Вихід
                        scanner.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Невірний вибір. Виберіть правильну дію.");
                        break;
                }
            }
        } else {
            System.out.println("Помилка при отриманні IDAO.");
        }


    }
    private static void autenfication(MySQLProxy dao, Scanner scanner) {
        while (true) {
            System.out.println("Дії з бд:");
            System.out.println("1. Пошук продукту за назвою");// кожен користувач може шукати продукт за назвою
            System.out.println("3. Видалення продукту за id");// тільки адмін
            System.out.println("4. Додавання нового продукту до системи");// тільки адмін
            System.out.println("5. Оновлення даних певного продукту у системі"); // тільки адмін
            System.out.println("2. Вихід");

            System.out.print("Виберіть дію: ");
            int choose = scanner.nextInt();
            scanner.nextLine();
            switch (choose) {
                case 1:
                    // Пошук продукту за назвою
                    System.out.print("Введіть назву продукту: ");
                    String Pname = scanner.nextLine();
                    dao.searchProductsByName(Pname);
                    break;

                case 2:
                    // Вихід з меню після аутентифікації
                    System.out.println("Вихід з меню після аутентифікації.");
                    return;
                case 3:
                    // видалення продукту
                    System.out.print("Введіть id продукту для видалення: ");
                    int idpFordelete = scanner.nextInt();
                    dao.deleteProduct(idpFordelete);
                    break;
                case 4:
                    // Додавання нового продукту у систему
                    scanner.nextLine();
                    System.out.println("назва продукту:");
                    String name = scanner.nextLine();
                    System.out.println("Ціна продукту:");
                    Float price = scanner.nextFloat();
                    System.out.println("Кількість товару:");
                    int amount = scanner.nextInt();
                    System.out.println("Вікові обмеження:");
                    float age_restrictions = scanner.nextFloat();
                    dao.InsertP(name, price, amount, age_restrictions);
                    break;
                case 5:
                    // Оновлення даних певного продукту у системі
                    System.out.println("Введіть ID продукту для оновлення:");
                    int productIdToUpdate = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Нова назва:");
                    String newName = scanner.nextLine();
                    System.out.println("Нова ціна:");
                    float newPrice = scanner.nextFloat();
                    System.out.println("Нова кількість:");
                    int newAmount = scanner.nextInt();
                    System.out.println("Нові вікові обмеження:");
                    float newAgeRestrictions = scanner.nextFloat();
                    dao.UpdateProduct(productIdToUpdate,newName, newPrice, newAmount, newAgeRestrictions);
                    break;

                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
                    break;
            }
        }
    }
}
