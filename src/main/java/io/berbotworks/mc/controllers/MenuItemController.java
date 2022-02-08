package io.berbotworks.mc.controllers;

import javax.servlet.ServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.berbotworks.mc.models.MenuItem;
import io.berbotworks.mc.services.MenuItemService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MenuItemController {
    private final MenuItemService menuItemService;

    @GetMapping("/items")
    public ResponseEntity<?> getMenuItems(ServletRequest request) {
        Long uid = ((Integer) request.getAttribute("uid")).longValue();
        return ResponseEntity.ok(menuItemService.getMenuItems(uid));
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<?> getMenuItem(@PathVariable Long itemId, ServletRequest request) {
        Long uid = ((Integer) request.getAttribute("uid")).longValue();
        return ResponseEntity.ok(menuItemService.getMenuItem(uid, itemId));
    }

    @PostMapping("/item")
    public ResponseEntity<?> saveMenuItem(@RequestBody MenuItem menuItem, ServletRequest request) {
        Long uid = ((Integer) request.getAttribute("uid")).longValue();
        return new ResponseEntity<>(menuItemService.saveMenuItem(uid, menuItem), HttpStatus.CREATED);
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable Long itemId, ServletRequest request) {
        menuItemService.deleteMenuItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/item/{itemId}/{bool}")
    public ResponseEntity<?> makeItemAvailableOrUnavailable(@PathVariable Long itemId, @PathVariable boolean bool,
            ServletRequest request) {
        menuItemService.setAvailable(bool, itemId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/item/{itemId}/u/{updateType}")
    public ResponseEntity<?> updateAvailable(@PathVariable Long itemId, @PathVariable String updateType,
            ServletRequest request) {
        menuItemService.setAvailable(updateType, itemId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/item/{itemId}")
    public ResponseEntity<?> updateMenuItem(@PathVariable Long itemId, @RequestBody MenuItem menuItem,
            ServletRequest request) {
        Long uid = ((Integer) request.getAttribute("uid")).longValue();
        menuItemService.updateMenuItem(itemId, menuItem, uid);
        return ResponseEntity.noContent().build();
    }
}
