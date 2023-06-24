package com.shail.shrinkit.repository;

import com.shail.shrinkit.entity.ShrinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<ShrinkEntity, Integer> {

    public Optional<ShrinkEntity> findByKey(String key);

    public Optional<ShrinkEntity> findByUrl(String url);

}
