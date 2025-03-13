import java.util.Comparator;

public class SupportComparator implements Comparator<FrequentItemset> {

    public int compare(FrequentItemset itemset1, FrequentItemset itemset2) {
        if (itemset1 == itemset2) {
            return 0;
        }

        double support1 = itemset1.getSupport();
        double support2 = itemset2.getSupport();

        if (support1 < support2) {
            return -1;
        } else if (support1 > support2) {
            return 1;
        } else {
            return 0;
        }
    }
}
