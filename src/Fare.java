/**
 * Тариф. Общее (базовая цена, алгоритм расчёта цены и возврата) лежит здесь,
 * различия (множитель цены, правила возврата и обмена) реализуют наследники.
 */
public abstract class Fare {

    private final String name;
    private final double basePrice;   // цена за базовый (эконом) перелёт

    protected Fare(String name, double basePrice) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название тарифа не может быть пустым");
        }
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Базовая цена должна быть больше нуля: " + basePrice);
        }
        this.name = name.trim();
        this.basePrice = basePrice;
    }

    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    // ---------- Различия: обязаны реализовать наследники ----------

    // Во сколько раз тариф дороже базовой цены
    protected abstract double getPriceMultiplier();

    // Какой процент стоимости возвращается, если отказ за daysBefore дней до вылета
    protected abstract double refundPercent(int daysBefore);

    // Сколько стоит обмен билета за daysBefore дней до вылета
    protected abstract double exchangeFee(int daysBefore);

    // ---------- Общий алгоритм: final, наследники его менять не могут ----------

    /**
     Итоговая цена = цена тарифа + доплата за багаж по правилу рейса.
     final: расчёт цены один для всех тарифов, иначе итог зависел бы от прихоти наследника.
     */
    public final double calculatePrice(Flight flight, double baggageKg) {
        if (flight == null) {
            throw new IllegalArgumentException("Рейс не задан");
        }
        return basePrice * getPriceMultiplier() + flight.getBaggageSurcharge(baggageKg);
    }

    // Сколько денег вернут клиенту. final: проверка данных одна для всех
    public final double refundAmount(double paid, int daysBefore) {
        checkPaidAndDays(paid, daysBefore);
        return paid * refundPercent(daysBefore) / 100;
    }

    // Стоимость обмена. final по той же причине
    public final double exchangeCost(double paid, int daysBefore) {
        checkPaidAndDays(paid, daysBefore);
        return exchangeFee(daysBefore);
    }

    private void checkPaidAndDays(double paid, int daysBefore) {
        if (paid < 0) {
            throw new IllegalArgumentException("Оплаченная сумма не может быть отрицательной: " + paid);
        }
        if (daysBefore < 0) {
            throw new IllegalArgumentException("Число дней до вылета не может быть отрицательным: " + daysBefore);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
