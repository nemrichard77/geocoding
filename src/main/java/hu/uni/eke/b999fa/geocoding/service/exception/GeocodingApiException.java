package hu.uni.eke.b999fa.geocoding.service.exception;

public class GeocodingApiException extends Exception {
    public GeocodingApiException() {
        super();
    }

    public GeocodingApiException(String message) {
        super(message);
    }

    public GeocodingApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeocodingApiException(Throwable cause) {
        super(cause);
    }

    protected GeocodingApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
