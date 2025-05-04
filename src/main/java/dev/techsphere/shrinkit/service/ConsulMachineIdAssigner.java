package dev.techsphere.shrinkit.service;

import dev.techsphere.shrinkit.exception.ShrinkItException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class ConsulMachineIdAssigner {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.cloud.consul.scheme:http}://${spring.cloud.consul.host:localhost}:${spring.cloud.consul.port:8500}")
    private String consulBaseUrl;

    private String sessionId;
    private int machineId = -1;
    private static final int MAX_MACHINE_ID = 31;
    
    @Autowired
    private RestTemplate template;

    @PostConstruct
    public void init() throws ShrinkItException {
        log.info("Consul Host:: {}", consulBaseUrl);
        sessionId = createSession();
        for (int i = 0; i < MAX_MACHINE_ID; i++) {
            if (acquireLock(i)) {
                this.machineId = i;
                log.info("Acquired machine ID: {}", i);
                break;
            }
        }

        if (machineId == -1) {
            throw new IllegalStateException("Could not acquire any machine ID from Consul.");
        }
    }

    @PreDestroy
    public void cleanup() {
        if (sessionId != null && machineId != -1) {
            template.put(consulBaseUrl + "/v1/kv/snowflake/machines/" + machineId + "?release=" + sessionId, null);
            template.put(consulBaseUrl + "/v1/session/destroy/" + sessionId, null);
            log.info("Released machine ID: {}", machineId);
        }
    }

    private String createSession() throws ShrinkItException {
        String name = "snowflake-" + UUID.randomUUID();
        String sessionJson = "{\"Name\": \"" + name + "\", \"TTL\": \"30s\", \"Behavior\": \"delete\"}";
        String sessionUrl = consulBaseUrl + "/v1/session/create";

        var response = template.exchange(sessionUrl, HttpMethod.PUT, new HttpEntity<>(sessionJson), String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody() != null ? response.getBody().split("\"")[3] : null;
        }
        throw new ShrinkItException("Failed to create Consul session");
    }

    private boolean acquireLock(int id) {
        String keyUrl = consulBaseUrl + "/v1/kv/snowflake/machines/" + id + "?acquire=" + sessionId;
        var response = template.exchange(keyUrl, HttpMethod.PUT, null, String.class);
        return Boolean.parseBoolean(response.getBody());
    }

    public int getMachineId() {
        return machineId;
    }

    //This should be dynamic but for simplicity used a hardcoded value
    public int getDatacenterId() {
        return 1;
    }
}
