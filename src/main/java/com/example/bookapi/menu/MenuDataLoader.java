package com.example.bookapi.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final MenuItemRepository menuItemRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (menuItemRepository.count() > 0) return;

        var admin = menuItemRepository.save(new MenuItem(1, "Administration", "", "fas fa-cog", null));
        menuItemRepository.save(new MenuItem(2, "Books", "/books", "fas fa-book", null));
        menuItemRepository.save(new MenuItem(3, "Authors", "/authors", "fas fa-user-edit", admin));
        menuItemRepository.save(new MenuItem(4, "Genres", "/genres", "far fa-object-ungroup", admin));
        menuItemRepository.save(new MenuItem(5, "Users", "/users", "fas fa-user", admin));
    }
}
