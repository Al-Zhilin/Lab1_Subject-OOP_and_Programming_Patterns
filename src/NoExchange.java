// Обмена билета нет. Стоимости: как у нового билета
public class NoExchange implements ExchangeRule {

    @Override
    public double exchangeFee(double price, int daysBefore) {
        return price;
    }
}
