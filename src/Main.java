import config.Load;
import config.Log;
import config.Save;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static int[] prices = {50, 14, 80};
    static String[] products = {"Молоко", "Хлеб", "Авокадо"};
    static int[] quantity = new int[products.length];

    static Scanner scanner = new Scanner(System.in);

    static Basket basket = new Basket(prices, products, quantity);

    public static void main(String[] args) throws IOException {

        Load.load();
        Log.log();
        Save.save();

        int count = 0;
        int num = 0;
        int lot = 0;

        madeDir();
        while (true) {
            File oldFile = new File("direct/Basket.json");
            if (count == 0) {
                if (oldFile.isFile() || Load.isEnabled()) {
                    recoverBasket(oldFile);
                }
            }
            System.out.println("Список возможных товаров для покупки");
            for (int i = 0; i < products.length; i++) {
                System.out.printf("%d. %s %d руб/шт \n", i + 1, products[i], prices[i]);
            }
            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                break;
            }
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.out.println("Введите корректно: номер товара, пробел, количество товара");
                continue;
            }
            try {
                num = Integer.parseInt(parts[0]);
                lot = Integer.parseInt(parts[1]);
            } catch (NumberFormatException a) {
                System.out.println("Введена некорректная информация");
            }
            if (Log.isEnabled()) {
                ClientLog.log(num, lot);
                ClientLog.exportAsCSV(madeFileCsv());
            }
            count = basket.addToCart(num, lot);
        }
        basket.printCart();
        if (Save.isEnabled()) {
            basket.saveJson(madeFileJson());
        }
    }

    public static void madeDir() {
        File dir = new File("direct");
        if (dir.mkdir()) {
            System.out.println(dir.getAbsolutePath());
        }
        System.out.println("Путь к папке " + dir.getAbsolutePath());
    }

    public static File madeFileJson() {
        File textFile = new File("direct/Basket.json");
        try {
            if (textFile.createNewFile()) {
                System.out.println("File added");
            }
        } catch (IOException e) {
            System.out.println("Exсeption!!!!");
            throw new RuntimeException(e);
        }
        return textFile;
    }

    public static File madeFileCsv() {
        File textFile = new File("direct/log.csv");
        try {
            if (textFile.createNewFile()) {
                System.out.println("File added");
            }
        } catch (IOException e) {
            System.out.println("Exсeption!!!!");
            throw new RuntimeException(e);
        }
        return textFile;
    }

    public static void recoverBasket(File oldFile) {
        if (oldFile.isFile()) {
            System.out.println("Хотите посмотреть прошлый список покупок? \nY - если ДА");
            String ask = scanner.nextLine();
            if (ask.equalsIgnoreCase("Y")) {
                basket = Basket.loadFromJsonFile(oldFile);
                basket.printCart();
            }
        }
    }
}












