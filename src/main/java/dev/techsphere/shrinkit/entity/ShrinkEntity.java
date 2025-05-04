package dev.techsphere.shrinkit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Table(name = "shrink_urls")
@Entity
@SequenceGenerator(name = "SEQ_ENTRY", sequenceName = "ENTRY_SEQ", allocationSize = 1)
public class ShrinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ENTRY")
    private Long id;

    @Column(name = "original_url", nullable = false)
    private String originalUrl;

    @Column(name = "shrink_code", nullable = false, unique = true)
    private String shrinkCode;

    @Lob
    @Column(name = "safety_info", columnDefinition = "TEXT")
    private String safetyInfo;

    @Lob
    @Column(name = "category_info", columnDefinition = "TEXT")
    private String categoryInfo;

    public ShrinkEntity() {
    }

    public ShrinkEntity(String originalUrl, String shrinkCode, String safetyInfo, String categoryInfo) {
        this.originalUrl = originalUrl;
        this.shrinkCode = shrinkCode;
        this.safetyInfo = safetyInfo;
        this.categoryInfo = categoryInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShrinkCode() {
        return shrinkCode;
    }

    public void setShrinkCode(String shrinkCode) {
        this.shrinkCode = shrinkCode;
    }

    public String getSafetyInfo() {
        return safetyInfo;
    }

    public void setSafetyInfo(String safetyInfo) {
        this.safetyInfo = safetyInfo;
    }

    public String getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(String categoryInfo) {
        this.categoryInfo = categoryInfo;
    }
}
