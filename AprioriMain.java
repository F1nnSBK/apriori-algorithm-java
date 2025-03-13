import java.util.List;
import java.util.Set;

public class AprioriMain {

    public static void main(String[] args) {
        // Konfigurationen
        String filePath = "data/marketing.csv"; // Pfad zur CSV-Datei
        String delimiter = ","; // Trennzeichen in der CSV-Datei
        String encoding = "UTF-8"; // Zeichenkodierung der CSV-Datei
        double minSupport = 0.4; // Minimaler Support-Wert

        CSVDataLoader dataLoader = new CSVDataLoader(
            filePath,
            delimiter,
            encoding
        );
        List<Set<Item>> transactions = dataLoader.loadTransactions();

        AprioriAlgorithm apriori = new AprioriAlgorithm(
            minSupport,
            transactions
        );
        List<FrequentItemset> frequentItemsets = apriori.run();

        System.out.println("Häufige Itemsets:");
        for (FrequentItemset itemset : frequentItemsets) {
            System.out.println(
                itemset.getItems() + " (Support: " + itemset.getSupport() + ")"
            );
        }
    }
}
