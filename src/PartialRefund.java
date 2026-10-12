/** Частичный возврат: процент зависит от того, далеко ли вылет. */
public class PartialRefund implements RefundRule {

    private final int thresholdDays;
    private final double farPercent;    // если до вылета не меньше thresholdDays
    private final double nearPercent;   // если ближе

    public PartialRefund(int thresholdDays, double farPercent, double nearPercent) {
        if (thresholdDays < 0) {
            throw new IllegalArgumentException("Порог дней не может быть отрицательным: " + thresholdDays);
        }
        if (farPercent < 0 || farPercent > 100 || nearPercent < 0 || nearPercent > 100) {
            throw new IllegalArgumentException("Процент возврата должен быть от 0 до 100");
        }
        this.thresholdDays = thresholdDays;
        this.farPercent = farPercent;
        this.nearPercent = nearPercent;
    }

    @Override
    public double refundPercent(int daysBefore) {
        return daysBefore >= thresholdDays ? farPercent : nearPercent;
    }
}
