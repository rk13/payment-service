package payments;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.bundles.assets.ConfiguredAssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.swagger.v3.jaxrs2.integration.resources.BaseOpenApiResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import payments.guice.HibernateBundle;
import payments.guice.HibernateModule;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class PaymentsApplication extends Application<PaymentsConfiguration> {

    public static void main(String[] args) throws Exception {
        new PaymentsApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<PaymentsConfiguration> bootstrap) {
        bootstrap.addBundle(assets());
        bootstrap.addBundle(hibernate());
        bootstrap.addBundle(guice());
    }

    @Override
    public void run(PaymentsConfiguration configuration, Environment environment) {
        environment.jersey().register(swagger());
    }

    private GuiceBundle<Configuration> guice() {
        return GuiceBundle.builder()
                .enableAutoConfig(getClass().getPackage().getName())
                .modules(new HibernateModule(hibernate))
                .useWebInstallers()
                .searchCommands()
                .noGuiceFilter()
                .build();
    }

    private ConfiguredAssetsBundle assets() {
        return new ConfiguredAssetsBundle();
    }

    private BaseOpenApiResource swagger() {
        return new OpenApiResource()
                .openApiConfiguration(
                        new SwaggerConfiguration()
                                .prettyPrint(true));
    }

    private HibernateBundle hibernate;
    private HibernateBundle hibernate() {
        hibernate = new HibernateBundle();
        return hibernate;
    }
}
