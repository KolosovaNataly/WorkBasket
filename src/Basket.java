import java.io.*;

public class Basket implements Serializable {
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
                str.append(write).append("\n");
                System.out.println(write);
            }
        }
        String write = "Итого: " + total + " руб";
        str.append(write);
        System.out.println(write);
    }

    public void saveBin(File file) {
        try (ObjectOutputStream outBin = new ObjectOutputStream(new FileOutputStream(file))) {
            outBin.writeUTF(str.toString());
            System.out.println("Ваш список сохранен в файле ");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Basket loadFromBinFile(File file) throws IOException {
        ObjectInputStream inputBin = new ObjectInputStream(new FileInputStream(file));
        String[] textBasket = inputBin.readUTF().split(" в сумме\n ");
        String[] basketParts;
        for (int i = 0; i < textBasket.length; i++) {
            basketParts = textBasket[i].split(" ");
            for (int j = 0; j < Main.products.length; j++) {
                String a = basketParts[0];
                if (Main.products[j].contains(a)) {
                    Main.quantity[j] = (Integer.parseInt(basketParts[4]));
                    total += Main.prices[j] * Main.quantity[j];
                }
            }
        }
        return new Basket(Main.prices, Main.products, Main.quantity);
    }

    public static int getTotal() {
        return total;
    }
}

