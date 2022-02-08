package io.berbotworks.mc.services;

import java.util.List;

import org.springframework.stereotype.Service;

import io.berbotworks.mc.models.PlacedOrder;
import io.berbotworks.mc.repositories.PlacedOrderRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlacedOrderService {
    private final PlacedOrderRepository placedOrderRepository;

    public void placeOrder(List<PlacedOrder> placedOrders, Long uid) {
        placedOrders.forEach(placedOrder -> {
            placedOrder.setUid(uid);
            placedOrderRepository.save(placedOrder);
        });
    }
}
