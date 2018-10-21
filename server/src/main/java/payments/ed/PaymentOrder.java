package payments.ed;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Currency;

@Entity
@Table(name = "ORDERS")
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
@Setter
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @JoinColumn(name = "debtoraccount")
    private Account debtorAccount;

    private Status status;
    private BigDecimal amount;
    private Currency currency;
    private String creditorName;
    private String creditorAccount;
    private String details;

    public enum Status {
        PENDING,
        EXECUTED,
        REJECTED
    }
}