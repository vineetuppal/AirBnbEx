package com.vineetuppal.airBnbApp.controller;

import com.vineetuppal.airBnbApp.dto.HotelDto;
import com.vineetuppal.airBnbApp.dto.HotelInfoDto;
import com.vineetuppal.airBnbApp.dto.HotelPriceDto;
import com.vineetuppal.airBnbApp.dto.HotelSearchRequest;
import com.vineetuppal.airBnbApp.service.HotelService;
import com.vineetuppal.airBnbApp.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelBrowseController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<Page<HotelPriceDto>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest){
        var page =  inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);
    }

    @GetMapping("{hotelId}/info")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId){
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }
}
