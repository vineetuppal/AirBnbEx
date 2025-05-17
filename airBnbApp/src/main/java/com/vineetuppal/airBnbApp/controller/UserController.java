package com.vineetuppal.airBnbApp.controller;

import com.vineetuppal.airBnbApp.dto.BookingDto;
import com.vineetuppal.airBnbApp.dto.ProfileUpdateRequestDto;
import com.vineetuppal.airBnbApp.dto.UserDto;
import com.vineetuppal.airBnbApp.entity.Booking;
import com.vineetuppal.airBnbApp.service.BookingService;
import com.vineetuppal.airBnbApp.service.BookingServiceImpl;
import com.vineetuppal.airBnbApp.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController{

    private final UserService userService;
    private final BookingService bookingService;

    @PatchMapping("/profile")
    public ResponseEntity<Void> updateProfile(@RequestBody ProfileUpdateRequestDto profileUpdateRequestDto){
        userService.updateProfile(profileUpdateRequestDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/myBookings")
    public ResponseEntity<List<BookingDto>> getMyBookings(){
        return ResponseEntity.ok(bookingService.getMyBookings());
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getMyProfile(){
        return ResponseEntity.ok(bookingService.getMyProfile());
    }
}
