/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Oscar
 */
@Provider
public class AppExceptionMapper implements ExceptionMapper<Throwable> {
    /**
     * Error response returned based on exception
     * @param ex exception
     * @return error response 
     */
    @Override
    public Response toResponse(Throwable ex) {
        System.out.println("MAPPER TRIGGERED: ");

        if(ex instanceof AppRuntimeException) {
            System.out.println("IN IF");
            System.out.println(ex.getMessage());
            switch (ErrorMessageEnum.valueOf(ex.getMessage())) {
                case INVALID_CONTENT:
                    System.out.println("INVALID_CONTENT");
                    return Response.status(Response.Status.BAD_REQUEST).build();
                case CONTENT_PRESENT:
                    System.out.println("CONTENT_PRESENT");
                    return Response.status(Response.Status.CONFLICT).build();
                case USERNAME_PRESENT:
                    System.out.println("USERNAME_PRESENT");
                    return Response.status(Response.Status.CONFLICT)
                            .entity(errorJsonWithCodeString(ErrorMessageConstants.USERNAME_PRESENT_CODE))
                            .type(MediaType.APPLICATION_JSON)
                            .build();
                case SSN_PRESENT:
                    System.out.println("SSN_PRESENT");
                    return Response.status(Response.Status.CONFLICT)
                            .entity(errorJsonWithCodeString(ErrorMessageConstants.SSN_PRESENT_CODE))
                            .type(MediaType.APPLICATION_JSON)
                            .build();
                case NO_CONTENT:
                    System.out.println("NO_CONTENT");
                    return Response.status(Response.Status.NO_CONTENT).build();
                case NO_DB_CONNECTION:
                    System.out.println("NO_DB_CONNECTION");
                    return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
                case OPERTAION_FAILED:
                    System.out.println("OPERTAION_FAILED");
                    return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
                default:
                    System.out.println("DEFUALT");
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            System.out.println("SOMETHING ELSE HAPPEND");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }
    
    private String errorJsonWithCodeString(int code) {
        return "{\"error\":" + code + "}";
    }
    
}
