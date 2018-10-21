package payments.guice;

import io.dropwizard.db.PooledDataSourceFactory;
import payments.PaymentsConfiguration;
import payments.ed.Account;
import payments.ed.PaymentOrder;
import payments.ed.Transaction;

public class HibernateBundle extends io.dropwizard.hibernate.HibernateBundle<PaymentsConfiguration> {

    public HibernateBundle() {
        super(Account.class, Transaction.class, PaymentOrder.class);
    }

    @Override
    public PooledDataSourceFactory getDataSourceFactory(PaymentsConfiguration configuration) {
        return configuration.getDataSourceFactory();
    }
}