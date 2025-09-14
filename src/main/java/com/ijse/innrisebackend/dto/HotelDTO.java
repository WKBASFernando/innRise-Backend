package com.ijse.innrisebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {
    private Long hotelId;
    private String name;
    private String location;
    private String address;
    private String contact_number;
    private String email;
    private String description;
    private int star_rating;
    private Long discountId; // instead of full Discount object
}
