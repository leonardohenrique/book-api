package com.example.bookapi.menu;

import com.example.bookapi.menu.dto.CreateMenuItemDTO;
import com.example.bookapi.menu.dto.UpdateMenuItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @GetMapping
    public List<MenuItem> getAll() {
        return menuItemService.findAll();
    }

    @GetMapping("/{id}")
    public MenuItem getById(@PathVariable Integer id) {
        return menuItemService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu Item not found with id " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuItem create(@RequestBody @Validated CreateMenuItemDTO createMenuItemDTO) {
        return menuItemService.create(createMenuItemDTO);
    }

    @PutMapping("/{id}")
    public MenuItem update(@PathVariable Integer id, @RequestBody @Validated UpdateMenuItemDTO updateMenuItemDTO) {
        return menuItemService.update(id, updateMenuItemDTO).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu Item not found with id " + id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        if (!menuItemService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu Item not found with id " + id);
        }

        menuItemService.deleteById(id);
    }


}
