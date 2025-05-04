package dev.techsphere.shrinkit.model;

public class ErrorResponse {
    private String errorDesc;

    public ErrorResponse(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }
}
