package payments.test.glue.steps;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.junit.Assert;
import payments.test.api.Payments;
import payments.test.api.req.PaymentsAmount;
import payments.test.api.req.PaymentsRequest;
import payments.test.api.res.PaymentsInfo;
import payments.test.api.res.Result;

public class PaymentSteps implements En {

    private Payments api =
            new Payments("http://localhost:8080");

    public PaymentSteps() {
        Given("^service has active customer account \"([^\"]*)\"$", (String arg0) -> {
        });

        Then("^service responds with ([^\"]*)$", (Result result) -> {
            api.getLastResponse()
                    .then()
                    .statusCode(result.code);
        });

        When("^transfer ([^\"]*) ([^\"]*) from ([^\"]*)/([^\"]*) to ([^\"]*)/([^\"]*) with \"([^\"]*)\"$",
                (String amount, String ccy, String debtor, String debtorAccount, String creditor,String creditorAccount, String details) -> {
            PaymentsRequest req =
                    PaymentsRequest.builder()
                            .paymentAmount(
                                    PaymentsAmount.builder()
                                            .currency(ccy)
                                            .amount(amount)
                                            .build())
                            .debtorName(debtor)
                            .debtorAccount(debtorAccount)
                            .creditorName(creditor)
                            .creditorAccount(creditorAccount)
                            .details(details)
                            .build();

            api.requestPayment(req);
        });

        And("^payment order status is ([^\"]*)", (String status) -> {
            PaymentsInfo info = api.getLastResponse().as(PaymentsInfo.class);
            Assert.assertEquals(info.getStatus(), status);
        });
    }
}
