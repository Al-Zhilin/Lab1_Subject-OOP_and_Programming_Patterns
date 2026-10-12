/**
 * Тариф, собранный из частей (композиция вместо наследования): класс обслуживания,
 * правило возврата и правило обмена приходят извне и меняются независимо друг от друга.
 */
public class ComposedFare extends Fare {

    private final ServiceClass serviceClass;
    private final RefundRule refundRule;
    private final ExchangeRule exchangeRule;

    public ComposedFare(String name, double basePrice, ServiceClass serviceClass,
                        RefundRule refundRule, ExchangeRule exchangeRule) {
        super(name, basePrice);
        if (serviceClass == null || refundRule == null || exchangeRule == null) {
            throw new IllegalArgumentException("Класс обслуживания и правила не могут быть null");
        }
        this.serviceClass = serviceClass;
        this.refundRule = refundRule;
        this.exchangeRule = exchangeRule;
    }

    @Override
    protected double getPriceMultiplier() {
        return serviceClass.getPriceMultiplier();
    }

    @Override
    protected double refundPercent(int daysBefore) {
        return refundRule.refundPercent(daysBefore);
    }

    @Override
    protected double exchangeFee(int daysBefore) {
        return exchangeRule.exchangeFee(getBasePrice() * getPriceMultiplier(), daysBefore);
    }
}
