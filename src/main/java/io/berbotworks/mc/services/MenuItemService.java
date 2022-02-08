package io.berbotworks.mc.services;

import java.util.List;

import org.springframework.stereotype.Service;

import io.berbotworks.mc.models.MenuItem;
import io.berbotworks.mc.models.User;
import io.berbotworks.mc.repositories.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final UserService userService;

    public List<MenuItem> getMenuItems(Long uid) {
        return menuItemRepository.findAllByUser_Id(uid);
    }

    public MenuItem getMenuItem(Long uid, Long menuItemId) {
        return menuItemRepository.findById(menuItemId).orElse(null);
    }

    public MenuItem saveMenuItem(Long uid, MenuItem menuItem) {
        User user = userService.getUser(uid);
        log.debug("uid {}, user: {}", uid, user);
        menuItem.setUser(user);
        return menuItemRepository.save(menuItem);
    }

    public void deleteMenuItem(Long menuItemId) {
        menuItemRepository.deleteById(menuItemId);
    }

    public void setAvailable(boolean bool, Long itemId) {
        int available;
        if (bool)
            available = menuItemRepository.getNormallyAvailableQtyById(itemId);
        else
            available = 0;
        menuItemRepository.updateAvailableById(itemId, available);
    }

    public void setAvailable(String updateType, Long itemId) {
        int available = menuItemRepository.getAvailableById(itemId);
        if (updateType.equalsIgnoreCase("add"))
            available++;
        else
            available--;
        menuItemRepository.updateAvailableById(itemId, available);
    }

    public void updateMenuItem(Long itemId, MenuItem menuItem, Long uid) {
        MenuItem mi = menuItemRepository.findById(itemId).orElse(null);
        if (mi != null) {
            menuItemRepository.updateMenuItem(menuItem.getIname(), menuItem.getNormallyAvailableQty(),
                    menuItem.getAvailable(), menuItem.getQuantityOrdered(), menuItem.getPlacedOrderQuantity(),
                    menuItem.getPrice(), itemId);
        }
    }
}
