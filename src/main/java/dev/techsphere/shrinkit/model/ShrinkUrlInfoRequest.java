package dev.techsphere.shrinkit.model;

public class ShrinkUrlInfoRequest {

    private String shrinkUrl;

    public ShrinkUrlInfoRequest() {
    }

    public ShrinkUrlInfoRequest(String shrinkUrl) {
        this.shrinkUrl = shrinkUrl;
    }

    public String getShrinkUrl() {
        return shrinkUrl;
    }

    public void setShrinkUrl(String shrinkUrl) {
        this.shrinkUrl = shrinkUrl;
    }
}
