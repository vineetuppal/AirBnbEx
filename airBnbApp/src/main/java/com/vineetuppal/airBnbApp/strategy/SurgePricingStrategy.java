package com.vineetuppal.airBnbApp.strategy;

import com.vineetuppal.airBnbApp.entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class SurgePricingStrategy implements PricingStrategy {

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price =  wrapped.calculatePrice(inventory);
        return price.multiply(inventory.getSurgeFactor());
    }
}
