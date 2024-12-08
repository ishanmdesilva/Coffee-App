package com.ino.coffeeApp.service.impl;

import com.ino.coffeeApp.entity.MenuItem;
import com.ino.coffeeApp.exception.ValidateRecordException;
import com.ino.coffeeApp.repository.MenuItemRepository;
import com.ino.coffeeApp.service.MenuItemService;
import com.ino.coffeeApp.util.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public MenuItem getById(Long id) {
        Optional<MenuItem> optMenuItem = menuItemRepository.findById(id);
        if (optMenuItem.isEmpty()) {
            throw new ValidateRecordException("Menu Item not found.", ErrorCodes.ENTITY_NOT_FOUND);
        }

        return optMenuItem.get();
    }
}
