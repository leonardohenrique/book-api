package com.example.bookapi.user;

import com.example.bookapi.user.dto.CreateUserDTO;
import com.example.bookapi.user.dto.UpdateUserDTO;
import com.example.bookapi.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getAll(@RequestParam(required = false) String term) {
        if (!isEmpty(term)) {
            return userService.searchByTerm(term);
        }
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Validated CreateUserDTO createUserDTO) {
        return userService.create(createUserDTO);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @Validated @RequestBody UpdateUserDTO updateUserDTO) {
        return userService.update(id, updateUserDTO).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!userService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + id);
        }

        userService.deleteById(id);
    }
}
