package com.ijse.innrisebackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class HotelPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private PackageEntity packageEntity;

    // optional extra fields
    private double specialPrice;
    private String availability;
}
