package com.vineetuppal.airBnbApp.service;

import com.vineetuppal.airBnbApp.dto.HotelDto;
import com.vineetuppal.airBnbApp.dto.HotelInfoDto;
import com.vineetuppal.airBnbApp.dto.RoomDto;
import com.vineetuppal.airBnbApp.entity.Hotel;
import com.vineetuppal.airBnbApp.entity.Room;
import com.vineetuppal.airBnbApp.entity.User;
import com.vineetuppal.airBnbApp.exception.ResourceNotFoundException;
import com.vineetuppal.airBnbApp.exception.UnAuthorisedException;
import com.vineetuppal.airBnbApp.repository.HotelRepository;
import com.vineetuppal.airBnbApp.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.vineetuppal.airBnbApp.util.AppUtils.getCurrentUser;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService{

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;
    private final RoomRepository roomRepository;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating a new hotel with name: {}", hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto,Hotel.class);
        hotel.setActive(false);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        hotel.setOwner(user);
        hotel = hotelRepository.save(hotel);
        log.info("Created a new hotel with ID: {}",hotel.getId());
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting the hotel with ID: {}", id);
        Hotel hotel =  hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+id));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.equals(hotel.getOwner())){
            throw new UnAuthorisedException("This user doesn't own this hotel with id: "+id);
        }

        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("Updating the hotel with ID: {}", id);
        Hotel hotel =  hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+id));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.equals(hotel.getOwner())){
            throw new UnAuthorisedException("This user doesn't own this hotel with id: "+id);
        }

        modelMapper.map(hotelDto, hotel);
        hotel.setId(id);
        hotel =  hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel =  hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+id));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.equals(hotel.getOwner())){
            throw new UnAuthorisedException("This user doesn't own this hotel with id: "+id);
        }

        for(Room room: hotel.getRooms()){
            inventoryService.deleteAllInventories(room);
            roomRepository.deleteById(room.getId());
        }

        hotelRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void activateHotel(Long hotelId) {
        log.info("Activating the hotel with ID: {}", hotelId);
        Hotel hotel =  hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+hotelId));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.equals(hotel.getOwner())){
            throw new UnAuthorisedException("This user doesn't own this hotel with id: "+hotelId);
        }

        hotel.setActive(true);

        //assuming only do it once
        for(Room room: hotel.getRooms()){
            inventoryService.initializeRoomForAYear(room);
        }

    }

    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {
        Hotel hotel =  hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+hotelId));

        List<RoomDto> rooms = hotel.getRooms().stream().
                map((element) -> modelMapper.map(element, RoomDto.class))
                .toList();

        return new HotelInfoDto(modelMapper.map(hotel, HotelDto.class), rooms);
    }

    @Override
    public List<HotelDto> getAllHotels() {
        User user = getCurrentUser();
        log.info("Getting all hotels for admin user with ID: {}", user.getId());
        List<Hotel> hotels = hotelRepository.findByOwner(user);
        return hotels.stream()
                .map((element) -> modelMapper.map(element, HotelDto.class))
                .collect(Collectors.toList());
    }
}
