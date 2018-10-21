package payments.health;

import payments.PaymentsConfiguration;
import ru.vyarus.dropwizard.guice.module.installer.feature.health.NamedHealthCheck;

import javax.inject.Inject;

public class NoopHealthCheck extends NamedHealthCheck {
    private final String template;

    @Inject
    public NoopHealthCheck(PaymentsConfiguration configuration) {
        this.template = configuration.healthCheck;
    }

    @Override
    protected Result check() {
        if (!template.contains("OK")) {
            return Result.unhealthy("NOK");
        }
        return Result.healthy();
    }

    @Override
    public String getName() {
        return "payments";
    }
}
