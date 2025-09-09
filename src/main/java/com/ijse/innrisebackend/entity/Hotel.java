package com.ijse.innrisebackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;

    // --- Basic Info ---
    private String name;
    private String location;
    private String address;
    private String contactNumber;
    private String email;

    @Column(length = 2000)
    private String description;

    private int starRating; // 1–5 stars

    // --- Relationships ---
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HotelPhoto> photos;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HotelPackage> hotelPackages;
}
