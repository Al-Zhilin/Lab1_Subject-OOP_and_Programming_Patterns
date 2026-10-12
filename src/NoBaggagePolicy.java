// Багаж платиться полностью (не включен в билет)
public class NoBaggagePolicy implements BaggagePolicy {

    private static final double PRICE_PER_KG = 600;

    @Override
    public double surcharge(double weightKg) {
        return weightKg * PRICE_PER_KG;
    }
}
