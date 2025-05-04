package dev.techsphere.shrinkit.repository;

import dev.techsphere.shrinkit.entity.ShrinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShrinkUrlRepository extends JpaRepository<ShrinkEntity, Long> {

    public Optional<ShrinkEntity> findByShrinkCode(String key);

    public Optional<ShrinkEntity> findByOriginalUrl(String url);

}
