package com.spotlight.platform.userprofile.api.web.exceptionmappers;

import com.spotlight.platform.userprofile.api.core.exceptions.EntityNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Exception mapper for handling EntityNotFoundException in the web layer.
 * Converts the exception into an HTTP response with status code 404 (Not Found).
 */
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException> {

    /**
     * Converts the EntityNotFoundException into an HTTP response with status code 404 (Not Found).
     *
     * @param exception The EntityNotFoundException to be handled.
     * @return A Response object with status code 404 (Not Found).
     */
    @Override
    public Response toResponse(EntityNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
