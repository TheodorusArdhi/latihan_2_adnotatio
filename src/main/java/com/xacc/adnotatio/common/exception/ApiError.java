package com.xacc.adnotatio.common.exception;

import java.time.LocalDateTime;

public record ApiError<T>(
        LocalDateTime timestamp,
        int status,
        String error,
        T message,
        String path
) {
}
