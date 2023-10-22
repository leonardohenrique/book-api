package com.example.bookapi.user;

import com.example.bookapi.user.dto.CreateUserDTO;
import com.example.bookapi.user.dto.UpdateUserDTO;
import com.example.bookapi.user.entity.User;
import com.example.bookapi.user.mapper.UserMapper;
import com.example.bookapi.user.repository.RoleRepository;
import com.example.bookapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User create(@Validated CreateUserDTO createUserDTO) {
        var user = userMapper.map(createUserDTO);
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        var roles = createUserDTO.getRoleIds().stream().map(roleRepository::findById).filter(Optional::isPresent).map(Optional::get).toList();
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public Optional<User> update(Long id, @Validated UpdateUserDTO updateUserDTO) {
        return userRepository.findById(id).map((user) -> {
            userMapper.map(updateUserDTO, user);
            return userRepository.save(user);
        });
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}
