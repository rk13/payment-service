package payments.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Collections;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException>
{
    @Override
    public Response toResponse(ValidationException exception)
    {
        return Response.status(422)
                .entity(Collections.singletonMap("errors", exception.getErrors()))
                .build();
    }
}