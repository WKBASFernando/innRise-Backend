package com.ijse.innrisebackend.controller;

import com.ijse.innrisebackend.dto.ApiResponse;
import com.ijse.innrisebackend.dto.HotelDTO;
import com.ijse.innrisebackend.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/innrise/hotel")
@RequiredArgsConstructor
@CrossOrigin
public class HotelController {
    private final HotelService hotelService;
    @GetMapping("search/{keyword}")
    public ResponseEntity<ApiResponse> getHotel(@PathVariable String keyword) {
        List<HotelDTO> hotelDTOS = hotelService.getAllHotelsByKeyword(keyword);
        return ResponseEntity.ok(
                new ApiResponse(
                        200,
                        "Hotels found",
                        hotelDTOS
                )
        );
    }

    @PostMapping("create")
    public ResponseEntity<ApiResponse> createHotel(@Valid @RequestBody HotelDTO hotelDTO) {
        log.info("Hotel Created");
        hotelService.saveHotel(hotelDTO);
        return ResponseEntity.ok(
                new ApiResponse(
                        200,
                        "Success",
                        "Hotel Created"
                )
        );
    }

    @PutMapping("update")
    public ResponseEntity<ApiResponse> updateHotel(@Valid @RequestBody HotelDTO hotelDTO) {
        log.info("Hotel Updated");
        hotelService.saveHotel(hotelDTO);
        return ResponseEntity.ok(
                new ApiResponse(
                        200,
                        "Success",
                        "Hotel Updated"
                )
        );
    }

    @GetMapping("getAll")
    public ResponseEntity<ApiResponse> getAllHotels() {
        List<HotelDTO> hotelDTOS = hotelService.getAllHotels();
        return ResponseEntity.ok(
                new ApiResponse(
                        200,
                        "Hotels found",
                        hotelDTOS
                )
        );
    }
}
