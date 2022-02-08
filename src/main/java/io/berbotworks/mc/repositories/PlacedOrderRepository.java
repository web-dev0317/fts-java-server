package io.berbotworks.mc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.berbotworks.mc.models.PlacedOrder;

public interface PlacedOrderRepository extends JpaRepository<PlacedOrder, Long> {

}
