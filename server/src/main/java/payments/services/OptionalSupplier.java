package payments.services;

import javax.persistence.NoResultException;
import java.util.Optional;
import java.util.function.Supplier;

interface OptionalSupplier {
    static <T> Optional<T> opt(Supplier<T> f) {
        try {
            return Optional.ofNullable(f.get());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
