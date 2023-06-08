package com.bmc.flow.modules.database.postgresql;

import jakarta.ws.rs.core.Response.Status;

import java.util.HashMap;
import java.util.Map;

import static jakarta.ws.rs.core.Response.Status.*;

/**
 * Postgresql error codes.
 * <p>
 *
 * @see <a href="https://www.postgresql.org/docs/current/errcodes-appendix.html">postgresql errors</a>
 */
public class PgErrorCodes {

    private static final Integer NOT_NULL_VIOLATION = 23502;

    private static final Integer FOREIGN_KEY_VIOLATION = 23503;

    private static final Integer UNIQUE_VIOLATION = 23505;

    // integrity constraint violation
    // Syntax Error or Access Rule Violation
    private static final Integer UNDEFINED_COLUMN_VIOLATION = 42703;

    private static final Map<Integer, Status> pgErrCodesToStatus;

    static {
        pgErrCodesToStatus = new HashMap<>();
        pgErrCodesToStatus.put(UNIQUE_VIOLATION, CONFLICT);
        pgErrCodesToStatus.put(FOREIGN_KEY_VIOLATION, BAD_REQUEST);
        pgErrCodesToStatus.put(UNDEFINED_COLUMN_VIOLATION, BAD_REQUEST);
        pgErrCodesToStatus.put(NOT_NULL_VIOLATION, BAD_REQUEST);
    }

    private PgErrorCodes() {
    }

    public static Status getStatusForPgErrCode(final String pgErrCode) {
        return pgErrCodesToStatus.getOrDefault(Integer.valueOf(pgErrCode), INTERNAL_SERVER_ERROR);
    }

}
