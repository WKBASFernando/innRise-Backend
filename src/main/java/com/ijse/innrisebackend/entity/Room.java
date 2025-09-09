package com.ijse.innrisebackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    private String type;
    private double price;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
