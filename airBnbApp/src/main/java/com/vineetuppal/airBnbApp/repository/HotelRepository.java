package com.vineetuppal.airBnbApp.repository;

import com.vineetuppal.airBnbApp.entity.Hotel;
import com.vineetuppal.airBnbApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByOwner(User user);
}
