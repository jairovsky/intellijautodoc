package io.github.jairovsky.intellijautodoc.project;

class CantGetProjectVersion extends RuntimeException {

    public CantGetProjectVersion() {
    }

    public CantGetProjectVersion(String message) {
        super(message);
    }

    public CantGetProjectVersion(String message, Throwable cause) {
        super(message, cause);
    }

    public CantGetProjectVersion(Throwable cause) {
        super(cause);
    }

    public CantGetProjectVersion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
