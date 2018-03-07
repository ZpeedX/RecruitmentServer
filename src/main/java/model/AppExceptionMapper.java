/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Intercepts all throwable exceptions not already handled and returns a response
 * to the client depending on the occured error.
 *
 * @author Oscar
 */
@Provider
public class AppExceptionMapper implements ExceptionMapper<Throwable> {

    static final Logger LOGGER = Logger.getLogger("Server_Logger");
    FileHandler fh;

    /**
     * Error response returned based on exception
     *
     * @param ex exception
     * @return error response
     */
    @Override
    public Response toResponse(Throwable ex) {
        logErrorMsg(ex);
        System.out.println("MAPPER TRIGGERED: ");

        if (ex instanceof AppRuntimeException) {
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
    
    /**
     * Converts error code to json format
     * @param code given error code
     * @return json string
     */
    private String errorJsonWithCodeString(int code) {
        return "{\"error\":" + code + "}";
    }
    
    /**
     * Logs the given throwble exception into a file
     * @param ex given throwable exception
     */
    private void logErrorMsg(Throwable ex) {
        try {
            // This block configure the logger with handler and formatter  
            fh = new FileHandler("Server_Logger.xml", true);
            LOGGER.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages  
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            fh.close();
        } catch (SecurityException | IOException e) {
            Logger.getLogger(AppExceptionMapper.class.getName()).log(Level.SEVERE, null, e);

        }
    }
}
