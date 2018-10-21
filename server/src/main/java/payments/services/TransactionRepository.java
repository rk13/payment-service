package payments.services;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import payments.ed.Transaction;

import javax.inject.Inject;

public class TransactionRepository extends AbstractDAO<Transaction> {

    @Inject
    public TransactionRepository(SessionFactory factory) {
        super(factory);
    }

    public Transaction store(Transaction txn) {
        return persist(txn);
    }
}