package payments.ed;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "TRANSACTIONS")
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(value = AccessLevel.PACKAGE)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @JoinColumn(name = "account")
    private Account account;

    private DebetOrCredit debetOrCredit;
    private Type type;
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "origin")
    private PaymentOrder origin;

    public enum Type {
        COMMISSION,
        TRANSFER
    }

    public enum DebetOrCredit {
        DEBET,
        CREDIT
    }
}
