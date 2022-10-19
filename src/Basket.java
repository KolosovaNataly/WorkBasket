import java.io.*;

public class Basket {
    private int[] prices;
    private String[] products;
    private int[] quantity;
    private StringBuilder str;
    private static int total;
    public Basket(int[] prices, String[] products, int[] quantity) {
        this.prices = prices;
        this.products = products;
        this.quantity = quantity;
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
            for (int i = 0; i < Main.products.length; i++) {
                String a = textBasket[0];
                if (Main.products[i].contains(a)) {
                    Main.quantity[i] = (Integer.parseInt(textBasket[4]));
                    total += Main.prices[i] * Main.quantity[i];
                }
            }
        }
        return new Basket(Main.prices, Main.products, Main.quantity);
    }
}
