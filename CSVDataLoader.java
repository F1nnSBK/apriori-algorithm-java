import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class CSVDataLoader {

    private String filePath;
    private String delimiter;
    private String encoding;
    private List<Set<Item>> transactionList;

    public CSVDataLoader(String filePath, String delimiter, String encoding) {
        this.filePath = filePath;
        this.delimiter = delimiter;
        this.encoding = encoding;
        this.transactionList = new ArrayList<>();
    }

    public List<Set<Item>> loadTransactions() {
        try {
            File csvFile = new File(filePath);
            Scanner reader = new Scanner(csvFile);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] items = line.split(delimiter);

                Set<Item> itemSet = new HashSet<>();
                for (String itemName : items) {
                    Item item = new Item(itemName.trim());
                    itemSet.add(item);
                }
                transactionList.add(itemSet);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Datei konnte nicht gefunden werden.");
        }
        return transactionList;
    }

    public List<Set<Item>> getTransactionList() {
        return transactionList;
    }

    public void printTransactions() {
        for (Set<Item> transaction : transactionList) {
            System.out.println(transaction);
        }
    }
}
