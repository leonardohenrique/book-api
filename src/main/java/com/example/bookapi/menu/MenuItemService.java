package com.example.bookapi.menu;


import com.example.bookapi.menu.dto.CreateMenuItemDTO;
import com.example.bookapi.menu.dto.UpdateMenuItemDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final ModelMapper modelMapper;

    public List<MenuItem> findAll() {
        return menuItemRepository.findAll();
    }

    public List<MenuItem> findAllByParentIdIsNull() {
        return menuItemRepository.findAllByParentIdIsNull();
    }

    public Optional<MenuItem> findById(Integer id) {
        return menuItemRepository.findById(id);
    }

    public MenuItem create(@Validated CreateMenuItemDTO createMenuItemDTO) {
        var menuItem = modelMapper.map(createMenuItemDTO, MenuItem.class);
        return menuItemRepository.save(menuItem);
    }

    public Optional<MenuItem> update(Integer id, @Validated UpdateMenuItemDTO updateMenuItemDTO) {
        return menuItemRepository.findById(id).map((menuItem) -> {
            modelMapper.map(updateMenuItemDTO, menuItem);
            return menuItemRepository.save(menuItem);
        });
    }

    public void deleteById(Integer id) {
        menuItemRepository.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return menuItemRepository.existsById(id);
    }


}
