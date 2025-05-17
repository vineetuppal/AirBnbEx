package com.vineetuppal.airBnbApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vineetuppal.airBnbApp.entity.Hotel;
import com.vineetuppal.airBnbApp.entity.Room;
import com.vineetuppal.airBnbApp.entity.User;
import com.vineetuppal.airBnbApp.entity.enums.BookingStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDto {
    private Long id;
    private Integer roomsCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime createdAt;
    private  LocalDateTime updatedAt;
    private BookingStatus bookingStatus;
    private Set<GuestDto> guests;
    private BigDecimal amount;
}
