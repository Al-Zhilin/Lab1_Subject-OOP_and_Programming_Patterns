// Два места багажа по 23 кг (всего 46 кг) включены, перевес оплачивается
public class TwoBagsPolicy implements BaggagePolicy {

    private static final double FREE_KG = 46;
    private static final double PRICE_PER_KG = 600;

    @Override
    public double surcharge(double weightKg) {
        if (weightKg <= FREE_KG) {
            return 0;
        }
        return (weightKg - FREE_KG) * PRICE_PER_KG;
    }
}
