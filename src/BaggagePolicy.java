/** Правило расчёта доплаты за багаж. */
public interface BaggagePolicy {

    /** Доплата в рублях за багаж указанного веса (в кг). */
    double surcharge(double weightKg);
}
