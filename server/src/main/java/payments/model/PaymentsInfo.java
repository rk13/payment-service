package payments.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Payment information")
public class PaymentsInfo {
    @Schema(description = "Unique payment identifier generated by the system", example = "550e8400-e29b-41d4-a716-446655440000")
    private String paymentId;

    @Schema(description = "Current payment status", example = "EXECUTED", allowableValues = {"EXECUTED", "PENDING", "REJECTED"})
    private String status;

    @Schema(description = "Payment debtor name", example = "Vladimir Kotov")
    private String debtorName;

    @Schema(description = "Payment account IBAN", example = "LV80BANK0000435195001")
    private String debtorAccount;

    @Schema(description = "Payment amount")
    private PaymentsAmount paymentAmount;

    @Schema(description = "Commission amount")
    private PaymentsAmount commissionAmount;

    @Schema(description = "Payment creditor name", example = "Jegor Kotov")
    private String creditorName;

    @Schema(description = "Payment credtor IBAN", example = "LV81BANK0000435195002")
    private String creditorAccount;

    @Schema(description = "Payment details", example = "For cookies")
    private String details;
}