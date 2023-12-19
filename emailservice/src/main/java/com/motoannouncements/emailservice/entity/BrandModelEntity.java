package com.motoannouncements.emailservice.entity;

import com.motoannouncements.emailservice.enums.BrandType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name="t_brand_model")
@Data
public class BrandModelEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(name="brand", nullable = false)
    private BrandType brand;
    @Column(name="model", nullable = true)
    private String model;
}
