package payments.test.api;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import payments.test.api.req.PaymentsRequest;

import static com.jayway.restassured.RestAssured.given;

public class Payments {

    private Response lastResponse;

    public Payments(String host) {
        RestAssured.baseURI = host;
    }

    public Response requestPayment(PaymentsRequest req) {
        Response res = jsonRequest()
                .content(req)
                .post("payments/");
        lastResponse = res;
        return res;
    }

    private RequestSpecification jsonRequest() {
        return given().contentType("application/json");
    }

    public Response getLastResponse() {
        return lastResponse;
    }
}
