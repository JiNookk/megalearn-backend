package jinookk.ourlms.dtos;

import java.util.List;

public class MonthlyPaymentsDto {
    private List<MonthlyPaymentDto> monthlyPayments;

    public MonthlyPaymentsDto() {
    }

    public MonthlyPaymentsDto(List<MonthlyPaymentDto> monthlyPayments) {
        this.monthlyPayments = monthlyPayments;
    }

    public List<MonthlyPaymentDto> getMonthlyPayments() {
        return monthlyPayments;
    }
}
