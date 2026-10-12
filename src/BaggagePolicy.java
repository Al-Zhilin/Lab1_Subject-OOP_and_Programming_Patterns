// интерфейс политики оплаты багажа
public interface BaggagePolicy {

    // расчет доплаты за багаж
    double surcharge(double weightKg);
}
