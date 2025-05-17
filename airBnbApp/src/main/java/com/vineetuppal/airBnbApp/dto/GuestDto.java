package com.vineetuppal.airBnbApp.dto;

import com.vineetuppal.airBnbApp.entity.User;
import com.vineetuppal.airBnbApp.entity.enums.Gender;
import lombok.Data;

@Data
public class GuestDto {
    private Long id;
    private User user;
    private String name;
    private Gender gender;
    private Integer age;
}
