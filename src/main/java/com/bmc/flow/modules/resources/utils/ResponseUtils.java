package com.bmc.flow.modules.resources.utils;

import com.bmc.flow.modules.database.postgresql.PgErrorCodes;
import io.vertx.pgclient.PgException;
import lombok.extern.jbosslog.JBossLog;
import org.hibernate.HibernateException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@JBossLog
public class ResponseUtils {

  private ResponseUtils() {
  }

  public static Response violationsToResponse(final Throwable throwable) {
    // this is guaranteed to always be a ConstraintViolationException
    Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) throwable).getConstraintViolations();

    Map<String, String> errors = new HashMap<>();
    violations.forEach(violation -> errors.put(trimPath(violation.getPropertyPath()), violation.getMessage()));

    return Response.ok(errors).status(BAD_REQUEST).build();
  }

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

  public static Response failToServerError(final Throwable throwable) {
    log.errorf(throwable.getMessage());

    return Response.serverError().build();
  }

  public static Response processPgException(final Throwable throwable) {
    PgException pgEx = ((PgException) throwable);
    String pgErrCode = pgEx.getCode();
    String pgErr = pgEx.getDetail() == null ? pgEx.getErrorMessage() : pgEx.getDetail();

    log.errorf(":::pgErrorCode: [%s] - errorDetail: [%s]", pgErrCode, pgErr);

    Map<String, String> errMessage = new HashMap<>();
    errMessage.put("error", pgErr.substring(pgErr.lastIndexOf("=") + 1)
                                 .replace("\"", "'"));

    Response.Status status = PgErrorCodes.getStatusForPgErrCode(pgErrCode);
    return Response.ok(errMessage).status(status).build();
  }

  private static String trimPath(final Path propertyPath) {
    String fullPath = propertyPath.toString();
    return fullPath.substring(fullPath.lastIndexOf(".") + 1);
  }

}
