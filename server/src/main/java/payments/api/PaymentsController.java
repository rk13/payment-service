package payments.api;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import payments.model.PaymentsInfo;
import payments.model.PaymentsRequest;
import payments.services.PaymentsService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Singleton
@Path("/payments")
@Produces(MediaType.APPLICATION_JSON)
public class PaymentsController implements PaymentsSpecification {

    private final PaymentsService service;

    @Inject
    public PaymentsController(PaymentsService cardResourcesService) {
        this.service = cardResourcesService;
    }

    @POST
    @UnitOfWork
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response makePayment(@Valid PaymentsRequest request) {
        PaymentsInfo payment = service.makePayment(request);
        return Response.status(Response.Status.CREATED)
                .entity(payment)
                .build();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    @Override
    public Response paymentInfo(@PathParam("id") String paymentId) {
        Optional<PaymentsInfo> payment = service.findPayment(paymentId);
        return payment
                .map(p -> Response.ok()
                        .entity(p)
                        .build())
                .orElse(Response.noContent()
                        .build());
    }
}
