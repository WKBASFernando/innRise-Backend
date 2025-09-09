package com.ijse.innrisebackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "package_entity")
@Data
public class PackageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long packageId;

    private String packageName;
    private double price;

    @OneToMany(mappedBy = "packageEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HotelPackage> hotelPackages;
}
