package com.ijse.innrisebackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class HotelPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private String caption;
    private int sortOrder;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
