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
        try (FileOutputStream fos = new FileOutputStream(file.getName()); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
            System.out.println("Ваш список сохранен в файле ");
        } catch (IOException e) {
            e.getMessage();
        }
    }


    public static Basket loadFromBinFile(File file, Basket basket) {
        try (FileInputStream fis = new FileInputStream(file.getName()); ObjectInputStream ois = new ObjectInputStream(fis)) {
            basket = (Basket) ois.readObject();
        } catch (FileNotFoundException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        } catch (ClassNotFoundException e) {
            e.getMessage();
        }
        return basket;
    }

    public static int getTotal() {
        return total;
    }
}

