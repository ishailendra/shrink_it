package com.shail.shrinkit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "shrink_urls")
@Entity
@Getter @Setter
@SequenceGenerator(name = "SEQ_ENTRY", sequenceName = "ENTRY_SEQ", allocationSize = 1)
public class ShrinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ENTRY")
    private Integer id;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "shrink_key", nullable = false, unique = true)
    private String key;
}
