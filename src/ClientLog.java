import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ClientLog {
    private static String[] info;

    public static void log(int productNum, int amount) {
        info = new String[]{String.valueOf(productNum), String.valueOf(amount)};
    }

    public static void exportAsCSV(File txtFile) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile, true))) {
            writer.writeNext(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
