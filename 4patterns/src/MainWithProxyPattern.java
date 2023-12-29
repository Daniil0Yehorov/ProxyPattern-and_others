import java.util.Scanner;

public class MainLast {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MySQLProxy dao = new MySQLProxy();

        while (true) {
            System.out.println("Меню:");
            System.out.println("1. Реєстрація");
            System.out.println("2. Аутентифікація");
            System.out.println("3. Вихід");
            System.out.print("Виберіть дію: ");

            int choose= scanner.nextInt();

            switch (choose) {
                case 1:
                    // Реєстрація
                    System.out.print("Введіть ID користувача: ");
                    int idreg = scanner.nextInt();
                    System.out.print("Введіть логін: ");
                    String loginreg = scanner.next();
                    System.out.print("Введіть пароль: ");
                    String passwordreg = scanner.next();
                    System.out.print("Введіть ID ролі: ");
                    int idrole = scanner.nextInt();
                    dao.insertUser(idreg, loginreg, passwordreg, idrole);
                    System.out.println("Користувач зареєстрований.");
                    break;

                case 2:
                    // Аутентифікація
                    System.out.print("Введіть логін: ");
                    String log = scanner.next();
                    System.out.print("Введіть пароль: ");
                    String pas = scanner.next();
                    int roleUser = dao.authenticateUser(log, pas);
                    if (roleUser != 0) {
                        System.out.println("Аутентифікація успішна.");
                        MySQLProxy dao1 = new MySQLProxy(roleUser);
                        autenfication(dao1, scanner);
                    }
                    else {
                        System.out.println("Аутентифікація не вдалася. Невірний логін або пароль.");
                    }
                    break;

                case 3:
                    // Вихід з програми
                    System.out.println("Вихід з програми.");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
                    break;
            }
        }
    }
    public static class SensorRole {

        public	String CancelRole() {
            return "Вам відказано у доступі";
        }
        public	String AceptRole() {
            return "Вам дозволено у доступі";
        }


    }
    private static void autenfication(MySQLProxy dao, Scanner scanner) {
        while (true) {
            System.out.println("Дії з бд:");
            System.out.println("1. Пошук продукту за назвою");// кожен користувач може шукати продукт за назвою
            System.out.println("2. Видалення продукту за id");// тільки адмін
            System.out.println("3. Додавання нового продукту до системи");// тільки адмін
            System.out.println("4. Оновлення даних певного продукту у системі"); // тільки адмін
            System.out.println("5. Вихід");
            System.out.print("Виберіть дію: ");
            int choose = scanner.nextInt();
            SensorRole sr=new SensorRole();
            scanner.nextLine();
            switch (choose) {
                case 1:
                    // Пошук продукту за назвою
                    System.out.print("Введіть назву продукту: ");
                    String Pname = scanner.nextLine();
                    dao.searchProductsByName(Pname);
                    System.out.println(sr.AceptRole());
                    break;

                case 5:
                    // Вихід з меню після аутентифікації
                    System.out.println("Вихід з меню після аутентифікації.");
                    return;
                case 2:
                    // видалення продукту
                    System.out.println("Введіть id продукту для видалення: ");
                    int idpFordelete = scanner.nextInt();
                    product a1= dao.deleteProduct(idpFordelete);
                    if(a1==null){
                        System.out.println(sr.CancelRole());
                    }
                    else {System.out.println(sr.AceptRole());}
                    break;
                case 3:
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
                    product a2= dao.InsertP(name, price, amount, age_restrictions);
                    if(a2==null){
                        System.out.println(sr.CancelRole());
                    }
                    else {System.out.println(sr.AceptRole());}
                    break;
                case 4:
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
                    product a3=dao.UpdateProduct(productIdToUpdate,newName, newPrice, newAmount, newAgeRestrictions);
                    if(a3==null){
                        System.out.println(sr.CancelRole());
                    }
                    else {System.out.println(sr.AceptRole());}
                    break;

                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
                    break;
            }
        }
    }
}
