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

    private static final Logger LOGGER = Logger.getLogger("Server_Logger");
    private final String SERVER_LOGGER_SEVERE_FILE = "Server_Logger_Severe.log";
    private final String SERVER_LOGGER_WARNING_FILE = "Server_Logger_Warning.log";
    private final String SERVER_LOGGER_INFO_FILE = "Server_Logger_Info.log";

    private FileHandler fh;

    /**
     * Error response returned based on exception
     *
     * @param ex exception
     * @return error response
     */
    @Override
    public Response toResponse(Throwable ex) {

        if (ex instanceof AppRuntimeException) {
            switch (ErrorMessageEnum.valueOf(ex.getMessage())) {
                case INVALID_CONTENT:
                    logErrorMsg(ex, Level.INFO);
                    return Response.status(Response.Status.BAD_REQUEST).build();
                case CONTENT_PRESENT:
                    logErrorMsg(ex, Level.INFO);
                    return Response.status(Response.Status.CONFLICT).build();
                case USERNAME_PRESENT:
                    logErrorMsg(ex, Level.INFO);
                    return Response.status(Response.Status.CONFLICT)
                            .entity(errorJsonWithCodeString(ErrorMessageConstants.USERNAME_PRESENT_CODE))
                            .type(MediaType.APPLICATION_JSON)
                            .build();
                case SSN_PRESENT:
                    logErrorMsg(ex, Level.INFO);
                    return Response.status(Response.Status.CONFLICT)
                            .entity(errorJsonWithCodeString(ErrorMessageConstants.SSN_PRESENT_CODE))
                            .type(MediaType.APPLICATION_JSON)
                            .build();
                case NO_CONTENT:
                    logErrorMsg(ex, Level.WARNING);
                    return Response.status(Response.Status.NO_CONTENT).build();
                case NO_DB_CONNECTION:
                    logErrorMsg(ex, Level.SEVERE);
                    return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
                case OPERTAION_FAILED:
                    logErrorMsg(ex, Level.WARNING);
                    return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
                default:
                    logErrorMsg(ex, Level.SEVERE);
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        } else {

            logErrorMsg(ex, Level.SEVERE);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    private String errorJsonWithCodeString(int code) {
        return "{\"error\":" + code + "}";
    }

    private void logErrorMsg(Throwable ex, Level logLvl) {
        try {
            String file_name;
            switch (logLvl.getName()) {
                case "SEVERE":
                    file_name = SERVER_LOGGER_SEVERE_FILE;
                    break;
                case "WARNING":
                    file_name = SERVER_LOGGER_WARNING_FILE;
                    break;
                case "INFO":
                    file_name = SERVER_LOGGER_INFO_FILE;
                    break;
                default:
                    file_name = SERVER_LOGGER_SEVERE_FILE;
                    break;

            }
            fh = new FileHandler(file_name, true);
            LOGGER.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            LOGGER.log(logLvl, ex.getMessage(), ex);

            fh.close();
        } catch (SecurityException | IOException e) {
            Logger.getLogger(AppExceptionMapper.class.getName()).log(Level.SEVERE, null, e);

        }
    }
}
