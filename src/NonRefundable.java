/** Возврата нет. */
public class NonRefundable implements RefundRule {

    @Override
    public double refundPercent(int daysBefore) {
        return 0;
    }
}
