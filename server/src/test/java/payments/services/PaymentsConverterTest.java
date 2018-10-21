package payments.services;

import org.junit.Test;
import payments.ed.Account;
import payments.ed.PaymentOrder;
import payments.model.PaymentsAmount;
import payments.model.PaymentsInfo;
import payments.model.PaymentsRequest;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertEquals;

public class PaymentsConverterTest {

    private PaymentsConverter subject = new PaymentsConverter();

    @Test
    public void toPaymentInfo() {
        PaymentOrder order = order();
        PaymentsInfo expected = paymentsInfo();
        PaymentsInfo actual = subject.toPaymentInfo(order);

        assertEquals(expected, actual);
    }

    @Test
    public void toOrder() {
        PaymentsRequest request = request();
        Account account = account();
        PaymentOrder actual = subject.toOrder(request, account);
        PaymentOrder expected = order();

        assertEquals(expected, order());
    }

    private Account account() {
        return Account.builder()
                .owner("DName")
                .iban("453")
                .build();
    }

    private PaymentsRequest request() {
        PaymentsRequest request = new PaymentsRequest();
        request.setPaymentAmount(PaymentsAmount.of("EUR", "10"));
        request.setCreditorName("CName");
        request.setCreditorAccount("123");
        request.setDebtorAccount("456");
        request.setDebtorName("DName");
        request.setDetails("details");
        return request;
    }

    private PaymentsInfo paymentsInfo() {
        return PaymentsInfo.builder()
                    .paymentAmount(PaymentsAmount.of("EUR", "10"))
                    .creditorName("CName")
                    .creditorAccount("123")
                    .debtorAccount("456")
                    .debtorName("DName")
                    .paymentId("1")
                    .details("details")
                    .status("PENDING")
                    .build();
    }

    private PaymentOrder order() {
        return PaymentOrder.builder()
                    .amount(BigDecimal.TEN)
                    .currency(Currency.getInstance("EUR"))
                    .creditorAccount("123")
                    .creditorName("CName")
                    .debtorAccount(Account.builder()
                            .owner("DName")
                            .iban("456")
                            .build())
                    .details("details")
                    .status(PaymentOrder.Status.PENDING)
                    .id("1")
                    .build();
    }
}