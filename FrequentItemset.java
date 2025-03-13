import java.util.Set;

public class FrequentItemset {

    private Set<Item> items;
    private double support;

    public FrequentItemset(Set<Item> items, double support) {
        this.items = items;
        this.support = support;
    }

    public Set<Item> getItems() {
        return this.items;
    }

    public double getSupport() {
        return this.support;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrequentItemset that = (FrequentItemset) o;
        return (
            Double.compare(that.support, support) == 0 &&
            items.equals(that.items)
        );
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(support);
        result = 31 * items.hashCode() + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
