package dev.techsphere.shrinkit.utils;

import dev.techsphere.shrinkit.entity.AuditInfo;
import dev.techsphere.shrinkit.model.ShrinkResponse;
import dev.techsphere.shrinkit.service.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommonUtils {

    @Autowired
    private SnowflakeIdGenerator idGenerator;

    public String generateCode() {
        return Base62Encoder.encode(idGenerator.generateId());
    }

    public <T extends AuditInfo> T setAuditInfo(T info, boolean isNew) {
        if (isNew) {
            info.setCreatedBy("SHRINK_IT_SERVICE");
            info.setCreationTimeStamp(LocalDateTime.now());
        }

        info.setUpdateTimestamp(LocalDateTime.now());
        info.setUpdatedBy("SHRINK_IT_SERVICE");

        return info;
    }

    public static String getFormattedResp(ShrinkResponse resp) {
        return String.format("""
                        Original URL: %s
                        
                        Shrink URL: %s
                        
                        Safety Info: %s
                        
                        Classification Info: %s
                        """, resp.getOriginalUrl(),
                resp.getShrinkUrl(),
                resp.getSafetyInfo(),
                resp.getUrlClassification());
    }
}
