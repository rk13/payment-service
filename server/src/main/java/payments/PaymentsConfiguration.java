package payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.bundles.assets.AssetsBundleConfiguration;
import io.dropwizard.bundles.assets.AssetsConfiguration;
import io.dropwizard.db.DataSourceFactory;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@OpenAPIDefinition(
        info = @Info(
                title = "Payments",
                version = "1.0.0",
                description = "Banking as you wished it would be"
        ),
        servers = {
                @Server(
                        description = "Locale development environment",
                        url = "http://localhost:8080/"
                )
        }
)
public class PaymentsConfiguration extends Configuration implements AssetsBundleConfiguration {
    @Valid
    @NotNull
    @JsonProperty
    private final AssetsConfiguration assets = AssetsConfiguration.builder().build();

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty
    public String healthCheck;

    @Override
    public AssetsConfiguration getAssetsConfiguration() {
        return assets;
    }
}