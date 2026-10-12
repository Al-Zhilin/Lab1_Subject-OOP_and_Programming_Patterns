/** Полный возврат, если отказ не в день вылета; в день вылета вернут половину. */
public class FullRefund implements RefundRule {

    @Override
    public double refundPercent(int daysBefore) {
        return daysBefore >= 1 ? 100 : 50;
    }
}
