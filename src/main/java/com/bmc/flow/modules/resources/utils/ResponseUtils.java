package com.bmc.flow.modules.resources.utils;

import com.bmc.flow.modules.database.postgresql.PgErrorCodes;
import io.vertx.pgclient.PgException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.ws.rs.core.Response;
import lombok.extern.jbosslog.JBossLog;
import org.hibernate.HibernateException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;

/**
 * this class is a set of utilities to handle {@link Response}
 */
@JBossLog
public class ResponseUtils {

    private ResponseUtils() {
    }

    /**
     * handles dtos / entities integrity violations.
     *
     * @param throwable the violation exception
     *
     * @return a {@link Response} wrapping the errors in a map, the map is returned as a k/v json.
     */
    public static Response violationsToResponse(final Throwable throwable) {
        // this is guaranteed to always be a ConstraintViolationException
        Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) throwable).getConstraintViolations();

        Map<String, String> errors = new HashMap<>();
        violations.forEach(violation -> errors.put(trimPath(violation.getPropertyPath()), violation.getMessage()));

        return Response.ok(errors).status(BAD_REQUEST).build();
    }

    /**
     * handles generic persistence exceptions and routes them to the proper specialized handler.
     *
     * @param throwable the persistence exception
     *
     * @return a {@link Response} the specialized handler's result or a {@link Response.Status#BAD_REQUEST} if no handler is matched
     */
    public static Response persistenceExResponse(final Throwable throwable) {
        log.errorf(throwable.getMessage());

        if (throwable.getCause() instanceof HibernateException hbnEx && hbnEx.getCause() instanceof PgException pgException) {
            return processPgException(pgException);
        }

        if (throwable.getCause() instanceof HibernateException hbnEx && hbnEx.getCause() instanceof ConstraintViolationException violationException) {
            return violationsToResponse(violationException);
        }

        return Response.status(BAD_REQUEST).build();
    }

    /**
     * handles exceptions for which we want to return a 500.
     * <p>
     * this can be a blanket handler for when all else fails
     *
     * @param throwable the exception
     *
     * @return a {@link Response} carrying only a {@link Response.Status#INTERNAL_SERVER_ERROR}
     */
    public static Response failToServerError(final Throwable throwable) {
        log.errorf(throwable.getMessage());

        return Response.serverError().build();
    }

    /**
     * handles postgreSQL exceptions.
     *
     * @param throwable the pgException
     *
     * @return a {@link Response} wrapping the errors in a map, the map is returned as a k/v json.
     */
    public static Response processPgException(final Throwable throwable) {
        PgException pgEx      = ((PgException) throwable);
        String      pgErrCode = pgEx.getSqlState();
        String      pgErr     = pgEx.getDetail() == null ? pgEx.getErrorMessage() : pgEx.getDetail();

        log.errorf(":::pgErrorCode: [%s] - errorDetail: [%s]", pgErrCode, pgErr);

        Map<String, String> errMessage = new HashMap<>();
        errMessage.put("error", pgErr.substring(pgErr.lastIndexOf("=") + 1)
                                     .replace("\"", "'"));

        Response.Status status = PgErrorCodes.getStatusForPgErrCode(pgErrCode);
        return Response.ok(errMessage).status(status).build();
    }

    /**
     * trims down a validation property {@link Path} down to the string after the last "." (dot).
     *
     * @param propertyPath the Path to trim
     *
     * @return the resulting string
     */
    private static String trimPath(final Path propertyPath) {
        String fullPath = propertyPath.toString();
        return fullPath.substring(fullPath.lastIndexOf(".") + 1);
    }

}
