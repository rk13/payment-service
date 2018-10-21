package payments.test.api.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentsRequest {
    private PaymentsAmount paymentAmount;
    private String debtorName;
    private String debtorAccount;
    private String creditorName;
    private String creditorAccount;
    private String details;
}
