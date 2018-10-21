package payments.services;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import payments.ed.Account;
import payments.ed.PaymentOrder;
import payments.ed.Transaction;
import payments.exceptions.ValidationException;
import payments.model.PaymentsInfo;
import payments.model.PaymentsRequest;

import javax.ws.rs.NotAuthorizedException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PaymentsServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    private PaymentsService subject;

    @Mock
    private PaymentOrderRepository orderRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private PaymentsConverter converter;

    @Spy
    private PaymentValidation validation;

    @Test
    public void makePayment_illegalAccount_fail() {
        thrown.expect(NotAuthorizedException.class);

        when(accountRepository.findByIban("illegal")).thenReturn(Optional.empty());

        subject.makePayment(illegalRequest());

        verifyZeroInteractions(orderRepository);
        verifyZeroInteractions(transactionRepository);
        verifyZeroInteractions(converter);
    }

    @Test
    public void makePayment_foreignCreditAccount_pending() {
        PaymentsRequest request = validRequest();
        PaymentOrder order = order();

        when(accountRepository.findByIban("valid1")).thenReturn(Optional.of(account1()));
        when(orderRepository.store(order)).thenReturn(order);

        when(converter.toOrder(any(PaymentsRequest.class), any(Account.class))).thenReturn(order);
        when(converter.toPaymentInfo(any(PaymentOrder.class))).thenCallRealMethod();

        subject.makePayment(request);

        assertEquals(PaymentOrder.Status.PENDING, order.getStatus());
        verify(orderRepository, times(1)).store(order);
        verifyZeroInteractions(transactionRepository);
    }

    @Test
    public void makePayment_ourCreditAccount_fundsTransferred() {
        PaymentsRequest request = validRequest();
        PaymentOrder order = order();

        when(accountRepository.findByIban("valid1")).thenReturn(Optional.of(account1()));
        when(accountRepository.findByIban("valid2")).thenReturn(Optional.of(account2()));
        when(orderRepository.store(order)).thenReturn(order);

        when(converter.toOrder(any(PaymentsRequest.class), any(Account.class))).thenReturn(order);
        when(converter.toPaymentInfo(any(PaymentOrder.class))).thenCallRealMethod();

        subject.makePayment(request);

        assertEquals(PaymentOrder.Status.EXECUTED, order.getStatus());
        verify(orderRepository, times(2)).store(order);
        verify(transactionRepository, times(2)).store(any(Transaction.class));
    }

    @Test
    public void makePayment_sameCreditorDebtor() {
        thrown.expect(ValidationException.class);

        PaymentsRequest request = new PaymentsRequest();
        request.setDebtorAccount("valid1");
        request.setCreditorAccount("valid1");

        subject.makePayment(request);
    }

    @Test
    public void findPayment_existing() {
        when(orderRepository.findById("existing")).thenReturn(Optional.of(order()));
        when(converter.toPaymentInfo(any(PaymentOrder.class))).thenCallRealMethod();

        Optional<PaymentsInfo> actual = subject.findPayment("existing");
        assertTrue(actual.isPresent());
    }

    @Test
    public void findPayment_notexisting() {
        when(orderRepository.findById("notexisting")).thenReturn(Optional.empty());

        Optional<PaymentsInfo> actual = subject.findPayment("notexisting");
        assertFalse(actual.isPresent());
    }

    private PaymentOrder order() {
        return PaymentOrder.builder()
                .debtorAccount(account1())
                .creditorAccount(account2().getIban())
                .status(PaymentOrder.Status.PENDING)
                .build();
    }

    private PaymentsRequest validRequest() {
        PaymentsRequest request = new PaymentsRequest();
        request.setDebtorAccount("valid1");
        request.setCreditorAccount("valid2");
        return request;
    }

    private PaymentsRequest illegalRequest() {
        PaymentsRequest request = new PaymentsRequest();
        request.setDebtorAccount("illegal");
        return request;
    }

    private Account account1() {
        return Account.builder()
                .iban("valid1")
                .owner("owner")
                .build();
    }

    private Account account2() {
        return Account.builder()
                .iban("valid2")
                .owner("owner")
                .build();
    }
}
