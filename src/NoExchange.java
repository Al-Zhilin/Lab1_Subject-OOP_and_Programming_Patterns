/** Обмена нет: он стоит как новый билет. */
public class NoExchange implements ExchangeRule {

    @Override
    public double exchangeFee(double price, int daysBefore) {
        return price;
    }
}
