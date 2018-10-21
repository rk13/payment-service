package payments.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

@Data
@Schema(description = "Transfer amount")
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class PaymentsAmount {
    @NotEmpty
    @Length(max = 3)
    @Schema(description = "ISO Currency code", example = "EUR")
    private String currency;

    @NotEmpty
    @Pattern(regexp = "\\d+(\\.\\d{1,2})?", message = "wrong amount format")
    @Schema(description = "Amount", example = "10.55")
    private String amount;
}
