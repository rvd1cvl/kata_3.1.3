package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class InitializationUserDB {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public InitializationUserDB(UserService userService, RoleService roleService) {
        this.userService = userService;

        this.roleService = roleService;
    }

    @PostConstruct
    public void createUsersWithRoles() {

        Role role1 = new Role("ADMIN");
        Role role2 = new Role("USER");

        roleService.saveRole(role1);
        roleService.saveRole(role2);

        Set<Role> userRole = new HashSet<>();
        userRole.add(role2);

        Set<Role> adminRole = new HashSet<>();
        adminRole.add(role1);
        adminRole.add(role2);

        User admin = new User("Абоба", "Абобович", "123", 18, new BCryptPasswordEncoder(8).encode("123"), userRole);
        User user = new User("Биба", "Бибович", "1234", 18, new BCryptPasswordEncoder(8).encode("1234"), adminRole);

        System.out.println(admin.toString());
        userService.saveUser(admin);
        userService.saveUser(user);
    }
}
