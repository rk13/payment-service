package payments.services;

import payments.ed.PaymentOrder;
import payments.ed.Transaction;
import payments.model.PaymentsInfo;
import payments.model.PaymentsRequest;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.NotAuthorizedException;
import java.util.Optional;

@Singleton
public class PaymentsService {
    private final PaymentOrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final PaymentsConverter converter;
    private final PaymentValidation validation;

    @Inject
    public PaymentsService(PaymentOrderRepository orderRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, PaymentsConverter converter, PaymentValidation validation) {
        this.orderRepository = orderRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.converter = converter;
        this.validation = validation;
    }

    public PaymentsInfo makePayment(PaymentsRequest request) {
        validation.check(request);

        return accountRepository
                .findByIban(request.getDebtorAccount())
                .map(account -> converter.toOrder(request, account))
                .map(this::execute)
                .orElseThrow(() ->
                        new NotAuthorizedException(
                                "Operations with [" + request.getDebtorAccount() + "] not allowed"));
    }

    public Optional<PaymentsInfo> findPayment(String id) {
        return orderRepository.findById(id)
                .map(converter::toPaymentInfo);
    }

    private PaymentsInfo execute(PaymentOrder order) {
        return Optional.of(order)
                .map(orderRepository::store)
                // small logic on immediate / deferred payment order processing
                .map(pending ->
                        creditorIsOurAccount(pending)
                                ? transferFunds(pending) : pending)
                .map(converter::toPaymentInfo)
                .orElseThrow(RuntimeException::new);
    }

    private boolean creditorIsOurAccount(PaymentOrder order) {
        return accountRepository.findByIban(
                order.getCreditorAccount()).isPresent();
    }

    private PaymentOrder transferFunds(PaymentOrder order) {
        return Optional.of(order)
                .map(pending -> {
                    order.setStatus(PaymentOrder.Status.EXECUTED);
                    transactionRepository.store(creditTransaction(pending));
                    transactionRepository.store(debitTransaction(pending));
                    return orderRepository.store(pending);
                })
                .orElseThrow(RuntimeException::new);
    }

    private Transaction debitTransaction(PaymentOrder order) {
        return Transaction.builder()
                .account(order.getDebtorAccount())
                .amount(order.getAmount())
                .debetOrCredit(Transaction.DebetOrCredit.DEBET)
                .origin(order)
                .build();
    }

    private Transaction creditTransaction(PaymentOrder order) {
        return Transaction.builder()
                .account(accountRepository.findByIban(order.getCreditorAccount()).get())
                .amount(order.getAmount())
                .debetOrCredit(Transaction.DebetOrCredit.CREDIT)
                .origin(order)
                .build();
    }
}
