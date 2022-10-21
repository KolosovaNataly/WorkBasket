

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class Basket implements Serializable {
    public static final String ARRAY_ELEMENTS_SEPARATOR = ";";
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

    public int[] getPrices() {
        return prices;
    }

    public int[] getQuantity() {
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

    public void saveTxt(File textFile) {
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (int e : quantity) {
                out.print(e + ARRAY_ELEMENTS_SEPARATOR);
            }
            out.println();
            for (String e : products) {
                out.print(e + ARRAY_ELEMENTS_SEPARATOR);
            }
            out.println();
            for (int e : prices) {
                out.print(e + ARRAY_ELEMENTS_SEPARATOR);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
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

    public void saveJson(File textFile) {
        try (Writer writer = new FileWriter(textFile)) {
            Gson gson = new Gson();
            Basket newBasket = new Basket(prices, products, quantity);
            gson.toJson(newBasket, writer);
            System.out.println("Ваш список сохранен в файле ");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromJsonFile(File textFile) {
        Basket oldBasket = null;
        try (FileReader reader = new FileReader(textFile)) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            oldBasket = gson.fromJson(reader, Basket.class);
            for (int i = 0; i < oldBasket.getQuantity().length; i++) {
                total += oldBasket.getPrices()[i] * oldBasket.getQuantity()[i];
            }
            System.out.println("Корзина загружена");
        } catch (Exception e) {
            System.out.println("Файл не найден");
        }
        return oldBasket;
    }

}