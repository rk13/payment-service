package payments.services;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import payments.ed.PaymentOrder;

import javax.inject.Inject;
import java.util.Optional;

import static payments.services.OptionalSupplier.opt;

public class PaymentOrderRepository extends AbstractDAO<PaymentOrder> {

    @Inject
    public PaymentOrderRepository(SessionFactory factory) {
        super(factory);
    }

    public PaymentOrder store(PaymentOrder order) {
        return persist(order);
    }

    Optional<PaymentOrder> findById(String id) {
        return opt(() -> get(id));
    }
}