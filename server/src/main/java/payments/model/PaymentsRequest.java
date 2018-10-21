package payments.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;

@Data
@Schema(description = "Payment request")
public class PaymentsRequest {
    @NotEmpty
    @Length(max = 70)
    @Schema(description = "Payment debtor name", example = "Vladimir Kotov")
    private String debtorName;

    @NotEmpty
    @Length(max = 34)
    @Schema(description = "Payment account IBAN", example = "LV80BANK0000435195001")
    private String debtorAccount;

    @Valid
    @Schema(description = "Payment amount")
    private PaymentsAmount paymentAmount;

    @NotEmpty
    @Length(max = 70)
    @Schema(description = "Payment creditor name", example = "Jegor Kotov")
    private String creditorName;

    @NotEmpty
    @Length(max = 34)
    @Schema(description = "Payment credtor IBAN", example = "LV81BANK0000435195002")
    private String creditorAccount;

    @NotEmpty
    @Length(max = 120)
    @Schema(description = "Payment details", example = "For cookies")
    private String details;
}
