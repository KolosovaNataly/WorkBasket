import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static int[] prices = {45, 40, 80};
    static String[] products = {"Чай", "Булочка", "Шоколад"};
    static int[] quantity = new int[products.length];

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        int num = 0;
        int lot = 0;
        Basket basket = new Basket(prices, products, quantity);

        madeDir();
        while (true) {
            if (count == 0) {
                File oldFile = new File("directBin/Basket.bin");
                if (oldFile.isFile() && Basket.getTotal() == 0) {
                    System.out.println("Хотите посмотреть прошлый список покупок? \nY - если ДА");
                    String ask = scanner.nextLine();
                    if (ask.equalsIgnoreCase("Y")) {
                        basket = Basket.loadFromBinFile(oldFile, basket);
                        basket.printCart();
                    }
                }
                System.out.println("Список возможных товаров для покупки");
                for (int i = 0; i < products.length; i++) {
                    System.out.printf("%d. %s %d руб/шт \n", i + 1, products[i], prices[i]);
                }
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
            count = basket.addToCart(num, lot);
        }
        basket.printCart();
        basket.saveBin(madeFile(), basket);
    }

    public static void madeDir() {
        File dir = new File("directBin");
        if (dir.mkdir()) {
            System.out.println(dir.getAbsolutePath());
        }
        System.out.println("Путь к папке " + dir.getAbsolutePath());
    }

    public static File madeFile() {
        File textFile = new File("directBin/Basket.bin");
        try {
            if (textFile.createNewFile()) {
                System.out.println("File added");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return textFile;
    }
}


