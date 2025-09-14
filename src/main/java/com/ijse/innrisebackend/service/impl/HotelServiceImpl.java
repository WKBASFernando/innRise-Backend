package com.ijse.innrisebackend.service.impl;

import com.ijse.innrisebackend.dto.HotelDTO;
import com.ijse.innrisebackend.entity.Hotel;
import com.ijse.innrisebackend.exception.ValidationException;
import com.ijse.innrisebackend.repository.HotelRepository;
import com.ijse.innrisebackend.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    @Override
    public void saveHotel(HotelDTO hotelDTO) {
        if(hotelDTO.getName()==null){
            throw new ValidationException("Hotel name is required");
        }
        hotelRepository.save(modelMapper.map(hotelDTO, Hotel.class));
    }

    @Override
    public void updateHotel(HotelDTO hotelDTO) {
        hotelRepository.save(modelMapper.map(hotelDTO, Hotel.class));
    }

    @Override
    public List<HotelDTO> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        return modelMapper.map(hotels, new TypeToken<List<HotelDTO>>() {}.getType());
    }

    @Override
    public void changeHotelStatus(String id) {

    }

    @Override
    public List<HotelDTO> getAllHotelsByKeyword(String status) {
        List<Hotel> list = hotelRepository.findByNameContainingIgnoreCase(status);
        return modelMapper.map(list, new TypeToken<List<HotelDTO>>() {}.getType());
    }
}
