// Эконом: дёшево, возврат с удержанием, обмен платный
public class EconomyFare extends Fare {

    public EconomyFare(double basePrice) {
        super("Эконом", basePrice);
    }

    @Override
    protected double getPriceMultiplier() {
        return 1.0;
    }

    @Override
    protected double refundPercent(int daysBefore) {
        return daysBefore >= 3 ? 70 : 30;
    }

    @Override
    protected double exchangeFee(int daysBefore) {
        return 2000;
    }
}
