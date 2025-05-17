package com.vineetuppal.airBnbApp.service;

import com.vineetuppal.airBnbApp.dto.ProfileUpdateRequestDto;
import com.vineetuppal.airBnbApp.entity.User;

public interface UserService {
    User getUserById(Long id);

    void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto);
}
