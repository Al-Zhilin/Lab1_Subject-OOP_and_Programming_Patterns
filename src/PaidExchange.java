/** Обмен за фиксированную плату. */
public class PaidExchange implements ExchangeRule {

    private final double fee;

    public PaidExchange(double fee) {
        if (fee < 0) {
            throw new IllegalArgumentException("Плата за обмен не может быть отрицательной: " + fee);
        }
        this.fee = fee;
    }

    @Override
    public double exchangeFee(double price, int daysBefore) {
        return fee;
    }
}
