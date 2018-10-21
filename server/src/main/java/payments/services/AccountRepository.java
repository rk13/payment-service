package payments.services;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import payments.ed.Account;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

import static payments.services.OptionalSupplier.opt;

public class AccountRepository extends AbstractDAO<Account> {

    @Inject
    public AccountRepository(SessionFactory factory) {
        super(factory);
    }

    Optional<Account> findByIban(String iban) {
        CriteriaBuilder qb = currentSession().getCriteriaBuilder();
        CriteriaQuery<Account> cq = qb.createQuery(Account.class);
        Root<Account> p = cq.from(Account.class);
        cq.where(qb.equal(p.get("iban"), iban));

        return opt(() ->
                currentSession().createQuery(cq).getSingleResult());
    }
}