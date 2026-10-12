/** Правило возврата билета. */
public interface RefundRule {

    /** Процент стоимости, который вернут при отказе за daysBefore дней до вылета. */
    double refundPercent(int daysBefore);
}
