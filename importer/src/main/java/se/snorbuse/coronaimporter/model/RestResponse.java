package se.snorbuse.coronaimporter.model;

public class RestResponse {
    private final int httpStatus;
    private final String responseMessage;

    public RestResponse(int httpStatus, String responseMessage) {
        this.httpStatus = httpStatus;
        this.responseMessage = responseMessage;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getResponseMessage() {
        return responseMessage;
    }
}
