package com.ijse.innrisebackend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discountId;

    private String code;
    private double percentage;

    @OneToMany(mappedBy = "discount")
    private List<Hotel> hotels;
}
