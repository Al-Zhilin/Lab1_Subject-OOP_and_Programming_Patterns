// Бизнес тариф: дорогой, возврат почти полный, обмен - бесплатный
public class BusinessFare extends Fare {

    public BusinessFare(double basePrice) {
        super("Бизнес", basePrice);
    }

    @Override
    protected double getPriceMultiplier() {
        return 2.5;
    }

    @Override
    protected double refundPercent(int daysBefore) {
        return daysBefore >= 1 ? 100 : 50;
    }

    @Override
    protected double exchangeFee(int daysBefore) {
        return 0;
    }
}
