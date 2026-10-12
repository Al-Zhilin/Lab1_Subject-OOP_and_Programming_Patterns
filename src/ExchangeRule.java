// Правило обмена билета
public interface ExchangeRule {

    // Стоимость обмена билета, цена которого price, за daysBefore дней до вылета
    double exchangeFee(double price, int daysBefore);
}
