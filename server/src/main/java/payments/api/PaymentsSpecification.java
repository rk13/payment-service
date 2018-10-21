package payments.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import payments.model.PaymentsInfo;
import payments.model.PaymentsRequest;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

@Tag(name = "Payments", description = "Money transfer services")
public interface PaymentsSpecification {
    @Operation(
            summary = "Money transfer between accounts",
            description = "Executes immediately (or schedules for execution) requested payment operation",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Successful payment request",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = PaymentsInfo.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Not allowed to make money transfer between these accounts"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Invalid request supplied (wth detailed errors)"
                    )
            }
    )
    Response makePayment(
            @RequestBody(description = "Payment information", required = true)
            @Valid PaymentsRequest request);


    @Operation(
            summary = "Money transfer information",
            description = "Returns payment info",
            responses = {
                    @ApiResponse(
                            description = "Successful request",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = PaymentsInfo.class)
                            )
                    ),
                    @ApiResponse(
                            description = "No requested payment info available",
                            responseCode = "204"
                    )
            }
    )
    Response paymentInfo(
            @Parameter(description = "Payment Id", example = "1", required = true) String paymentId);
}
