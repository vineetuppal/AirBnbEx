package com.vineetuppal.airBnbApp.service;

import com.vineetuppal.airBnbApp.entity.Booking;

public interface CheckoutService {
    String getCheckoutSession(Booking booking, String successUrl, String failureUrl);
}
