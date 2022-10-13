import java.io.*;

public class Basket {
    private static int[] prices = {45, 20, 80};
    private static String[] products = {"Чай", "Булочка", "Шоколад"};
    private static int[] quantity = new int[products.length];
    private StringBuilder str;
    private static int total;

    public Basket(int[] prices, String[] products, int[] quantity) {
        Basket.prices = prices;
        Basket.products = products;
        Basket.quantity = quantity;
    }

    public static int[] getPrices() {
        return prices;
    }

    public static String[] getProducts() {
        return products;
    }

    public static int[] getQuantity() {
        return quantity;
    }

    public int addToCart(int productNum, int amount) {
        if (productNum > 0 && productNum <= products.length) {
            quantity[productNum - 1] += amount;
            total += prices[productNum - 1] * amount;
        } else {
            System.out.println("Нет такого варианта");
        }
        return total;
    }

    public void printCart() {
        str = new StringBuilder();
        System.out.println("Ваша корзина:");
        for (int i = 0; i < quantity.length; i++) {
            if (quantity[i] != 0) {
                String write = (products[i] + " - " + prices[i] + " руб/шт, " + quantity[i] + " шт, " + (quantity[i] * prices[i]) + " руб в сумме");
                str.append(write + "\n");
                System.out.println(write);
            }
        }
        String write = "Итого: " + total + " руб";
        str.append(write);
        System.out.println(write);
    }

    public void saveTxt(File textFile) throws IOException {
        OutputStreamWriter out = null;
        out = new OutputStreamWriter(new FileOutputStream(textFile));
        out.write(str.toString());
        System.out.println("Ваш список сохранен в файле ");
        out.close();
    }

    public static Basket loadFromTxtFile(File textFile) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(textFile)));
        String[] textBasket;
        while (input.ready()) {
            textBasket = input.readLine().split(" ");
            for (int i = 0; i < products.length; i++) {
                String a = textBasket[0];
                if (products[i].contains(a)) {
                    quantity[i] = (Integer.parseInt(textBasket[4]));
                    total += prices[i] * quantity[i];
                }
            }
        }

        return new Basket(prices, products, quantity);
    }
}
