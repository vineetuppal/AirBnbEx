package com.vineetuppal.airBnbApp.repository;

import com.vineetuppal.airBnbApp.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}