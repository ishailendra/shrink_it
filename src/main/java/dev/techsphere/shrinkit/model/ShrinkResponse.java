package dev.techsphere.shrinkit.model;

public class ShrinkResponse {

    private String originalUrl;
    private String shrinkUrl;
    private String safetyInfo;
    private String urlClassification;

    public ShrinkResponse() {
    }

    public ShrinkResponse(String originalUrl, String shrinkUrl, String safetyInfo, String urlClassification) {
        this.originalUrl = originalUrl;
        this.shrinkUrl = shrinkUrl;
        this.safetyInfo = safetyInfo;
        this.urlClassification = urlClassification;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String url) {
        this.originalUrl = url;
    }

    public String getShrinkUrl() {
        return shrinkUrl;
    }

    public void setShrinkUrl(String shrinkUrl) {
        this.shrinkUrl = shrinkUrl;
    }

    public String getSafetyInfo() {
        return safetyInfo;
    }

    public void setSafetyInfo(String safetyInfo) {
        this.safetyInfo = safetyInfo;
    }

    public String getUrlClassification() {
        return urlClassification;
    }

    public void setUrlClassification(String urlClassification) {
        this.urlClassification = urlClassification;
    }
}
