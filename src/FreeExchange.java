// Обмен бесплатный
public class FreeExchange implements ExchangeRule {

    @Override
    public double exchangeFee(double price, int daysBefore) {
        return 0;
    }
}
