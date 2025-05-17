package com.vineetuppal.airBnbApp.strategy;

import com.vineetuppal.airBnbApp.entity.Inventory;

import java.math.BigDecimal;

public interface PricingStrategy {

    BigDecimal calculatePrice(Inventory inventory);
}
