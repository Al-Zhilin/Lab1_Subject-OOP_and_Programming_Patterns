/** Чартер: самый дешёвый, возврата нет, обмен слишком дорогой. */
public class CharterFare extends Fare {

    public CharterFare(double basePrice) {
        super("Чартер", basePrice);
    }

    @Override
    protected double getPriceMultiplier() {
        return 0.8;
    }

    @Override
    protected double refundPercent(int daysBefore) {
        return 0;
    }

    @Override
    protected double exchangeFee(int daysBefore) {
        // обмен фактически невозможен: стоит как новый билет
        return getBasePrice() * getPriceMultiplier();
    }
}
