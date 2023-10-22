package com.example.bookapi.menu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String link;

    @Column(length = 45)
    private String iconClass;

    @JsonIgnoreProperties({"children"})
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private MenuItem parent;

    @JsonIgnoreProperties({"parent"})
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItem> children = new ArrayList<>();

    public MenuItem(Integer id, String name, String link, String iconClass, MenuItem parent) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.iconClass = iconClass;
        this.parent = parent;
    }

    public void addChild(MenuItem child) {
        children.add(child);
        child.setParent(this);
    }

    public void removeChild(MenuItem child) {
        children.remove(child);
        child.setParent(null);
    }
}