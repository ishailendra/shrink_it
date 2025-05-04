package dev.techsphere.shrinkit.service;

import dev.techsphere.shrinkit.entity.ShrinkEntity;
import dev.techsphere.shrinkit.exception.ShrinkItException;
import dev.techsphere.shrinkit.model.ShrinkRequest;
import dev.techsphere.shrinkit.model.ShrinkResponse;
import dev.techsphere.shrinkit.model.ShrinkUrlInfoRequest;
import dev.techsphere.shrinkit.repository.ShrinkUrlRepository;
import dev.techsphere.shrinkit.utils.CommonUtils;
import dev.techsphere.shrinkit.utils.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ShrinkService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommonUtils utils;

    @Autowired
    private ShrinkUrlRepository repo;

    @Value("${shrink.base.url}")
    private String baseUrl;

    @Autowired
    private ValidationUtil validationUtil;

    @Autowired
    private UrlAiService aiService;

    @Autowired
    private SnowflakeIdGenerator idGenerator;

    @Transactional
    @Cacheable(value = "url_generation_cache", key = "#request.originalUrl", unless = "#result == null")
    public ShrinkResponse shrinkUrl(ShrinkRequest request) throws ShrinkItException {

        ShrinkResponse response = null;

        try {

            if (!validationUtil.isRequestValid(request)) {
                throw new ShrinkItException("Invalid Request");
            }

            if (!validationUtil.isValidUrl(request.getOriginalUrl())) {
                throw new ShrinkItException("Invalid URL");
            }

            String originalUrl = request.getOriginalUrl();

            Optional<ShrinkEntity> urlOptional = repo.findByOriginalUrl(originalUrl);

            response = urlOptional.map(ent -> new ShrinkResponse(ent.getOriginalUrl(), generateUrl(ent.getShrinkCode()), ent.getSafetyInfo(), ent.getCategoryInfo()))
                    .orElseGet(() -> {
                        String safetyInfo = aiService.detectMaliciousUrl(originalUrl);
                        String urlClassification = aiService.categorizeUrl(originalUrl);
                        String shrinkCode = utils.generateCode().trim();
                        repo.save(new ShrinkEntity(originalUrl, shrinkCode, safetyInfo, urlClassification));
                        return new ShrinkResponse(originalUrl, generateUrl(shrinkCode), safetyInfo, urlClassification);
                    });

            return response;

        } catch (Exception e) {
            throw new ShrinkItException(e.getMessage());
        }
    }

    private String generateUrl(String shrinkCode) {
        return baseUrl + shrinkCode;
    }

    @Transactional
    @Cacheable(value = "url_redirect_cache", key = "#shrinkCode", unless = "#result == null")
    public String getOriginalUrl(String shrinkCode) throws ShrinkItException {
        log.info("Fetching Original URL for: {}", shrinkCode);
        return repo.findByShrinkCode(shrinkCode.trim())
                    .map(ShrinkEntity::getOriginalUrl)
                    .orElseThrow(() -> new ShrinkItException("Invalid Shrink URL"));
    }

    @Transactional
    @Cacheable(value = "url_info_cache", key = "#request.shrinkUrl", unless = "#result == null")
    public ShrinkResponse shrinkUrlInfo(ShrinkUrlInfoRequest request) throws ShrinkItException {

        if (!validationUtil.isShrinkInfoRequestValid(request)) {
           throw new ShrinkItException("Invalid Request");
        }

        String shrinkCode = getShrinkCode(request.getShrinkUrl());

        log.info("Fetching Original URL info for: {}", shrinkCode);

        return repo.findByShrinkCode(shrinkCode)
                .map(ent -> new ShrinkResponse(ent.getOriginalUrl(), generateUrl(shrinkCode), ent.getSafetyInfo(), ent.getCategoryInfo()))
                .orElseThrow(() -> new ShrinkItException("Invalid Shrink URL"));
    }

    public String getShrinkCode(String shrinkUrl) {
        return shrinkUrl.replace(baseUrl, "");
    }
}
