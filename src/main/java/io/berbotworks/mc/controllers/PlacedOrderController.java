package io.berbotworks.mc.controllers;

import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import io.berbotworks.mc.models.PlacedOrder;
import io.berbotworks.mc.services.PlacedOrderService;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = { "http://localhost:3000",
        "https://ft-s.herokuapp.com/" }, allowedHeaders = "*", maxAge = 10000l)
@RequiredArgsConstructor
@RestController
public class PlacedOrderController {
    private final PlacedOrderService placedOrderService;

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody List<PlacedOrder> placedOrders, ServletRequest request) {
        Long uid = ((Integer) request.getAttribute("uid")).longValue();
        placedOrderService.placeOrder(placedOrders, uid);
        return ResponseEntity.ok().build();
    }
}
