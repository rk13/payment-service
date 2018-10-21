package payments.services;

import payments.ed.Account;
import payments.ed.PaymentOrder;
import payments.model.PaymentsAmount;
import payments.model.PaymentsInfo;
import payments.model.PaymentsRequest;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

@Singleton
class PaymentsConverter {

    PaymentsInfo toPaymentInfo(PaymentOrder order) {
        return PaymentsInfo.builder()
                .paymentId(order.getId())
                .creditorAccount(order.getCreditorAccount())
                .creditorName(order.getCreditorName())
                .details(order.getDetails())
                .status(Optional.ofNullable(order.getStatus())
                        .map(Enum::toString)
                        .orElse(""))
                .debtorAccount(Optional.ofNullable(order.getDebtorAccount())
                        .map(Account::getIban)
                        .orElse(""))
                .debtorName(Optional.ofNullable(order.getDebtorAccount())
                        .map(Account::getOwner)
                        .orElse(""))
                .paymentAmount(
                        PaymentsAmount.of(
                                Optional.ofNullable(order.getCurrency())
                                        .map(Currency::toString)
                                        .orElse(""),
                                Optional.ofNullable(order.getAmount())
                                        .map(BigDecimal::toPlainString)
                                        .orElse("0.0")))
                .build();
    }

    PaymentOrder toOrder(PaymentsRequest request, Account fromAccount) {
        return PaymentOrder.builder()
                .status(PaymentOrder.Status.PENDING)
                .creditorName(request.getCreditorName())
                .creditorAccount(request.getCreditorAccount())
                .debtorAccount(fromAccount)
                .details(request.getDetails())
                .amount(Optional.ofNullable(request.getPaymentAmount())
                        .map(PaymentsAmount::getAmount)
                        .map(BigDecimal::new)
                        .orElse(BigDecimal.ZERO))
                .currency(Optional.ofNullable(request.getPaymentAmount())
                        .map(PaymentsAmount::getCurrency)
                        .map(Currency::getInstance)
                        .orElse(Currency.getInstance("EUR")))
                .build();
    }
}
