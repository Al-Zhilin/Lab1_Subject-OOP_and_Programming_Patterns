/** Багаж не включён в билет: платится весь вес. */
public class NoBaggagePolicy implements BaggagePolicy {

    private static final double PRICE_PER_KG = 600;

    @Override
    public double surcharge(double weightKg) {
        return weightKg * PRICE_PER_KG;
    }
}
