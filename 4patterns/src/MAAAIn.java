import java.util.Scanner;

public class MAAAIn {
    public static void main(String[] args) {
        MySQLDAO dao = MySQLDAO.getInstance();
        Caretaker caretaker = new Caretaker();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1.Створити продукт");
            System.out.println("2. Оновити продукт");
            System.out.println("3. Видалити продукт");
            System.out.println("4. Відновити попередній стан");
            System.out.println("5. Відновити наступний стан");
            System.out.println("6. Вийти");

            System.out.print("Оберіть дію: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createProduct(dao, caretaker, scanner);

                    break;
                case 2:
                    updateProduct(dao, caretaker, scanner);
                    break;
                case 3:
                    deleteProduct(dao, caretaker, scanner);
                    break;
                case 4:
                    restorePreviousState(caretaker);
                    break;
                case 5:
                    restoreNextState(caretaker);
                    break;
                case 6:
                    System.out.println("Завершена.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Оберіть від 1 до 6!!!");
            }
        }
    }

    private static void createProduct(MySQLDAO dao, Caretaker caretaker, Scanner scanner) {
        System.out.print("Введіть назву продукту: ");
        String name = scanner.nextLine();

        System.out.print("Введіть ціну продукту: ");
        float price = scanner.nextFloat();

        System.out.print("Введіть кількістть продукту: ");
        int amount = scanner.nextInt();

        System.out.print("Введіть вікові обмеження продукту: ");
        float ageRestrictions = scanner.nextFloat();

        product newProduct = dao.InsertP(name, price, amount, ageRestrictions);
        if (newProduct != null) {
            caretaker.addMemento(newProduct.saveState());
            System.out.println("Продукт був додан.");
        } else {
            System.out.println("Не вдалося створити продукт.");
        }
    }

    private static void updateProduct(MySQLDAO dao, Caretaker caretaker, Scanner scanner) {
        System.out.print("Введіть id продукту для оновлення: ");
        int productId = scanner.nextInt();
        scanner.nextLine();

        product existingProduct = dao.searchProductById(productId);
        if (existingProduct != null) {
            System.out.print("Введіть нове ім'я продукта: ");
            String newName = scanner.nextLine();

            System.out.print("Введіть нову ціну продукта: ");
            float newPrice = scanner.nextFloat();

            System.out.print("Введіть нову кількість продукту: ");
            int newAmount = scanner.nextInt();

            System.out.print("Введіть нові вікові обмеження продукту: ");
            float newAgeRestrictions = scanner.nextFloat();

            dao.UpdateProduct(productId, newName, newPrice, newAmount, newAgeRestrictions);
            product updatedProduct = dao.searchProductById(productId);
            if (updatedProduct != null) {
                caretaker.addMemento(updatedProduct.saveState());
                System.out.println("Продукт був успішно оновлен.");
            } else {
                System.out.println("Не вдалося оновити продукт.");
            }
        } else {
            System.out.println("Продукта не існує.");
        }
    }

    private static void deleteProduct(MySQLDAO dao, Caretaker caretaker, Scanner scanner) {
        System.out.print("Введіть id продукту для видалення: ");
        int productId = scanner.nextInt();

        product deletedProduct = dao.deleteProduct(productId);
        if (deletedProduct != null) {
            caretaker.addMemento(deletedProduct.saveState());
            System.out.println("Продукт був видалений.");
        } else {
            System.out.println("Не вдалося видалити.");
        }
    }

private static void restorePreviousState(Caretaker caretaker) {
    Memento previousState = caretaker.previous();
    if (previousState != null) {
        product restoredProduct = previousState.getState();
        System.out.println("Продукт успішно відновлено в попередній стан:");
        System.out.println("Ім'я: " + restoredProduct.getName());
        System.out.println("Ціна: " + restoredProduct.getPrice());
        System.out.println("Кількість: " + restoredProduct.getAmount());
        System.out.println("Вікові обмеження: " + restoredProduct.getAge_restrictions());

        // Оновлення в базі даних
        MySQLDAO.getInstance(). UpdateProduct(
                restoredProduct.getId(),
                restoredProduct.getName(),
                restoredProduct.getPrice(),
                restoredProduct.getAmount(),
                restoredProduct.getAge_restrictions()
        );

    } else {
        System.out.println("Немає попередніх станів для відновлення.");
    }
}

    private static void restoreNextState(Caretaker caretaker) {
        Memento nextState = caretaker.next();
        if (nextState != null) {
            product restoredProduct = nextState.getState();
            System.out.println("Продукт успішно відновлено в наступний стан:");
            System.out.println("Ім'я: " + restoredProduct.getName());
            System.out.println("Ціна: " + restoredProduct.getPrice());
            System.out.println("Кількість: " + restoredProduct.getAmount());
            System.out.println("Вікові обмеження: " + restoredProduct.getAge_restrictions());

            // Оновлення в базі даних
            MySQLDAO.getInstance().UpdateProduct(
                    restoredProduct.getId(),
                    restoredProduct.getName(),
                    restoredProduct.getPrice(),
                    restoredProduct.getAmount(),
                    restoredProduct.getAge_restrictions()
            );
        } else {
            System.out.println("Немає наступних станів для відновлення.");
        }
    }

}
