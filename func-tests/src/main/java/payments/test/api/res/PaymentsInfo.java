package payments.test.api.res;

import lombok.Data;
import lombok.NoArgsConstructor;
import payments.test.api.req.PaymentsAmount;

@Data
public class PaymentsInfo {
    private String paymentId;
    private String status;
    private String debtorName;
    private String debtorAccount;
    private PaymentsAmount paymentAmount;
    private PaymentsAmount commissionAmount;
    private String creditorName;
    private String creditorAccount;
    private String details;
}