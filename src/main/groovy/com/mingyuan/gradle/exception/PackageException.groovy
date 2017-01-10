package com.mingyuan.gradle.exception

/**
 * Created by yanxq on 17/1/10.
 */
class PackageException extends RuntimeException {

    PackageException() {
        super()
    }

    PackageException(String message) {
        super(message)
    }

    PackageException(String message, Throwable cause) {
        super(message, cause)
    }

    PackageException(Throwable cause) {
        super(cause)
    }

    protected PackageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace)
    }
}
