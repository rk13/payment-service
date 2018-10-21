package payments.ed;

import lombok.*;

import javax.persistence.*;
import java.util.Currency;

@Entity
@Table(name = "ACCOUNTS")
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
@Setter(value = AccessLevel.PACKAGE)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String iban;
    private Currency currency;
    private String owner;
}
