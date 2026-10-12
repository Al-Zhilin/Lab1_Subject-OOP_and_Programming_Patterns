/** Одно место багажа до 23 кг включено, перевес оплачивается. */
public class OneBagPolicy implements BaggagePolicy {

    private static final double FREE_KG = 23;
    private static final double PRICE_PER_KG = 600;

    @Override
    public double surcharge(double weightKg) {
        if (weightKg <= FREE_KG) {
            return 0;
        }
        return (weightKg - FREE_KG) * PRICE_PER_KG;
    }
}
