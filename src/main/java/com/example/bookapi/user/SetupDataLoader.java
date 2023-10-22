package com.example.bookapi.user;

import com.example.bookapi.user.entity.Privilege;
import com.example.bookapi.user.entity.Role;
import com.example.bookapi.user.entity.User;
import com.example.bookapi.user.repository.PrivilegeRepository;
import com.example.bookapi.user.repository.RoleRepository;
import com.example.bookapi.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (userRepository.findByUsername("admin").isPresent()) return;

        var readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        var writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        var adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);

        var adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        var userRole = createRoleIfNotFound("ROLE_USER", Collections.singletonList(readPrivilege));

        var adminUser = new User();
        adminUser.setName("Administrator");
        adminUser.setEmail("admin@test.com");
        adminUser.setUsername("admin");
        adminUser.setPassword(passwordEncoder.encode("admin"));
        adminUser.setRoles(Collections.singletonList(adminRole));
        adminUser.setEnabled(true);
        userRepository.save(adminUser);

        var user = new User();
        user.setName("User");
        user.setEmail("user@test.com");
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setRoles(Collections.singletonList(userRole));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Transactional
    public Privilege createPrivilegeIfNotFound(String name) {
        return privilegeRepository.findByName(name).orElseGet(() -> {
            var privilege = new Privilege(name);
            return privilegeRepository.save(privilege);
        });
    }

    @Transactional
    public Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        return roleRepository.findByName(name).orElseGet(() -> {
            var role = new Role(name);
            role.setPrivileges(privileges);
            return roleRepository.save(role);
        });
    }
}