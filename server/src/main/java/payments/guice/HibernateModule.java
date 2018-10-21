package payments.guice;

import com.google.inject.AbstractModule;
import org.hibernate.SessionFactory;

public class HibernateModule extends AbstractModule {

    private final HibernateBundle hibernateBundle;

    public HibernateModule(HibernateBundle hibernateBundle) {
        this.hibernateBundle = hibernateBundle;
    }

    @Override
    protected void configure() {
        bind(SessionFactory.class).toInstance(hibernateBundle.getSessionFactory());
    }
}

