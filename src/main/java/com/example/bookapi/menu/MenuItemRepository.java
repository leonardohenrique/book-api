package com.example.bookapi.menu;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {

    List<MenuItem> findAllByParentIdIsNull();

}
