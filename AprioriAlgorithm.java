import java.util.*;

public class AprioriAlgorithm {

    private double minSupport;
    private List<Set<Item>> transactions;

    public AprioriAlgorithm(double minSupport, List<Set<Item>> transactions) {
        this.minSupport = minSupport;
        this.transactions = transactions;
    }

    public List<FrequentItemset> run() {
        List<Set<Item>> frequentItemsets = new ArrayList<>();
        List<Set<Item>> currentItemsets = new ArrayList<>();

        // Initialisiere mit häufigen 1-Itemsets
        for (Set<Item> transaction : transactions) {
            for (Item item : transaction) {
                Set<Item> itemset = new HashSet<>();
                itemset.add(item);
                if (!currentItemsets.contains(itemset)) {
                    currentItemsets.add(itemset);
                }
            }
        }
        try {
            currentItemsets = pruneItemsets(currentItemsets, minSupport);
        } catch (AprioriException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        frequentItemsets.addAll(currentItemsets);

        while (!currentItemsets.isEmpty()) {
            List<Set<Item>> candidateItemsets = generateCandidateItemsets(
                currentItemsets
            );
            try {
                candidateItemsets = pruneItemsets(
                    candidateItemsets,
                    minSupport
                );
            } catch (AprioriException e) {
                System.out.println(e.getMessage());
                break;
            }

            frequentItemsets.addAll(candidateItemsets);
            currentItemsets = candidateItemsets;
        }

        List<FrequentItemset> result = new ArrayList<>();
        for (Set<Item> itemset : frequentItemsets) {
            result.add(new FrequentItemset(itemset, calculateSupport(itemset)));
        }

        return result;
    }

    private List<Set<Item>> generateCandidateItemsets(
        List<Set<Item>> itemsets
    ) {
        List<Set<Item>> candidateItemsets = new ArrayList<>();
        for (int i = 0; i < itemsets.size(); i++) {
            for (int j = i + 1; j < itemsets.size(); j++) {
                Set<Item> itemset1 = itemsets.get(i);
                Set<Item> itemset2 = itemsets.get(j);

                Set<Item> union = new HashSet<>(itemset1);
                union.addAll(itemset2);

                if (
                    union.size() == itemset1.size() + 1 &&
                    !candidateItemsets.contains(union)
                ) {
                    candidateItemsets.add(union);
                }
            }
        }
        return candidateItemsets;
    }

    private List<Set<Item>> pruneItemsets(
        List<Set<Item>> itemsets,
        double minSupport
    ) throws AprioriException {
        List<Set<Item>> frequentItemsets = new ArrayList<>();
        for (Set<Item> itemset : itemsets) {
            if (calculateSupport(itemset) >= minSupport) {
                frequentItemsets.add(itemset);
            }
        }
        if (frequentItemsets.isEmpty() && !itemsets.isEmpty()) {
            throw new AprioriException("Keine häufigen Itemsets gefunden.");
        }
        return frequentItemsets;
    }

    private double calculateSupport(Set<Item> itemset) {
        int count = 0;
        for (Set<Item> transaction : transactions) {
            if (transaction.containsAll(itemset)) {
                count++;
            }
        }
        return (double) count / transactions.size();
    }
}
