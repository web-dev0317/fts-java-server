package io.berbotworks.mc.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import io.berbotworks.mc.models.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findAllByUser_Id(Long uid);

    Optional<MenuItem> findById(Long itemId);

    @Query("SELECT available FROM MenuItem WHERE id = ?1")
    int getAvailableById(Long itemId);

    @Query("SELECT normallyAvailableQty FROM MenuItem WHERE id = ?1")
    int getNormallyAvailableQtyById(Long itemId);

    @Transactional
    @Modifying
    @Query("UPDATE MenuItem SET available = ?2 WHERE id = ?1")
    int updateAvailableById(Long itemId, int available);

    @Transactional
    @Modifying
    @Query("UPDATE MenuItem SET iname = ?1, normallyAvailableQty = ?2, available = ?3, quantityOrdered = ?4, placedOrderQuantity = ?5, price = ?6 WHERE id = ?7")
    int updateMenuItem(String iname, int normallyAvailableQty, int available, int quantityOrdered,
            int placedOrderQuantity, BigDecimal price, Long itemId);
}
