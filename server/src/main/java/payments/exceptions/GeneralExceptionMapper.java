package payments.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GeneralExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable ex) {
        if (ex instanceof WebApplicationException) {
            return ((WebApplicationException) ex).getResponse();
        }
        return Response.serverError().build();
    }
}
